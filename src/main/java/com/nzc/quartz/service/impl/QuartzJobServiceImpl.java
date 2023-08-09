package com.nzc.quartz.service.impl;

import java.util.Date;

import com.nzc.quartz.common.CommonConstant;
import com.nzc.quartz.entity.QuartzJob;
import com.nzc.quartz.exception.MyException;
import com.nzc.quartz.job.PublishTaskJob;
import com.nzc.quartz.mapper.QuartzJobMapper;
import com.nzc.quartz.service.IQuartzJobService;
import com.nzc.quartz.util.CronUtil;
import com.nzc.quartz.util.DateUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 定时任务在线管理
 * @Author: nzc
 */
@Slf4j
@Service
public class QuartzJobServiceImpl extends ServiceImpl<QuartzJobMapper, QuartzJob> implements IQuartzJobService {

	@Autowired
	private QuartzJobMapper quartzJobMapper;

	@Autowired
	private Scheduler scheduler;


	/**
	 * 保存&启动定时任务
	 */
	@Override
	public boolean saveAndScheduleJob(QuartzJob quartzJob) {
		if (CommonConstant.STATUS_NORMAL.equals(quartzJob.getStatus())) {
			// 定时器添加
			this.schedulerAdd(quartzJob.getJobClassName().trim(), quartzJob.getCronExpression().trim(), quartzJob.getParameter());
		}
		// DB设置修改
		return this.save(quartzJob);
	}

	/**
	 * 前端传参（date）
	 * 自行组装 param
	 * @param date
	 * @param param
	 */
	@Override
	public boolean saveAndScheduleJob(Date date, String param) {
		String cron = CronUtil.getCron(date);
		String className =  PublishTaskJob.class.getName();
		QuartzJob quartzJob = QuartzJob.builder()
				.createBy("user_song")
				.createTime(DateUtils.getCurrentTime())
				.cronExpression(cron)
				.jobClassName(className)
				.description(date + "执行下发任务")
				.parameter(param)
				.status(0)
				.build();
		saveAndScheduleJob(quartzJob);

		return false;
	}

	/**
	 * 恢复定时任务
	 */
	@Override
	public boolean resumeJob(QuartzJob quartzJob) {
		schedulerDelete(quartzJob.getJobClassName().trim());
		schedulerAdd(quartzJob.getJobClassName().trim(), quartzJob.getCronExpression().trim(), quartzJob.getParameter());
		quartzJob.setStatus(CommonConstant.STATUS_NORMAL);

		return this.updateById(quartzJob);
	}

	@Override
	public void test(String param) {
		System.out.println("param====>"+param);
	}

	/**
	 * 编辑&启停定时任务
	 * @throws SchedulerException 
	 */
	@Override
	public boolean editAndScheduleJob(QuartzJob quartzJob) throws SchedulerException {
		if (CommonConstant.STATUS_NORMAL.equals(quartzJob.getStatus())) {
			schedulerDelete(quartzJob.getJobClassName().trim());
			schedulerAdd(quartzJob.getJobClassName().trim(), quartzJob.getCronExpression().trim(), quartzJob.getParameter());
		}else{
			scheduler.pauseJob(JobKey.jobKey(quartzJob.getJobClassName().trim()));
		}
		return this.updateById(quartzJob);
	}

	/**
	 * 删除&停止删除定时任务
	 */
	@Override
	public boolean deleteAndStopJob(QuartzJob job) {
		schedulerDelete(job.getJobClassName().trim());
		return this.removeById(job.getId());
	}

	/**
	 * 添加定时任务
	 * 
	 * @param jobClassName
	 * @param cronExpression
	 * @param parameter
	 */
	private void schedulerAdd(String jobClassName, String cronExpression, String parameter) {
		try {
			// 启动调度器
			scheduler.start();

			// 构建job信息
			JobKey jobKey = new JobKey("JobName-TaskId-" + parameter, "GroupPublishTask-" + jobClassName);

			JobDetail jobDetail = JobBuilder.newJob(getClass(jobClassName).getClass())
					.withIdentity(jobKey)
					.usingJobData("parameter", parameter)
					.storeDurably(true)  // 持久化
					.build();

			// 表达式调度构建器(即任务执行的时间)
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

			// 按新的cronExpression表达式构建一个新的trigger
			CronTrigger trigger = TriggerBuilder.newTrigger()
					.withDescription("Publish Trigger")
					.withIdentity("Trigger-"+ parameter, "Trigger-Group")
					.withSchedule(scheduleBuilder)
					.build();

			scheduler.scheduleJob(jobDetail, trigger);
		} catch (SchedulerException e) {
			throw new MyException("创建定时任务失败", e);
		} catch (RuntimeException e) {
			throw new MyException(e.getMessage(), e);
		}catch (Exception e) {
			throw new MyException("后台找不到该类名：" + jobClassName, e);
		}
	}

	/**
	 * 删除定时任务
	 * 
	 * @param jobClassName
	 */
	private void schedulerDelete(String jobClassName) {
		try {
			/*使用给定的键暂停Trigger 。*/
			scheduler.pauseTrigger(TriggerKey.triggerKey(jobClassName));
			/*从调度程序中删除指示的Trigger */
			scheduler.unscheduleJob(TriggerKey.triggerKey(jobClassName));
			/*从 Scheduler 中删除已识别的Job - 以及任何关联的Trigger */
			scheduler.deleteJob(JobKey.jobKey(jobClassName));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new MyException("删除定时任务失败");
		}
	}

	private static Job getClass(String classname) throws Exception {
		Class<?> class1 = Class.forName(classname);
		return (Job) class1.newInstance();
	}




}
