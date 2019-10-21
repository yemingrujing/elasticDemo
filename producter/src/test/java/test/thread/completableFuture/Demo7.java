package test.thread.completableFuture;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @ProjectName: elasticsearch
 * @Package: test.thread.completableFuture
 * @ClassName: Demo7
 * @Author: guang
 * @Description: acceptEither / applyToEither
 * acceptEither 没有返回值，applyToEither 有返回值
 * @Date: 2019/10/21 11:45
 * @Version: 1.0
 */
public class Demo7 {

    /**
     * acceptEither方法是当任意一个 CompletionStage 完成的时候，action 这个消费者就会被执行。这个方法返回 CompletableFuture<Void>
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void test1() throws ExecutionException, InterruptedException {
        Random random = new Random();
        CompletableFuture
                .supplyAsync(() -> {
                    ThreadUtil.sleep(random.nextInt(1000));
                    return "A";
                }).acceptEither(CompletableFuture.supplyAsync(() -> {
                    ThreadUtil.sleep(random.nextInt(1000));
                    return "B";
                }), System.out::println)
                .get();
    }

    /**
     * 两个CompletionStage，谁执行返回的结果快，就用那个CompletionStage的结果进行下一步的转化操作。
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void test2() throws ExecutionException, InterruptedException {
        Random random = new Random();
        CompletableFuture<Integer> future = CompletableFuture
                .supplyAsync(() -> {
                    int t = random.nextInt(1000);
                    ThreadUtil.sleep(t);
                    System.out.println("f1=" + t);
                    return t;
                }).applyToEither(CompletableFuture.supplyAsync(() -> {
                    int t = random.nextInt(1000);
                    ThreadUtil.sleep(t);
                    System.out.println("f2=" + t);
                    return t;
                }), (t) -> {
                    System.out.println(t);
                    return t * 2;
                });
        System.out.println(future.get());
    }

    /**
     * 两个CompletionStage，任何一个完成了都会执行下一步的操作（Runnable）
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void test3() throws ExecutionException, InterruptedException {
        Random random = new Random();
        CompletableFuture
                .supplyAsync(() -> {
                    int t = random.nextInt(1000);
                    ThreadUtil.sleep(t);
                    System.out.println("f1=" + t);
                    return t;
                }).runAfterEither(CompletableFuture.supplyAsync(() -> {
                    int t = random.nextInt(1000);
                    ThreadUtil.sleep(t);
                    System.out.println("f2=" + t);
                    return t;
                }), () -> System.out.println("有一个任务已经完成了！")).get();
    }
}
