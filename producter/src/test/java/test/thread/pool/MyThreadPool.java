package test.thread.pool;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ProjectName: elasticsearch
 * @Package: test.thread.pool
 * @ClassName: MyThreadPool
 * @Author: guang
 * @Description: 自定义简单线程池
 * @Date: 2019/7/30 17:23
 * @Version: 1.0
 */
public class MyThreadPool {

    /**存放线程的集合*/
    private ArrayList<MyWorkThread> threads;
    /**任务队列*/
    private BlockingQueue<Runnable> taskQueue;
    /**线程池初始限定大小*/
    private int threadNum;
    /** 线程池最大大小 */
    private int maxThreadNum;
    /**已经工作的线程数目*/
    private int workThreadNum;

    private final ReentrantLock mainLock = new ReentrantLock();

    public MyThreadPool(int initPoolNum) {
        this.threadNum = initPoolNum;
        maxThreadNum = initPoolNum;
        this.threads = new ArrayList<>(initPoolNum);
        //任务队列初始化为线程池线程数的四倍
        this.taskQueue = new ArrayBlockingQueue<>(initPoolNum * 4);

        this.workThreadNum = 0;
    }

    public MyThreadPool(int initPoolNum, int maxThreadNum, BlockingQueue<Runnable> taskQueue) {
        this.threadNum = initPoolNum;
        this.maxThreadNum = maxThreadNum;
        this.threads = new ArrayList<>(initPoolNum);
        //任务队列初始化为线程池线程数的四倍
        this.taskQueue = taskQueue;

        this.workThreadNum = 0;
    }

    public void execute(Runnable runnable) {
        try {
            mainLock.lock();
            //线程池未满，每加入一个任务则开启一个线程
            if(workThreadNum < threadNum) {
                MyWorkThread myThead = new MyWorkThread(runnable);
                myThead.start();
                threads.add(myThead);
                workThreadNum++;
            }
            //线程池已满，放入任务队列，等待有空闲线程时执行
            else {
                //队列已满，无法添加、且线程数小于最大线程数时，新增一个任务线程跑任务
                if(!taskQueue.offer(runnable) && workThreadNum < maxThreadNum) {
                    MyWorkThread overLimitThead = new MyWorkThread(runnable);
                    overLimitThead.start();
                    threads.add(overLimitThead);
                    workThreadNum++;
                }
                //队列已满，无法添加、且线程数大于等于最大线程数时，拒绝任务
                else{
                    rejectTask();
                }
            }
        } finally {
            mainLock.unlock();
        }
    }

    public BlockingQueue<Runnable> getTaskQueue() {
        return taskQueue;
    }

    private void rejectTask() {
        System.out.println("任务队列已满，无法继续添加，请扩大您的初始化线程池！");
    }

    public static void main(String[] args) {
        MyThreadPool myThreadPool = new MyThreadPool(10);
        Runnable task = () -> System.out.println(Thread.currentThread().getName()+"执行中");

        for (int i = 0; i < 200; i++) {
            myThreadPool.execute(task);
        }
    }

    /**
     * 自定义线程类
     */
    class MyWorkThread extends Thread{
        private Runnable task;

        public MyWorkThread(Runnable runnable) {
            this.task = runnable;
        }
        @Override
        public void run() {
            //该线程一直启动着，不断从任务队列取出任务执行
            while (true) {
                //如果初始化任务不为空，则执行初始化任务
                if(task != null) {
                    task.run();
                    task = null;
                }
                //否则去任务队列取任务并执行
                else {
                    Runnable queueTask;
                    try {
                        queueTask = taskQueue.take();
                        queueTask.run();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
