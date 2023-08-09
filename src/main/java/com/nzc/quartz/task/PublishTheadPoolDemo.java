package com.nzc.quartz.task;

import com.nzc.quartz.service.IQuartzJobService;
import com.nzc.quartz.service.impl.QuartzJobServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class PublishTheadPoolDemo {

    @Autowired
    IQuartzJobService quartzJobService;

    private static final ExecutorService executor = Executors.newFixedThreadPool(5);


    @Bean
    public void publishToOta() throws InterruptedException {

        // TODO: 创建多个线程，模拟多个用户下发任务
        //提交一个无返回值的任务（实现了Runnable接口）
        executor.submit(()-> run(10, "qingsong01", false));
        executor.submit(()-> run(10, "qingsong02", true));
        executor.submit(()-> run(15, "qingsong03", false));
        executor.submit(()-> run(30, "qingsong04", false));
        executor.submit(()-> run(25, "qingsong05", false));
        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);

    }


    public void run(Integer delayTime, String taskId, Boolean isScheduler) {
        System.out.println("publishToOta init !");
        if (isScheduler) {
            // 立即下发
            PublishTask.Publish(taskId);
            log.info("===================================已立即下发任务：taskId: " + taskId + "！");
        } else {
            // 定时部署
            Date date = new Date(System.currentTimeMillis() + 1000L * delayTime); // 30秒后
            quartzJobService.saveAndScheduleJob(date, taskId);
            log.info("===================================创建定时下发任务: taskId" + taskId + ", dateTime: " + new Date());
        }
    }


}






