package com.pactera.demo.common.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @description: 定时任务
 * @author: LiuZhiJun
 * @create: 2019-08-12 15:17
 **/
@Component
public class SchedulerTask {

    @Autowired
    ValueOperations<String, String> valueOperations;

    @Scheduled(cron = "0 */1 * * * ? ")
    private void proces(){
//        if (valueOperations.get("timingService") == null) {
//            if(valueOperations.setIfAbsent("timingService", "timingService", 10, TimeUnit.SECONDS)){
//                System.out.println("跑业务逻辑");
//            }
//        }
    }
}
