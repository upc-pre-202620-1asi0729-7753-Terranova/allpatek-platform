package com.allpatekplatform.shared.infrastructure.concurrency.loom.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Configures asynchronous task execution using virtual threads.
 */
@Configuration
@EnableAsync
public class VirtualThreadsConfiguration {

    @Bean(name = "applicationTaskExecutor")
    public TaskExecutor applicationTaskExecutor() {
        var executor = new SimpleAsyncTaskExecutor("loom-exec-");
        executor.setVirtualThreads(true);
        return executor;
    }
}
