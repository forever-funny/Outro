package com.demons.manager.schedule.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author : Outro
 * Description :
 **/
@Component
public class PrintJob {

  private static final Logger logger = LoggerFactory.getLogger(PrintJob.class);

  public void run() {
    logger.info("[PrintJob] current time:{}", System.currentTimeMillis());
  }
}
