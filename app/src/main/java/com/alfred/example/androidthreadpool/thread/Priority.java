package com.alfred.example.androidthreadpool.thread;

public enum Priority {
    /**
     * Lowest priority level. Used for background tasks. Nice value is 10;
     */
    BACKGROUND(4),

    /**
     * Normal priority level. Used for warming of data that might soon get
     * visible. Same priority as main thread. Nice value is 0;
     */
    NORMAL(5);

    /**
     * High priority level. Used for forground jobs. Nice value is -2;
     */
//    HIGH(6),

    /**
     * Highest priority level. Used for data that are required instantly(mainly
     * for emergency). Nice value is -4;
     */
//    IMMEDIATE(7);
    private int priority;

    Priority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
