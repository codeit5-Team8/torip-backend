package com.codeit.torip.common.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class AsyncConfig {

    @Bean
    public ThreadPoolTaskExecutor messageThreadPoolTaskExecutor() {
        var taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setThreadNamePrefix("async-task-");
        taskExecutor.setCorePoolSize(200);
        taskExecutor.setMaxPoolSize(400);
        taskExecutor.afterPropertiesSet();
        return taskExecutor;
    }

}
