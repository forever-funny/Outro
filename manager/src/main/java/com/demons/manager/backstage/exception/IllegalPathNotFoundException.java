package com.demons.manager.backstage.exception;

/**
 * @author Outro
 * 自定义未知路径异常
 */
public class IllegalPathNotFoundException extends Exception {
    public IllegalPathNotFoundException() {
        super("PATH NOT FOUND");
    }
}
