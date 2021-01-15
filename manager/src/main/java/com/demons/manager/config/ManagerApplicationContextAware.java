package com.demons.manager.config;

import com.demons.manager.backstage.netty.NettyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author : Outro
 * @date : 2021-01-15 0015 17:37
 * Description :
 **/
@Component
public class ManagerApplicationContextAware implements ApplicationContextAware {

  @Autowired
  NettyServer nettyServer;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) {
    nettyServer.start();
  }
}
