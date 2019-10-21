package test.thread.completableFuture;

/**
 * @ProjectName: elasticsearch
 * @Package: test.thread.completableFuture
 * @ClassName: ThreadUtil
 * @Author: guang
 * @Description: 线程工具类
 * @Date: 2019/10/21 9:22
 * @Version: 1.0
 */
public class ThreadUtil {

    public static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
