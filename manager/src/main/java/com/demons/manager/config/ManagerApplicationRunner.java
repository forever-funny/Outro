package com.demons.manager.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author : Outro
 * @date : 2021-01-15 0015 17:38
 * Description :
 **/
@Component
public class ManagerApplicationRunner implements ApplicationRunner {

  private static final Logger logger = LoggerFactory.getLogger(ManagerApplicationRunner.class);

  @Override
  public void run(ApplicationArguments args) throws Exception {
    logger.info("manager runner start...");
  }
}
