package com.demons.manager.utils;

import com.alibaba.fastjson.JSON;

/**
 * @author Outro
 * 统一定义响应格式,内容,错误码
 */
public class Response<T> {

  private final int code;
  private final String reason;
  private T result;

  public enum ERROR_CODE {
    OK(200),
    NET_EXCEPTION(400),
    SERVER_ERROR(500),
    BUSINESS(20001),
    DATABASE(20002);

    int errorCode;

    ERROR_CODE(int errorCode) {
      this.errorCode = errorCode;
    }

    public int getCode() {
      return errorCode;
    }
  }

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