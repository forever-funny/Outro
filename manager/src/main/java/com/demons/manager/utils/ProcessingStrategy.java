package com.demons.manager.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author: Outro
 * @Desc: 任务添加到线程池的方式,处理策略,阻塞
 */
public class ProcessingStrategy implements RejectedExecutionHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(ProcessingStrategy.class);
    
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        try {
            executor.getQueue().put(r);
        } catch (InterruptedException e) {
            logger.error("ProcessingStrategy put data to queue exception!", e);
            Thread.currentThread().interrupt();
        }
    }
}
