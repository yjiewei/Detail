/*
 * @author yangjiewei
 * @date 2022/9/25 14:29
 */
package com.yangjiewei.scheduledtasks.controller;

import com.yangjiewei.scheduledtasks.task.ScheduleTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/scheduleTask")
public class ScheduleTaskController {

    @Resource
    private ScheduleTask scheduleTask;

    /**
     * 2022-09-25 14:37:18.639  INFO 2308 --- [           main] c.y.s.ScheduledTasksApplication          : 动态定时任务启动完成...
     * 2022-09-25 14:37:20.018  INFO 2308 --- [pool-2-thread-1] c.y.scheduledtasks.task.ScheduleTask     : 当前时间是:2022-09-25T14:37:20.018766
     * 2022-09-25 14:37:30.002  INFO 2308 --- [pool-2-thread-1] c.y.scheduledtasks.task.ScheduleTask     : 当前时间是:2022-09-25T14:37:30.001571800
     * 2022-09-25 14:37:40.004  INFO 2308 --- [pool-2-thread-1] c.y.scheduledtasks.task.ScheduleTask     : 当前时间是:2022-09-25T14:37:40.004079900
     * 2022-09-25 14:37:50.013  INFO 2308 --- [pool-2-thread-1] c.y.scheduledtasks.task.ScheduleTask     : 当前时间是:2022-09-25T14:37:50.013269300
     * 2022-09-25 14:38:00.010  INFO 2308 --- [pool-2-thread-1] c.y.scheduledtasks.task.ScheduleTask     : 当前时间是:2022-09-25T14:38:00.010825700
     * 2022-09-25 14:38:10.004  INFO 2308 --- [pool-2-thread-1] c.y.scheduledtasks.task.ScheduleTask     : 当前时间是:2022-09-25T14:38:10.004075200
     * 2022-09-25 14:38:20.013  INFO 2308 --- [pool-2-thread-1] c.y.scheduledtasks.task.ScheduleTask     : 当前时间是:2022-09-25T14:38:20.013615500
     * 2022-09-25 14:38:30.014  INFO 2308 --- [pool-2-thread-1] c.y.scheduledtasks.task.ScheduleTask     : 当前时间是:2022-09-25T14:38:30.014453
     * 2022-09-25 14:38:40.003  INFO 2308 --- [pool-2-thread-1] c.y.scheduledtasks.task.ScheduleTask     : 当前时间是:2022-09-25T14:38:40.003140700
     * 2022-09-25 14:38:50.002  INFO 2308 --- [pool-2-thread-1] c.y.scheduledtasks.task.ScheduleTask     : 当前时间是:2022-09-25T14:38:50.002016400
     * 2022-09-25 14:39:00.012  INFO 2308 --- [pool-2-thread-1] c.y.scheduledtasks.task.ScheduleTask     : 当前时间是:2022-09-25T14:39:00.012856700
     * 2022-09-25 14:39:10.006  INFO 2308 --- [pool-2-thread-1] c.y.scheduledtasks.task.ScheduleTask     : 当前时间是:2022-09-25T14:39:10.006380600
     * 2022-09-25 14:39:20.004  INFO 2308 --- [pool-2-thread-1] c.y.scheduledtasks.task.ScheduleTask     : 当前时间是:2022-09-25T14:39:20.004498100
     * 2022-09-25 14:39:23.678  INFO 2308 --- [nio-8080-exec-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
     * 2022-09-25 14:39:23.678  INFO 2308 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
     * 2022-09-25 14:39:23.691  INFO 2308 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 13 ms
     * 2022-09-25 14:39:23.738  INFO 2308 --- [nio-8080-exec-1] c.y.s.controller.ScheduleTaskController  : 更新执行任务的时间表达式
     * 2022-09-25 14:39:30.004  INFO 2308 --- [pool-2-thread-1] c.y.scheduledtasks.task.ScheduleTask     : 当前时间是:2022-09-25T14:39:30.004791800
     * 2022-09-25 14:39:45.011  INFO 2308 --- [pool-2-thread-1] c.y.scheduledtasks.task.ScheduleTask     : 当前时间是:2022-09-25T14:39:45.011559600
     * 2022-09-25 14:40:00.014  INFO 2308 --- [pool-2-thread-1] c.y.scheduledtasks.task.ScheduleTask     : 当前时间是:2022-09-25T14:40:00.014261200
     * 2022-09-25 14:40:15.010  INFO 2308 --- [pool-2-thread-1] c.y.scheduledtasks.task.ScheduleTask     : 当前时间是:2022-09-25T14:40:15.010434800
     *
     * http://localhost:8080/scheduleTask/updateCron?cron=0/15 * * * * ?
     *
     * @param cron 表达式
     * @return
     */
    @GetMapping("/updateCron")
    public String updateCron(String cron) {
        log.info("更新执行任务的时间表达式");
        scheduleTask.setCron(cron);
        return "更新Cron成功！";
    }


    @GetMapping("/updateTimer")
    public String updateTimer(Long timer) {
        log.info("更新执行任务的timer表达式:{}", timer);
        scheduleTask.setTimer(timer);
        return "更新timer成功";
    }

}
