/*
 * @author yangjiewei
 * @date 2022/9/25 14:18
 */
package com.yangjiewei.scheduledtasks.task;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.core.config.Scheduled;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@Slf4j
@Component
@PropertySource("classpath:/task-config.ini")
public class ScheduleTask implements SchedulingConfigurer {

    @Value("${printTime.cron}")
    private String cron;

//    @Value("${server.port}")
//    private String test;

    /**
     * 区别于CronTrigger触发器，该触发器可随意设置循环间隔时间，不像cron表达式只能定义小于等于间隔59秒
     * 单位是毫秒 这是10s
     */
    private Long timer = 10000L;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        // 动态使用cron表达式设置循环间隔
        scheduledTaskRegistrar.addTriggerTask(
            () -> log.info("当前时间是:{}", LocalDateTime.now()),
            triggerContext -> {
                /*// 使用CronTrigger触发器，可动态修改cron表达式来操作循环规则
                CronTrigger cronTrigger = new CronTrigger(cron);
                return cronTrigger.nextExecutionTime(triggerContext);*/

                // 使用不同的触发器，为设置循环时间的关键，区别于CronTrigger触发器，该触发器可随意设置循环间隔时间，单位为毫秒
                PeriodicTrigger periodicTrigger = new PeriodicTrigger(timer);
                return periodicTrigger.nextExecutionTime(triggerContext);
            }
        );
    }
}
