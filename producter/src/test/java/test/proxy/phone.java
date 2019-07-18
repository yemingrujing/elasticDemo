package test.proxy;

/**
 * @ProjectName: elasticsearch
 * @Package: test.proxy
 * @ClassName: phone
 * @Author: guang
 * @Description: 生产手机
 * @Date: 2019/7/18 17:15
 * @Version: 1.0
 */
public class phone implements Iphone {

    @Override
    public void product6sPlus() {
        System.out.println("生产6sPlus");
    }

    @Override
    public void product7sPlus() {
        System.out.println("生产7sPlus");
    }

    @Override
    public void product8sPlus() {
        System.out.println("生产8sPlus");
    }

    @Override
    public void productX() {
        System.out.println("生产X");
    }

    @Override
    public void productXS() {
        System.out.println("生产XS");
    }
}
