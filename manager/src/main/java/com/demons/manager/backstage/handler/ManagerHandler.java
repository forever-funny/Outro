package com.demons.manager.backstage.handler;

import com.demons.manager.utils.NettyHttpRequest;
import com.demons.manager.utils.NettyHttpResponse;
import com.demons.manager.utils.Path;
import com.demons.manager.utils.Response;
import com.demons.manager.backstage.annotation.NettyHttpHandler;
import com.demons.manager.exception.IllegalMethodNotAllowedException;
import com.demons.manager.exception.IllegalPathNotFoundException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author Outro
 * 预先加载所有uri对应的handler,匹配并处理
 */
@ChannelHandler.Sharable
@Component
public class ManagerHandler extends SimpleChannelInboundHandler<FullHttpRequest> implements ApplicationContextAware {
    
    private static final Logger logger = LoggerFactory.getLogger(ManagerHandler.class);
    
    /** 这个map必须是静态的!!! */
    private static Map<Path, IFunctionHandler> functionHandlerMap = new HashMap<>();
    
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) {
        FullHttpRequest copyRequest = request.copy();
        try {
            onReceivedRequest(ctx, new NettyHttpRequest(copyRequest));
        } catch (Exception e) {
            logger.error("controller handler execute exception!", e);
        }
    }
    
    private void onReceivedRequest(ChannelHandlerContext context, NettyHttpRequest request) {
        // 处理请求
        FullHttpResponse response = handleHttpRequest(context, request);
        // 释放资源
        ReferenceCountUtil.release(request);
        // 响应
        context.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
        context.close();
    }
    
    private FullHttpResponse handleHttpRequest(ChannelHandlerContext context, NettyHttpRequest request) {
        try {
            // 匹配到合法的uri,执行业务逻辑处理
            IFunctionHandler functionHandler = matchFunctionHandler(request);
            final HttpHeaders headers = request.headers();
            // 请求头的key和value统一抹平成小写,方便取值和判断
            Map<String, String> tmpHeader = new HashMap<>(headers.size());
            for (Map.Entry<String, String> entry : headers) {
                tmpHeader.put(entry.getKey().toLowerCase(), entry.getValue().toLowerCase());
            }
            getRemoteIpAddress(context, request, tmpHeader);
            if (logger.isDebugEnabled()) {
                logger.debug("HTTP request header information after processing:{}", tmpHeader);
            }
            final byte[] body = getBodyBytes(request);
            // 执行业务逻辑并返回响应
            Response<Object> response = functionHandler.execute(tmpHeader, body);
            return NettyHttpResponse.ok(response.toString());
        } catch (IllegalMethodNotAllowedException error) {
            return NettyHttpResponse.make(HttpResponseStatus.METHOD_NOT_ALLOWED);
        } catch (IllegalPathNotFoundException error) {
            return NettyHttpResponse.make(HttpResponseStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("handleHttpRequest Error!", e);
            return NettyHttpResponse.makeError(e);
        }
    }
    
    private void getRemoteIpAddress(ChannelHandlerContext context, NettyHttpRequest request, Map<String, String> tmpHeader) {
        // 获取ng转发的客户端ip地址
        String clientIp = request.headers().get( "X-Forwarded-For" );
        if  (clientIp == null) {
            InetSocketAddress inetSocketAddress = (InetSocketAddress) context.channel()
                    .remoteAddress();
            clientIp = inetSocketAddress.getAddress().getHostAddress();
        }
        if (clientIp != null) {
            tmpHeader.put("client-ip", clientIp);
        }
    }
    
    private byte[] getBodyBytes(NettyHttpRequest request) {
        // 获取body字节数据
        final ByteBuf byteBuf = request.content();
        final byte[] body = new byte[byteBuf.capacity()];
        byteBuf.readBytes(body);
        return body;
    }
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        // 获取包含NettyHttpHandler注解的所有handler
        Map<String, Object> handlers =
                applicationContext.getBeansWithAnnotation(NettyHttpHandler.class);
        for (Map.Entry<String, Object> entry : handlers.entrySet()) {
            Object handler = entry.getValue();
            Path path = Path.make(handler.getClass().getAnnotation(NettyHttpHandler.class));
            if (functionHandlerMap.containsKey(path)) {
                // 不允许出现相同uri的handler
                logger.error("The same uri handler is not allowed!");
                System.exit(0);
            }
            functionHandlerMap.put(path, (IFunctionHandler) handler);
        }
        logger.info("show uri map:{}", functionHandlerMap);
    }
    
    /**
     * 根据uri返回不同的handler
     * @param request 请求
     * @return 对应的业务
     * @throws Exception 异常
     */
    private IFunctionHandler matchFunctionHandler(NettyHttpRequest request) throws 
            IllegalPathNotFoundException, IllegalMethodNotAllowedException {
        // 没看懂...
        AtomicBoolean matched = new AtomicBoolean(false);
        Stream<Path> stream = functionHandlerMap.keySet().stream()
                .filter(((Predicate<Path>) path -> {
                    // 过滤 Path URI 不匹配的
                    if (request.matched(path.getUri(), path.isEqual())) {
                        matched.set(true);
                        return matched.get();
                    }
                    return false;
                }).and(path -> {
                    // 过滤 Method 匹配的
                    return request.isAllowed(path.getMethod());
                }));
        Optional<Path> optional = stream.findFirst();
        stream.close();
        if (!optional.isPresent() && !matched.get()) {
            throw new IllegalPathNotFoundException();
        }
        if (!optional.isPresent() && matched.get()) {
            throw new IllegalMethodNotAllowedException();
        }
        return functionHandlerMap.get(optional.get());
    }
    
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("ManagerHandler catch exception!", cause);
        ctx.close();
    }
}