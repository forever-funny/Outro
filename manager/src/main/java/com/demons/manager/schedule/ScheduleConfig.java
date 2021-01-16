package com.demons.manager.schedule;

import com.demons.manager.utils.ProcessingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

/**
 * @author Outro
 * 配置任务调度的线程池线程个数
 */
@Configuration
public class ScheduleConfig implements SchedulingConfigurer {

  private static final Logger logger = LoggerFactory.getLogger(ScheduleConfig.class);

  @Value("${schedule.thread-number:1}")
  int threadNumber;

  private final ThreadFactory factory = new ThreadFactory() {
    int counter;
    @Override
    public Thread newThread(Runnable r) {
      final Thread t = new Thread(r);
      t.setName("schedule-" + counter++);
      return t;
    }
  };

  @Override
  public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
    logger.info("schedule task thread number is:{}", threadNumber);
    ScheduledExecutorService pool = new ScheduledThreadPoolExecutor(threadNumber, factory, new ProcessingStrategy());
    scheduledTaskRegistrar.setScheduler(pool);
  }
}
