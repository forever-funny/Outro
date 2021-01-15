package com.demons.manager.utils;

import com.alibaba.fastjson.JSON;

/**
 * @author Outro
 */
public class Response<T> {

  private final int code;
  private final String reason;
  private T result;

  /***
   * 后台使用的响应构造
   * @param code 错误码
   * @param reason 原因
   */
  public Response(int code, String reason) {
    this.code = code;
    this.reason = reason;
  }

  /***
   * 接口使用的响应构造
   * @param code 错误码
   * @param reason 原因
   * @param result 结果
   */
  public Response(int code, String reason, T result) {
    this.code = code;
    this.reason = reason;
    this.result = result;
  }

  public int getCode() {
    return code;
  }

  public String getReason() {
    return reason;
  }

  public T getResult() {
    return result;
  }

  public static Response<Object> ok(String reason) {
    return new Response<>(200, reason);
  }

  public static Response<Object> fail(int code, String reason) {
    return new Response<>(code, reason);
  }

  @Override
  public String toString() {
    return JSON.toJSONString(this);
  }
}