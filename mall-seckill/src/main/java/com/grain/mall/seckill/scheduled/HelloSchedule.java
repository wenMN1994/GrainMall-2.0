package com.grain.mall.seckill.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/9/4 16:16
 * @description：
 * @modified By：
 * @version: $
 */
//@Slf4j
//@Component
//@EnableAsync
//@EnableScheduling
public class HelloSchedule {

    /**
     * springboot开启定时任务：
     *
     * 1、@EnableScheduling 开启定时任务
     *
     * 2、@Scheduled(cron = "* * * * * ?") 开启一个定时任务
     *
     * 3、自动配置类 TaskSchedulingAutoConfiguration 属性绑定在TaskSchedulingProperties
     *
     * springboot开启异步任务：
     *
     * 1、@EnableAsync 开启异步任务功能
     *
     * 2、@Async 给希望异步执行的方法上标注
     *
     * 3、自动配置类 TaskExecutionAutoConfiguration 属性绑定在TaskExecutionProperties
     *
     * 注意：
     *
     * 1、spring中的cron表达式6位组成，不允许第7位的年；
     *
     * 2、在周几的位置，1-7代表周一到周日；
     *
     * 3、定时任务不应该改阻塞，默认是阻塞的
     *
     * ​	1）、可以让业务运行以异步的方式，自己提交到线程池
     *
     * ```
     * CompletableFuture.runAsync(()->{
     *     xxxService.hello();
     * },executor);
     * ```
     *
     * ​	2）、支持定时任务线程池，设置TaskSchedulingProperties
     *
     * ```
     * spring.task.scheduling.pool.size=5 （不稳定，容易失效）
     * ```
     *
     * ​	3）、让定时任务异步执行
     *
     * 解决：使用异步+定时任务来完成定时任务阻塞的功能
     */
//    @Async
//    @Scheduled(cron = "* * * ? * 5")
//    public void hello(){
//        log.info("hello...");
//    }
}
