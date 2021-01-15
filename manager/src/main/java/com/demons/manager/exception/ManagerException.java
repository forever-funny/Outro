package com.demons.manager.exception;

/**
 * @author : Outro
 * Description : 自定义异常
 **/
public class ManagerException extends Exception {
  public ManagerException() {
    super();
  }

  public ManagerException(String message) {
    super(message);
  }

  public ManagerException(String message, Throwable cause) {
    super(message, cause);
  }

  public ManagerException(Throwable cause) {
    super(cause);
  }

  protected ManagerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
