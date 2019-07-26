package test.thread.pool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName: elasticsearch
 * @Package: test.thread.pool
 * @ClassName: MyScheduledFutureTask
 * @Author: guang
 * @Description: 线程任务的实现
 * @Date: 2019/7/26 17:25
 * @Version: 1.0
 */
public class MyScheduledFutureTask implements Runnable, Delayed {

    // 任务触发时间的纳秒值
    private long time;

    // 循环间隔的纳秒值
    private final long period;

    // 线程池中的队列
    private BlockingQueue queue;

    // 执行任务
    private Runnable task;

    MyScheduledFutureTask(Runnable r, long time, int period, BlockingQueue<Runnable> queue) {
        this.task = r;
        this.time = time;
        this.period = period;
        this.queue = queue;
    }

    /**
     * 获取触发时间与当前时间的时间差
     * @param unit
     * @return
     */
    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(time - System.nanoTime(), TimeUnit.NANOSECONDS);
    }

    /**
     * 自定义任务队列实现了堆数据结构，此方法用于堆插入或者删除时的堆排序
     * @param o
     * @return
     */
    @Override
    public int compareTo(Delayed o) {
        MyScheduledFutureTask futureTask = (MyScheduledFutureTask) o;
        long diff = time - futureTask.time;
        if (diff < 0) {
            return -1;
        } else if (diff > 0) {
            return 1;
        } else {
            return 1;
        }
    }

    @Override
    public void run() {
        // 执行逻辑
        task.run();
        // 任务执行结束后，将下次任务触发的时间增加一周期
        time += TimeUnit.SECONDS.toNanos(period);
        // 重新往线程池队列中加入此任务
        queue.add(this);
    }
}
