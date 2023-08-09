package com.nzc.quartz.service;

import java.util.Date;
import java.util.List;

import com.nzc.quartz.entity.QuartzJob;
import org.quartz.SchedulerException;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 定时任务在线管理
 * @Author: nzc
 */
public interface IQuartzJobService extends IService<QuartzJob> {

	boolean saveAndScheduleJob(QuartzJob quartzJob);

	boolean saveAndScheduleJob(Date date, String param);

	boolean editAndScheduleJob(QuartzJob quartzJob) throws SchedulerException;

	boolean deleteAndStopJob(QuartzJob quartzJob);

	boolean resumeJob(QuartzJob quartzJob);

	void test(String param);
}
