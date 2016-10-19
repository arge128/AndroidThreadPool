package com.alfred.example.androidthreadpool.thread;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;


public interface ExecutorSupplier {

    ExecutorService forPriorityTasks(Priority priority);

    Executor forMainThreadTasks();

    ExecutorService forSerialTasks();

}
