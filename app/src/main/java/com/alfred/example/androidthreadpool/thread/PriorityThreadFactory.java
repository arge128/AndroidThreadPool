package com.alfred.example.androidthreadpool.thread;

import java.util.concurrent.ThreadFactory;

public class PriorityThreadFactory implements ThreadFactory {
    private final Priority priority;
    private static int index;

    public PriorityThreadFactory(Priority threadPriority) {
        priority = threadPriority;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setPriority(priority.getPriority());
        t.setName("t-" + priority.getPriority() + "-" + getNextIndex());
        return t;
    }

    private int getNextIndex() {
        return index++;
    }
}
