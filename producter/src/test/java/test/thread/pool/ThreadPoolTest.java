package test.thread.pool;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: elasticsearch
 * @Package: test.thread.pool
 * @ClassName: ThreadPoolTest
 * @Author: guang
 * @Description: ${description}
 * @Date: 2019/7/25 13:58
 * @Version: 1.0
 */
public class ThreadPoolTest {

    public static void main(String[] args) {
        ThreadPool t = ThreadPoolManager.getThreadPool(6);
        List<Runnable> taskList = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            taskList.add(new Task());
        }
        t.execute(taskList);
        System.out.println(t);
        // 所有线程执行完才destory
        t.destory();
        System.out.println(t);
    }

    /**
     * 任务类
     */
    static class Task implements Runnable {

        private static volatile int i = 1;

        @Override
        public void run() {
            int sum = 0;
            for (int j = 0; j < i; j++) {
                sum += j;
            }
            System.out.println("当前处理的线程是：" + Thread.currentThread().getName() + " 执行任务" + (i++) + "完成, 结果：" + sum);
        }
    }
}
