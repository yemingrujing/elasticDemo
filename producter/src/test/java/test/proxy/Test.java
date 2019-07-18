package test.proxy;

import java.lang.reflect.Proxy;

/**
 * @ProjectName: elasticsearch
 * @Package: test.proxy
 * @ClassName: Test
 * @Author: guang
 * @Description: 测试
 * @Date: 2019/7/18 17:21
 * @Version: 1.0
 */
public class Test {

    public static void main(String[] args) {
        Iphone phone = new phone();
        AgentRun agentRun = new AgentRun();
        agentRun.setObject(phone);
        /*
        static Object newProxyInstance(ClassLoader loader, Class[] interfaces,InvocationHandler h)
            - loader 指定代理类的ClassLoader加载器
            - interfaces 指定代理类要实现的接口
            - h: 表示的是当我这个动态代理对象在调用方法的时候，会关联到哪一个InvocationHandler对象上
         */
        Iphone phoneProxy = (Iphone) Proxy.newProxyInstance(phone.getClass().getClassLoader(), phone.getClass().getInterfaces(), agentRun);
        phoneProxy.product7sPlus();
    }
}
