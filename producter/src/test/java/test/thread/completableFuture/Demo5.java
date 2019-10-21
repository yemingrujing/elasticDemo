package test.thread.completableFuture;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @ProjectName: elasticsearch
 * @Package: test.thread.completableFuture
 * @ClassName: Demo5
 * @Author: guang
 * @Description: 纯消费 thenAccept/thenAcceptBoth/thenRun
 * @Date: 2019/10/21 11:17
 * @Version: 1.0
 */
public class Demo5 {

    /**
     * 接收任务的处理结果，并消费处理，无返回结果。
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void test1() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> future = CompletableFuture
                .supplyAsync(() -> 1)
                .thenAccept(System.out::println) // 消费 上一级返回值 1
                .thenAcceptAsync(System.out::println);  // 上一级没有返回值 输出null
        System.out.println(future.get()); // 消费函数没有返回值 输出null
    }

    /**
     * 当两个CompletionStage都执行完成后，把结果一块交给thenAcceptBoth来进行消耗
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void test2() throws ExecutionException, InterruptedException {
        CompletableFuture
                .supplyAsync(() -> 1)
                .thenAcceptBoth(CompletableFuture.supplyAsync(() -> 2), (a, b) -> {
                    System.out.println(a);
                    System.out.println(b);
                }).get();
    }

    /**
     * 两个CompletionStage，都完成了计算才会执行下一步的操作（Runnable）
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void test3() throws ExecutionException, InterruptedException {
        CompletableFuture
                .supplyAsync(() -> {
                    int t = 1;
                    System.out.println(t);
                    return t;
                })
                .runAfterBoth(CompletableFuture.supplyAsync(() -> {
                    int t = 2;
                    System.out.println(t);
                    return t;
                }), () -> System.out.println("任务已完成")).get();
    }
}
