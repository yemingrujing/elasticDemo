package test.thread.pool;

import java.util.concurrent.TimeUnit;

/**
 * @ProjectName: elasticsearch
 * @Package: test.thread.pool
 * @ClassName: MyScheduledThreadPool
 * @Author: guang
 * @Description: 自定义简单定时线程池
 * @Date: 2019/7/30 17:25
 * @Version: 1.0
 */
public class MyScheduledThreadPool extends MyThreadPool {

    public MyScheduledThreadPool(int initPoolNum) {
        super(initPoolNum, Integer.MAX_VALUE, new MyDelayQueue());
    }

    /**
     * 每隔固定时间周期执行任务
     * @param command 任务
     * @param period 时间周期（以秒为单位）
     */
    public void scheduleAtFixedRate(Runnable command, int period) {
        if (command == null)
            throw new NullPointerException();

        if (period <= 0)
            throw new IllegalArgumentException();

        //包装任务为周期任务
        MyScheduledFutureTask mScheduledTask = new MyScheduledFutureTask(command, triggerTime(period), period, getTaskQueue());
        //延迟周期执行
        delayedExecute(mScheduledTask);
    }

    private void delayedExecute(MyScheduledFutureTask task) {
        getTaskQueue().add(task);
        ensurePrestart();
    }


    /**
     * 确保线程池已经启动，有线程会去读取队列，并执行任务
     */
    void ensurePrestart() {
        execute(null);
    }

    /**
     * 获取触发时间
     * @param period 延迟时间
     * @return 返回触发时间
     */
    long triggerTime(int period) {
        return System.nanoTime() + TimeUnit.SECONDS.toNanos((long)period);
    }
}
