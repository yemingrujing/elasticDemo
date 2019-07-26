package test.thread.pool;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @ProjectName: elasticsearch
 * @Package: test.thread.pool
 * @ClassName: ThreadPoolManager
 * @Author: guang
 * @Description: 线程池类，线程管理器：创建线程，执行任务，销毁线程，获取线程基本信息
 * @Date: 2019/7/25 11:43
 * @Version: 1.0
 */
public class ThreadPoolManager implements ThreadPool {

    // 线程池中默认线程的个数为5
    private static int workerNum = 5;

    // 工作线程数组
    private WorkThread[] workThreads;

    // 执行任务数量
    private static volatile int executeTaskNumber = 0;

    // 任务队列 作为一个缓冲 List线程不安全
//    private List<Runnable> taskQueue = new LinkedList<>();

    // 阻塞队列
    private BlockingQueue<Runnable> taskQueue;

    private static ThreadPoolManager threadPool;

    private AtomicLong threadNum = new AtomicLong();

    /**
     * 创建具有默认线程个数的线程池
     */
    private ThreadPoolManager() {
        this(workerNum);
    }

    /**
     * 创建线程池,worker_num为线程池中工作线程的个数
     * @param workerNum
     */
    private ThreadPoolManager(int workerNum) {
        if (workerNum > 0) {
            ThreadPoolManager.workerNum = workerNum;
        }

        // 工作线程初始化
        workThreads = new WorkThread[workerNum];
        // 初始化任务队列
        taskQueue = new ArrayBlockingQueue<>(1024);

        for (int i = 0; i < ThreadPoolManager.workerNum; i ++) {
            workThreads[i] = new WorkThread();
            Thread thread = new Thread(workThreads[i], "ThreadPool-worker" + threadNum.incrementAndGet());
            System.out.println("初始化线程总数" + (i + 1) + "--------------当前线程名称是：" + thread.getName());
            thread.start();
        }
    }

    /**
     * 单态模式，获得一个默认线程个数的线程池
     * @return
     */
    public static ThreadPool getThreadPool() {
        return getThreadPool(ThreadPoolManager.workerNum);
    }

    /**
     * 单态模式，获得一个指定线程个数的线程池,worker_num(>0)为线程池中工作线程的个数
     * @param workerNum
     * @return
     */
    public static ThreadPool getThreadPool(int workerNum) {
        // worker_num<=0创建默认的工作线程个数
        if (workerNum <= 0) {
            workerNum = ThreadPoolManager.workerNum;
        }

        if (threadPool == null) {
            threadPool = new ThreadPoolManager(workerNum);
        }

        return threadPool;
    }

    /**
     * 执行任务,其实只是把任务加入任务队列，什么时候执行有线程池管理器觉定
     * @param task
     */
    @Override
    public void execute(Runnable task) {
        synchronized (taskQueue) {
            taskQueue.add(task);
            // 通知 启动的线程
            taskQueue.notifyAll();
        }
    }

    /**
     * 批量执行任务,其实只是把任务加入任务队列，什么时候执行有线程池管理器觉定
     * @param tasks
     */
    @Override
    public void execute(Runnable[] tasks) {
        synchronized (taskQueue) {
            for (Runnable task : tasks) {
                taskQueue.add(task);
            }
            taskQueue.notifyAll();
        }
    }

    /**
     * 批量执行任务,其实只是把任务加入任务队列，什么时候执行有线程池管理器决定
     * @param tasks
     */
    @Override
    public void execute(List<Runnable> tasks) {
        synchronized (taskQueue) {
            for (Runnable task : tasks) {
                taskQueue.add(task);
            }
            taskQueue.notifyAll();
        }
    }

    // 返回已完成任务的个数,这里的已完成是只出了任务队列的任务个数，可能该任务并没有实际执行完成
    @Override
    public int getExecuteTaskNumber() {
        return executeTaskNumber;
    }

    // 返回任务队列的长度，即还没处理的任务个数
    @Override
    public int getWaitTaskNumber() {
        return taskQueue.size();
    }

    // 返回工作线程的个数
    @Override
    public int getWorkThreadNumber() {
        return workerNum;
    }

    /**
     * 销毁线程池,该方法保证在所有任务都完成的情况下才销毁所有线程，否则等待任务完成才销毁
     */
    @Override
    public void destory() {
        while (!taskQueue.isEmpty()) {
            try {
                // 不断检测任务队列是否全部执行
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 工作线程停止工作，且置为null
        for (int i = 0; i < workerNum; i++) {
            workThreads[i].stopWorker();
            workThreads[i] = null;
        }

        threadPool = null;
        taskQueue.clear();
    }

    @Override
    public String toString() {
        return "当前工作线程数：" + workerNum + ", 已完成任务数：" + executeTaskNumber + ", 等待任务数量：" + getWaitTaskNumber();
    }

    /**
     * 内部类，工作线程
     */
    private class WorkThread extends Thread {

        // 该工作线程是否有效 用于结束该工作线程
        private boolean isRunning = true;

        /**
         * 关键所在，如果任务队列不空，则取出任务执行，若任务队列空，则等待
         */
        @Override
        public void run() {
            // 队列同步机制synchronized
            // 接收队列当中的任务对象Runnable类型
            Runnable r = null;

            // 线程无效 结束run方法 线程没用了
            while (isRunning) {
                synchronized (taskQueue) {
                    // 队列为空
                    while (isRunning && taskQueue.isEmpty()) {
                        try {
                            taskQueue.wait(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    if (!taskQueue.isEmpty()) {
                        // 取出任务
                        r = taskQueue.poll();
                    }

                    if (r != null) {
                        // 执行任务
                        r.run();
                    }

                    executeTaskNumber++;
                    r = null;
                }
            }
        }

        /**
         * 停止工作 让该线程自然执行完run方法 自然结束
         */
        public void stopWorker() {
            isRunning = false;
        }
    }
}
