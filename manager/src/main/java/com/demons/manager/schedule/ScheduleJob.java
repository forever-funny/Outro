package com.demons.manager.schedule;

import com.demons.manager.schedule.jobs.PrintJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author : Outro
 * Description : 定时任务集合
 **/
@Component
public class ScheduleJob {

    @Autowired
    PrintJob printJob;

    @Scheduled(fixedDelayString = "${UPDATE_PRINT_INTERVAL:5000}")
    public void printJob() {
        printJob.run();
    }

}
