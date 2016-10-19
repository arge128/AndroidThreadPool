package com.alfred.example.androidthreadpool.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class DefaultExecutorSupplier implements ExecutorSupplier {

    private static final int DEFAULT_MAX_NUM_THREADS = 2 * Runtime.getRuntime().availableProcessors();
    private Map<Priority, ExecutorService> priorityExecutorServiceMap = new HashMap<>();
    private final Executor mMainThreadExecutor;
    private ExecutorService serialTaskExecutorService;

    public DefaultExecutorSupplier() {
        for (Priority priority : Priority.values()) {
            priorityExecutorServiceMap.put(priority, new ThreadPoolExecutor(0, DEFAULT_MAX_NUM_THREADS, 60L, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(),
                    new PriorityThreadFactory(priority)));
        }
        mMainThreadExecutor = new MainThreadExecutor();
        serialTaskExecutorService = Executors.newSingleThreadExecutor(new PriorityThreadFactory(Priority.BACKGROUND));
    }

    @Override
    public ExecutorService forPriorityTasks(Priority priority) {
        return priorityExecutorServiceMap.get(priority);
    }

    @Override
    public Executor forMainThreadTasks() {
        return mMainThreadExecutor;
    }

    @Override
    public ExecutorService forSerialTasks() {
        return serialTaskExecutorService;
    }

}
