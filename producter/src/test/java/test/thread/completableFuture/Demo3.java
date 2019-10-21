package test.thread.completableFuture;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @ProjectName: elasticsearch
 * @Package: test.thread.completableFuture
 * @ClassName: Demo3
 * @Author: guang
 * @Description: 计算完成时对结果的处理 whenComplete/exceptionally/handle
 * @Date: 2019/10/21 10:27
 * @Version: 1.0
 */
public class Demo3 {


    @Test
    public void test1() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            ThreadUtil.sleep(100);
            return 20;
        }).whenCompleteAsync((v, e) -> {
            System.out.println(v);
            System.out.println(e);
        });
        System.out.println(future.get());
    }

    /**
     * handle 是执行任务完成时对结果的处理。
     * handle 方法和 thenApply 方法处理方式基本一样。不同的是 handle 是在任务完成后再执行，还可以处理异常的任务。thenApply 只可以执行正常的任务，任务出现异常则不执行 thenApply 方法。
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void test2() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            ThreadUtil.sleep(100);
            return 10/0;
        }).whenCompleteAsync((v, e) -> {
            System.out.println(v);
            System.out.println(e);
        }).exceptionally((e) -> {
            System.out.println(e.getMessage());
            return 30;
        });
        System.out.println(future.get());
    }

    @Test
    public void test3() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture
                .supplyAsync(() -> 10/0)
                .handle((t, e) -> {
                    System.out.println(e.getMessage());
                    return 10;
                });
        System.out.println(future.get());
    }
}
