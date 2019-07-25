package test.thread.pool;

import java.util.List;

/**
 * @ProjectName: elasticsearch
 * @Package: test.thread.pool
 * @ClassName: ThreadPool
 * @Author: guang
 * @Description: 线程池接口
 * @Date: 2019/7/25 11:39
 * @Version: 1.0
 */
public interface ThreadPool {

    void execute(Runnable task);

    void execute(Runnable[] tasks);

    void execute(List<Runnable> tasks);

    /**
     * 返回执行任务的个数
     * @return
     */
    int getExecuteTaskNumber();

    /**
     * 返回任务队列的长度，即还没处理的任务个数
     * @return
     */
    int getWaitTaskNumber();

    /**
     * 返回工作线程的个数
     * @return
     */
    int getWorkThreadNumber();

    /**
     * 关闭线程池
     */
    void destory();
}
