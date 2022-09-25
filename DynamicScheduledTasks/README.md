# 工程简介
在SpringBoot项目中简单使用定时任务，要借助cron表达式且都提前定义好放在配置文件里，
不能在项目运行中动态修改任务执行时间，实在不太灵活。

这个demo旨在在SpringBoot项目中实现动态定时任务。

总的来说，就是定时任务通过实现SchedulingConfigurer类，然后ScheduledTaskRegistrar类动态设置cron表达式实现。


# 延伸阅读
代码来自：https://mp.weixin.qq.com/s/1JUsgtBc0OGiVwsb0PcHTQ
