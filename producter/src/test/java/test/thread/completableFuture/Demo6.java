package test.thread.completableFuture;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @ProjectName: elasticsearch
 * @Package: test.thread.completableFuture
 * @ClassName: Demo6
 * @Author: guang
 * @Description: 组合 thenCompose/thenCombine
 * @Date: 2019/10/21 11:35
 * @Version: 1.0
 */
public class Demo6 {

    @Test
    public void test1() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture
                .supplyAsync(() -> 1).thenApply((a) -> {
                    ThreadUtil.sleep(1000);
                    return a + 10;
                }).thenCompose((s) -> {
                    System.out.println(s);
                    return CompletableFuture.supplyAsync(() -> s * 5);
                });
        System.out.println(future.get());
    }

    @Test
    public void test2() throws ExecutionException, InterruptedException {
        Random random = new Random();
        CompletableFuture<Integer> future = CompletableFuture
                .supplyAsync(() -> {
                    ThreadUtil.sleep(random.nextInt(1000));
                    System.out.println("supplyAsync");
                    return 2;
                }).thenApply((a) -> {
                    ThreadUtil.sleep(random.nextInt(1000));
                    System.out.println("thenApply");
                    return a * 3;
                }).thenCombine(CompletableFuture.supplyAsync(() -> {
                    ThreadUtil.sleep(random.nextInt(1000));
                    System.out.println("thenCombineAsync");
                    return 10;
                }), (a, b) -> {
                    System.out.println(a);
                    System.out.println(b);
                    return a + b;
                });
        System.out.println(future.get());
    }
}
