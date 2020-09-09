package com.twinmask.gps.comm.lifecycle;

import org.springframework.context.SmartLifecycle;

public abstract class GpsSmartLifecycle implements SmartLifecycle {

    static final int DEFAULT_PHASE = Integer.MAX_VALUE;

    volatile boolean isRunning = false;

    @Override
    public boolean isAutoStartup() {
        return true;
    }

    @Override
    public void stop(Runnable runnable) {
        stop();
        runnable.run();
    }

    @Override
    public void start() {
        doStart();
        isRunning = true;
    }

    @Override
    public void stop() {
        doStop();
        isRunning = false;
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public int getPhase() {
        return DEFAULT_PHASE;
    }

    /**
     * 启动任务, 支持重复调用
     */
    protected abstract void doStart();

    /**
     * 停止任务, 支持重复调用
     */
    protected abstract void doStop();

}
