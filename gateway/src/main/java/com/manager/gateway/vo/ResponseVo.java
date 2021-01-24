package com.manager.gateway.vo;

/**
 * @author : Outro
 * Description : 网关统一响应格式
 **/
public class ResponseVo {

  public ResponseVo(int code, String reason) {
    this.code = code;
    this.reason = reason;
  }

  private int code;
  private String reason;

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  @Override
  public String toString() {
    return "{\"code\":" +  code +
        ", \"reason\":\"" + reason + "\"}";
  }
}
