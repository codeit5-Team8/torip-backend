package com.codeit.torip;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

@Slf4j
@EnableJpaAuditing
@SpringBootApplication
@RequiredArgsConstructor
public class ToripApplication {

    private final ThreadPoolTaskExecutor taskExecutor;

    public static void main(String[] args) {
        SpringApplication.run(ToripApplication.class, args);
    }

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
        Locale.setDefault(Locale.KOREA);
    }

    @PreDestroy
    public void gracefulShutDown() {
        try {
            taskExecutor.getThreadPoolExecutor().shutdown();
            if (!taskExecutor.getThreadPoolExecutor().awaitTermination(120, TimeUnit.SECONDS)) {
                log.info("Timeout reached while waiting for async tasks to complete.");
            } else {
                log.info("All async tasks completed.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // 예외가 발생하면 인터럽트 처리
            log.error("Graceful shutdown interrupted.");
            log.error("", e);
        }
    }

}
