package com.demons.manager.backstage.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Outro
 * 在这里可以做拦截器，验证一些请求的合法性
 * 使用Sharable注解:在bootstrap时只创建一个实例，减少GC。否则每次连接都会new出handler对象
 */
@ChannelHandler.Sharable
@Component
public class InterceptorHandler extends ChannelInboundHandlerAdapter {

  private static final Logger logger = LoggerFactory.getLogger(InterceptorHandler.class);

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) {
    FullHttpRequest request = (FullHttpRequest) msg;
    HttpHeaders headers = request.headers();
    logger.info("show original request headers:{}", headers);
    // 这里可以增加鉴权,限量等业务逻辑
    ctx.fireChannelRead(msg);
  }

  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) {
    ctx.flush();
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    logger.error("InterceptorHandler catch exception!", cause);
    ctx.close();
  }
}
