package com.demons.manager.backstage.exception;

/**
 * @author Outro
 * 自定义未知请求方法异常
 */
public class IllegalMethodNotAllowedException extends Exception {
    public IllegalMethodNotAllowedException() {
        super("METHOD NOT ALLOWED");
    }
}
