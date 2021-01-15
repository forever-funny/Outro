package com.demons.manager.utils;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;

import java.util.Objects;

/**
 * @author Outro
 */
public class NettyHttpRequest implements FullHttpRequest {

    private final FullHttpRequest realRequest;

    public NettyHttpRequest(FullHttpRequest request){
        this.realRequest = request;
    }
    
    public boolean isAllowed(String method){
        return method().name().equalsIgnoreCase(method);
    }

    public boolean matched(String path,boolean equal){
        String uri = uri().toLowerCase();
        return equal ? Objects.equals(path,uri) : uri.startsWith(path);
    }

    @Override
    public ByteBuf content() {
        return realRequest.content();
    }

    @Override
    public HttpHeaders trailingHeaders() {
        return realRequest.trailingHeaders();
    }

    @Override
    public FullHttpRequest copy() {
        return realRequest.copy();
    }

    @Override
    public FullHttpRequest duplicate() {
        return realRequest.duplicate();
    }

    @Override
    public FullHttpRequest retainedDuplicate() {
        return realRequest.retainedDuplicate();
    }

    @Override
    public FullHttpRequest replace(ByteBuf byteBuf) {
        return realRequest.replace(byteBuf);
    }

    @Override
    public FullHttpRequest retain(int i) {
        return realRequest.retain(i);
    }

    @Override
    public int refCnt() {
        return realRequest.refCnt();
    }

    @Override
    public FullHttpRequest retain() {
        return realRequest.retain();
    }

    @Override
    public FullHttpRequest touch() {
        return realRequest.touch();
    }

    @Override
    public FullHttpRequest touch(Object o) {
        return realRequest.touch(o);
    }

    @Override
    public boolean release() {
        return realRequest.release();
    }

    @Override
    public boolean release(int i) {
        return realRequest.release(i);
    }

    @Override
    public HttpVersion getProtocolVersion() {
        return protocolVersion();
    }

    @Override
    public HttpVersion protocolVersion() {
        return realRequest.protocolVersion();
    }

    @Override
    public FullHttpRequest setProtocolVersion(HttpVersion httpVersion) {
        return realRequest.setProtocolVersion(httpVersion);
    }

    @Override
    public HttpHeaders headers() {
        return realRequest.headers();
    }

    @Override
    public HttpMethod getMethod() {
        return method();
    }

    @Override
    public HttpMethod method() {
        return realRequest.method();
    }

    @Override
    public FullHttpRequest setMethod(HttpMethod httpMethod) {
        return realRequest.setMethod(httpMethod);
    }

    @Override
    public String getUri() {
        return uri();
    }

    @Override
    public String uri() {
        return realRequest.uri();
    }

    @Override
    public FullHttpRequest setUri(String s) {
        return realRequest.setUri(s);
    }

    @Override
    public DecoderResult getDecoderResult() {
        return decoderResult();
    }

    @Override
    public DecoderResult decoderResult() {
        return realRequest.decoderResult();
    }

    @Override
    public void setDecoderResult(DecoderResult decoderResult) {
        realRequest.setDecoderResult(decoderResult);
    }
}