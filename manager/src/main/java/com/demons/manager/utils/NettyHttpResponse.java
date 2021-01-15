package com.demons.manager.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

import static io.netty.handler.codec.http.HttpHeaderNames.*;

/**
 * @author Outro
 * Netty响应信息
 */
public class NettyHttpResponse extends DefaultFullHttpResponse {

    private static final String CONTENT_ERROR_404 = "{\"code\":404,\"reason\":\"Request path not found\"}";
    private static final String CONTENT_ERROR_405 = "{\"code\":405,\"reason\":\"Method Not Allowed\"}";
    private static final String CONTENT_ERROR_500 = "{\"code\":500,\"reason\":\"%s\"}";
    private String content;

    private NettyHttpResponse(HttpResponseStatus status, ByteBuf buffer) {
        super(HttpVersion.HTTP_1_1, status,buffer);
        headers().set(CONTENT_TYPE, "application/json");
        headers().setInt(CONTENT_LENGTH, content().readableBytes());
        // 支持CORS 跨域访问
        headers().set(ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        headers().set(ACCESS_CONTROL_ALLOW_HEADERS, "Origin, X-Requested-With, Content-Type, Accept, RCS-ACCESS-TOKEN");
        headers().set(ACCESS_CONTROL_ALLOW_METHODS, "GET,POST,PUT,DELETE");
    }

    public static FullHttpResponse make(HttpResponseStatus status) {
        if (HttpResponseStatus.INTERNAL_SERVER_ERROR == status) {
            return NettyHttpResponse.make(HttpResponseStatus.INTERNAL_SERVER_ERROR, 
                    String.format(CONTENT_ERROR_500, "Internal Server Error, Please Resend the Data"));
        }
        if (HttpResponseStatus.NOT_FOUND == status) {
            return NettyHttpResponse.make(HttpResponseStatus.NOT_FOUND, CONTENT_ERROR_404);
        }
        if (HttpResponseStatus.METHOD_NOT_ALLOWED == status) {
            return NettyHttpResponse.make(HttpResponseStatus.METHOD_NOT_ALLOWED, CONTENT_ERROR_405);
        }
        return NettyHttpResponse.make(HttpResponseStatus.OK, "");
    }

    public static FullHttpResponse makeError(Exception exception) {
        String message = exception.getClass().getName() + ":" + exception.getMessage();
        return NettyHttpResponse.make(HttpResponseStatus.INTERNAL_SERVER_ERROR, String.format(CONTENT_ERROR_500, message));
    }

    public static FullHttpResponse ok(String content) {
        return make(HttpResponseStatus.OK,content);
    }

    public static FullHttpResponse make(HttpResponseStatus status, String content) {
        byte[] body = content.getBytes();
        NettyHttpResponse response = new NettyHttpResponse(status, Unpooled.wrappedBuffer(body));
        response.content = content;
        return response;
    }

    @Override
    public String toString(){
        return protocolVersion().toString() + " " + status().toString() + "\n" +
                CONTENT_TYPE + ": " + headers().get(CONTENT_TYPE) + "\n" +
                CONTENT_LENGTH + ": " + headers().get(CONTENT_LENGTH) + "\n" +
                "content-body" + ": " + content + "\n";
    }
    
    @Override
    public int hashCode() {
        return super.hashCode();
    }
    
    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
