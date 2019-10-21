package test.thread.completableFuture;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;

/**
 * @ProjectName: elasticsearch
 * @Package: test.thread.completableFuture
 * @ClassName: Demo1
 * @Author: guang
 * @Description: 主动完成计算
 * @Date: 2019/10/21 9:22
 * @Version: 1.0
 */
public class Demo1 {

    @Test
    public void test1() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            ThreadUtil.sleep(200);
            return 100 / 0;
        });
        // 和 get() 方法类似也是主动阻塞线程，等待计算结果。
//        System.out.println(future.join());
        // 该方法为阻塞方法，会等待计算结果完成
//        System.out.println(future.get());
        // 该方法为阻塞方法，会等待计算结果完成
        System.out.println(future.getNow(10));
    }

    @Test
    public void test2() {
        CompletableFuture<Integer> future1 = new CompletableFuture<>();
        // 如果 CompletableFuture 没有关联任何的Callback、异步任务等，如果调用get方法，那会一直阻塞下去
//        future1.get();
        // 可以使用complete方法主动完成计算
        future1.complete(10);
    }
}
