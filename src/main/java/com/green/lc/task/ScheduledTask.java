
/*
 * User:liveGreen
 * Date: 2019/5/27
 */

package com.green.lc.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 定时任务
 * 注解@Scheduled任务方法、@EnableScheduling注解写于启动类
 * 启动类写了@EnableScheduling注解，被注解的定时任务才会生效哦
 */
@Component
public class ScheduledTask {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 1000)
    public void getTask(){
        System.out.println("任务一，从配置文件加载任务信息，当前时间" + dateFormat.format(new Date()));
    }

    @Scheduled(cron = "${jobs.cron}")
    public void getTaskTwo(){
        System.out.println("任务二，从配置文件加载任务信息，当前时间" + dateFormat.format(new Date()));
    }


}
