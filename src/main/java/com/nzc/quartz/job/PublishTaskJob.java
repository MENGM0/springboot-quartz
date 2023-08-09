package com.nzc.quartz.job;

import com.nzc.quartz.task.PublishTask;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

@Slf4j
public class PublishTaskJob implements Job {


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // 入参
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String parameter = jobDataMap.getString("parameter");

        PublishTask.Publish(parameter);
//        log.info("qingsong 的下发定时任务!  时间:" + new Date());
    }

}
