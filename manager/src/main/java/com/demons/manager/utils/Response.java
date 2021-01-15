package com.demons.manager.utils;

/**
 * @author Outro
 */
public final class Response {
    
    private final int code;
    private final String reason;
    
    public Response(int code, String reason) {
        this.code = code;
        this.reason = reason;
    }
    
    @Override
    public String toString() {
        return "{\"code\":" + code + ",\"" + "reason\":\"" + reason + "\"}";
    }
    
    public int getCode() {
        return code;
    }
    
    public String getReason() {
        return reason;
    }
    
    public static Response ok(String reason) {
        return new Response(200, reason);
    }

    public static Response fail(int code, String reason) {
        return new Response(code, reason);
    }
}