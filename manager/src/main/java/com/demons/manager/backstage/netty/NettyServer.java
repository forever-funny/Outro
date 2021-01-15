package com.demons.manager.backstage.netty;

import com.demons.manager.backstage.handler.ManagerHandler;
import com.demons.manager.backstage.handler.InterceptorHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Outro
 * 启动Netty服务器
 */
@Component
public class NettyServer {

  private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);

  int port = 1218;

  /** 必须采用注解形式,因为该实例中也包含了注解 */
  @Resource
  InterceptorHandler interceptorHandler;

  @Resource
  ManagerHandler managerHandler;

  /** boss个数一般一个即可 */
  private int bossGroupThreadNum = 1;
  /** worker个数默认为 cpu核数 * 2 */
  private int workerGroupThreadNum = 4;
  /** 读取超时时间,单位:秒 */
  private int readTimeoutSecond = 60;

  public void start() {
    logger.info("Manager Netty server start...");
    EventLoopGroup bossGroup = new NioEventLoopGroup(bossGroupThreadNum);
    EventLoopGroup workerGroup = new NioEventLoopGroup(workerGroupThreadNum);
    ServerBootstrap bootstrap = new ServerBootstrap();
    bootstrap.group(bossGroup, workerGroup);
    bootstrap.channel(NioServerSocketChannel.class);
    // 初始化服务端可连接队列
    bootstrap.option(ChannelOption.SO_BACKLOG, 1000);
    // 使用对象池,重用缓冲区
    bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
    // 允许重复使用本地地址和端口
    bootstrap.childOption(ChannelOption.SO_REUSEADDR, true);
    // 当设置该选项以后，连接会测试链接的状态，用于可能长时间没有数据交流的连接
    bootstrap.childOption(ChannelOption.SO_KEEPALIVE, false);
    // 需要等到发送的数据量最大的时候，一次性发送数据，适用于文件传输
    bootstrap.childOption(ChannelOption.TCP_NODELAY, false);
    // 一个连接的远端关闭时本地端是否关闭，默认值为False。值为False时，连接自动关闭
    bootstrap.childOption(ChannelOption.ALLOW_HALF_CLOSURE, false);
    bootstrap.childOption(ChannelOption.RCVBUF_ALLOCATOR, new AdaptiveRecvByteBufAllocator());
    bootstrap.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);

    bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
      @Override
      public void initChannel(SocketChannel ch) {
        final ChannelPipeline pipeline = ch.pipeline();
        // HTTP服务的解码器
        pipeline.addLast(new HttpServerCodec());
        // HTTP消息的合并处理
        pipeline.addLast(new HttpObjectAggregator(1024 * 1024 * 10));
        // 支持大文件传输
        pipeline.addLast(new ChunkedWriteHandler());
        // token拦截器
        pipeline.addLast(interceptorHandler);
        // 业务逻辑
        pipeline.addLast(managerHandler);
        // 读取超时时间
        pipeline.addLast(new ReadTimeoutHandler(readTimeoutSecond));
      }

      @Override
      public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        final Channel channel = ctx.channel();
        if (channel.isActive()) {
          ctx.close();
        }
      }
    });

    ChannelFuture channelFuture = bootstrap.bind(port).syncUninterruptibly().addListener(future -> {
      logger.info("Manager Netty server start successfully! port:{}", port);
    });
    channelFuture.channel().closeFuture().addListener(future -> {
      logger.info("Manager Netty Server Shutdown...");
      bossGroup.shutdownGracefully();
      workerGroup.shutdownGracefully();
    });
  }
}