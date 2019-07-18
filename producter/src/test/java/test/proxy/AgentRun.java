package test.proxy;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @ProjectName: elasticsearch
 * @Package: test.proxy
 * @ClassName: AgentRun
 * @Author: guang
 * @Description: 代理类
 * @Date: 2019/7/18 17:18
 * @Version: 1.0
 */
@Setter
@Getter
public class AgentRun implements InvocationHandler {

    private Object object;

    /**
     * 处理在动态代理类对象上的方法调用
     * @param proxy 该参数为代理类的实例
     * @param method 被调用的方法对象
     * @param args 调用method对象的方法参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("宣传");
        Object result = method.invoke(object, args);
        System.out.println("销售和发货");
        return result;
    }
}
