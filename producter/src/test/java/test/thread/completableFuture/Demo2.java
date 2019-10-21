package test.thread.completableFuture;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @ProjectName: elasticsearch
 * @Package: test.thread.completableFuture
 * @ClassName: Demo2
 * @Author: guang
 * @Description: 执行异步任务
 * @Date: 2019/10/21 9:33
 * @Version: 1.0
 */
public class Demo2 {

    @Test
    public void test1() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> System.out.println("runAsync"));
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "supplyAsync");
        System.out.println(future1.get());
        System.out.println(future2.get());
    }
}
