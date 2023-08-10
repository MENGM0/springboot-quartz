package com.nzc.quartz.task;

import lombok.extern.slf4j.Slf4j;


/**
 * 业务代码
 */

@Slf4j
public class PublishTask {

    private String Param;

    public static void Publish(String param){

        log.info("***************************定时下发事件触发， taskId： " + param);


        log.info("test1");
    }


}
