package com.alfred.example.androidthreadpool.thread;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by alfred on 17/10/2016.
 */

public enum ThreadPoolManager implements ExecutorService {
    POOL;
    private ExecutorSupplier executorSupplier;

    ThreadPoolManager() {
        executorSupplier = new DefaultExecutorSupplier();
    }

    public void setExecutorSupplier(ExecutorSupplier executorSupplier) {
        this.executorSupplier = executorSupplier;
    }

    public ExecutorSupplier getExecutorSupplier() {
        return executorSupplier;
    }

    @Override
    public void execute(Runnable runnable) {
        execute(runnable, Priority.BACKGROUND);
    }

    public void execute(Runnable runnable, Priority priority) {
        getService(priority).execute(runnable);
    }

    public void executeOnMainThread(Runnable runnable) {
        executorSupplier.forMainThreadTasks().execute(runnable);
    }

    public void executeSerial(Runnable runnable) {
        executorSupplier.forSerialTasks().execute(runnable);
    }

    @NonNull
    @Override
    public Future<?> submit(Runnable runnable) {
        return submit(runnable, Priority.BACKGROUND);
    }

    public Future<?> submit(Runnable runnable, Priority priority) {
        return getService(priority).submit(runnable);
    }

    @NonNull
    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return submit(task, Priority.BACKGROUND);
    }

    public <T> Future<T> submit(Callable<T> task, Priority priority) {
        return getService(priority).submit(task);

    }

    @NonNull
    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        return submit(task, result, Priority.BACKGROUND);
    }

    public <T> Future<T> submit(Runnable task, T result, Priority priority) {
        return getService(priority).submit(task, result);
    }

    @NonNull
    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        return invokeAll(tasks, Priority.BACKGROUND);
    }

    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, Priority priority) throws InterruptedException {
        return getService(priority).invokeAll(tasks);
    }

    @NonNull
    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
        return invokeAll(tasks, timeout, unit, Priority.BACKGROUND);
    }

    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit, Priority priority) throws InterruptedException {
        return getService(priority).invokeAll(tasks, timeout, unit);
    }

    @NonNull
    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        return invokeAny(tasks, Priority.BACKGROUND);
    }

    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, Priority priority) throws InterruptedException, ExecutionException {
        return getService(priority).invokeAny(tasks);
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return invokeAny(tasks, timeout, unit, Priority.BACKGROUND);
    }

    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit, Priority priority) throws InterruptedException, ExecutionException, TimeoutException {
        return getService(priority).invokeAny(tasks, timeout, unit);
    }

    private ExecutorService getService(Priority priority) {
        if (executorSupplier == null) {
            throw new NullPointerException("No Executor Supplier.");
        } else {
            ExecutorService service = executorSupplier.forPriorityTasks(priority);
            if (service == null) {
                throw new NullPointerException("No " + priority.name() + " ThreadPool.");
            } else {
                return service;
            }
        }
    }


    @Override
    public void shutdown() {
        if (executorSupplier != null) {
            for (Priority p : Priority.values()) {
                ExecutorService service = executorSupplier.forPriorityTasks(p);
                if (service != null) {
                    service.shutdown();
                }
            }
        }
    }

    @NonNull
    @Override
    public List<Runnable> shutdownNow() {
        List<Runnable> result = new ArrayList<>();
        if (executorSupplier != null) {
            for (Priority p : Priority.values()) {
                ExecutorService service = executorSupplier.forPriorityTasks(p);
                if (service != null) {
                    result.addAll(service.shutdownNow());
                }
            }
        }
        return result;
    }

    @Override
    public boolean isShutdown() {
        boolean result = true;
        if (executorSupplier != null) {
            for (Priority p : Priority.values()) {
                ExecutorService service = executorSupplier.forPriorityTasks(p);
                if (service != null) {
                    result = result && service.isShutdown();
                }
            }
        }
        return result;
    }

    @Override
    public boolean isTerminated() {
        boolean result = true;
        if (executorSupplier != null) {
            for (Priority p : Priority.values()) {
                ExecutorService service = executorSupplier.forPriorityTasks(p);
                if (service != null) {
                    result = result && service.isTerminated();
                }
            }
        }
        return result;
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        boolean result = true;
        if (executorSupplier != null) {
            for (Priority p : Priority.values()) {
                ExecutorService service = executorSupplier.forPriorityTasks(p);
                if (service != null) {
                    result = result && service.awaitTermination(timeout, unit);
                }
            }
        }
        return result;
    }


}
