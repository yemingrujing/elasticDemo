package test.thread.completableFuture;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @ProjectName: elasticsearch
 * @Package: test.thread.completableFuture
 * @ClassName: Demo4
 * @Author: guang
 * @Description: 结果处理转换 thenApply
 * @Date: 2019/10/21 11:04
 * @Version: 1.0
 */
public class Demo4 {

    /**
     * 当一个线程依赖另一个线程时，可以使用 thenApply 方法来把这两个线程串行化。
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void test1() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture
                .supplyAsync(() -> 1)
                .thenApply((a) -> {
                    System.out.println(a);
                    return a * 10;
                }).thenApply((a) -> {
                    System.out.println(a);
                    return a + 10;
                }).thenApply((a) -> {
                    System.out.println(a);
                    return a - 5;
                });
        System.out.println(future.get());
    }
}
