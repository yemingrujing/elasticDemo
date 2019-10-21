package test.thread.completableFuture;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @ProjectName: elasticsearch
 * @Package: test.thread.completableFuture
 * @ClassName: Demo8
 * @Author: guang
 * @Description: allOf / anyOf
 * @Date: 2019/10/21 13:32
 * @Version: 1.0
 */
public class Demo8 {

    /**
     * 把有方法都执行完才往下执行，没有返回值
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void test1() throws ExecutionException, InterruptedException {
        Random random = new Random();
        CompletableFuture.allOf(CompletableFuture.runAsync(() -> {
            ThreadUtil.sleep(random.nextInt(1000));
            System.out.println(1);
        }), CompletableFuture.runAsync(() -> {
            ThreadUtil.sleep(random.nextInt(1000));
            System.out.println(2);
        })).get();
    }

    /**
     * 任意一个方法执行完都往下执行，返回一个Object类型的值
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void test2() throws ExecutionException, InterruptedException {
        Random random = new Random();
        Object obj = CompletableFuture.anyOf(CompletableFuture.supplyAsync(() -> {
            ThreadUtil.sleep(random.nextInt(1000));
            return 1;
        }), CompletableFuture.supplyAsync(() -> {
            ThreadUtil.sleep(random.nextInt(1000));
            return 2;
        })).get();
        System.out.println(obj);
    }
}
