package test.thread.pool;

import java.util.AbstractQueue;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ProjectName: elasticsearch
 * @Package: test.thread.pool
 * @ClassName: MyDelayQueue
 * @Author: guang
 * @Description: 自定义延迟队列
 * @Date: 2019/7/26 16:17
 * @Version: 1.0
 */
public class MyDelayQueue extends AbstractQueue implements BlockingQueue {

    // 堆数据结构
    private MyScheduledFutureTask[] queue = new MyScheduledFutureTask[16];

    // 队列元素个数
    private int size = 0;

    // 锁，用于队列新增和删除时保持线程安全
    private final transient ReentrantLock lock = new ReentrantLock();

    // 用于实现队列延迟取出元素
    private final Condition available = lock.newCondition();

    /**
     * 领导线程，可理解为正在获取节点的线程、锁和Condition一起，控制队列延迟获取节点时，线程的等待和唤醒
     */
    private Thread leader = null;

    @Override
    public Iterator iterator() {
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(Object o) throws InterruptedException {

    }

    @Override
    public boolean offer(Object o, long timeout, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public Object take() throws InterruptedException {
        return null;
    }

    @Override
    public Object poll(long timeout, TimeUnit unit) throws InterruptedException {
        return null;
    }

    @Override
    public int remainingCapacity() {
        return 0;
    }

    @Override
    public int drainTo(Collection c) {
        return 0;
    }

    @Override
    public int drainTo(Collection c, int maxElements) {
        return 0;
    }

    @Override
    public boolean offer(Object o) {
        return false;
    }

    @Override
    public Object poll() {
        return null;
    }

    @Override
    public Object peek() {
        return null;
    }

    @Override
    public boolean add(Object o) {
        lock.lock();

        try {
            // 队列空间不足时，扩展
            if (size >= queue.length - 1) {
                queue = Arrays.copyOf(queue, queue.length * 2);
            }

            MyScheduledFutureTask task = (MyScheduledFutureTask) o;
            // queue没有任务时，直接往数组第一个放入任务
            if (size == 0) {
                queue[size++] = task;
            } else {
                // queue已经有任务时，在堆尾部增加任务，并实行堆上浮操作
                size++;
            }
        } finally {
            lock.unlock();
        }
        return true;
    }

    /**
     *  删除f节点，将堆中尾节点设置为头节点，然后进行下沉排序
     * @param f
     * @return
     */
    private MyScheduledFutureTask finshPoll(MyScheduledFutureTask f) {

        return f;
    }

    /**
     * 在堆尾部增加节点，实行堆排序的上浮操作
     * @param k
     * @param key
     */
    private void siftUp(int k, MyScheduledFutureTask key) {

    }

    /**
     * 从堆的顶部拿取节点，实现堆排序的下沉操作
     * @param k
     * @param key
     */
    private void siftDown(int k, MyScheduledFutureTask key) {

    }
}
