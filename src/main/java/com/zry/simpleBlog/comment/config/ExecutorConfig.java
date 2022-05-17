package com.zry.simpleBlog.comment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 自定义线程池
 * @author 14470
 */
@Configuration
@EnableAsync
public class ExecutorConfig {
 
	/** Set the ThreadPoolExecutor's core pool size. */
	private int corePoolSize = Runtime.getRuntime().availableProcessors()/2;
	/** Set the ThreadPoolExecutor's maximum pool size. */
	private int maxPoolSize = Runtime.getRuntime().availableProcessors();
	/** Set the capacity for the ThreadPoolExecutor's BlockingQueue. */
	private int queueCapacity = 10;

	@Bean("myThreadPool")
	public Executor mySimpleAsync() {

		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(corePoolSize);
		executor.setMaxPoolSize(maxPoolSize);
		executor.setQueueCapacity(queueCapacity);
		executor.setThreadNamePrefix("MyExecutor-");
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		executor.initialize();
		return executor;
	}
}
