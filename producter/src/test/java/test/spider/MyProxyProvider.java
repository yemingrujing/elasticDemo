package test.spider;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.ProxyProvider;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @ProjectName: elasticsearch
 * @Package: test.spider
 * @ClassName: MyProxyProvider
 * @Author: guang
 * @Description: 快代理 代理ip获取
 * @Date: 2019/8/1 10:46
 * @Version: 1.0
 */
@Slf4j
public class MyProxyProvider implements ProxyProvider {

    private List<Proxy> proxyList = Lists.newArrayList();
    private volatile Map<String, ArrayBlockingQueue<Proxy>> siteProxysMap = Maps.newHashMap();
    private Object siteProxysMapLock = new Object();

    // 获取代理信息的地址
    private String apiUrl;
    // 用户名
    private String userName;
    // 密码
    private String password;
    private volatile  static MyProxyProvider instance = null;

    public MyProxyProvider(String apiUrl, String userName, String password) {
        this.apiUrl = apiUrl;
        this.userName = userName;
        this.password = password;
        this.init();
    }

    public static MyProxyProvider getInstance(String apiUrl, String userName, String password) {
        if (instance == null) {
            synchronized (MyProxyProvider.class) {
                if (instance == null) {
                    instance = new MyProxyProvider(apiUrl, userName, password);
                }
            }
        }
        return instance;
    }

    private void init() {
        try {
            log.info("========================get proxy============================");
            String s = HttpUtil.get(this.apiUrl);
            log.info("请求结果：{}",  s);
            if (StrUtil.isNotBlank(s)) {
                final JSONObject jsonObject = JSON.parseObject(s);
                if (jsonObject == null) {
                    return;
                }
                final JSONObject data = jsonObject.getJSONObject("data");
                if (data == null) {
                    return;
                }
                final JSONArray proxy_list = data.getJSONArray("proxy_list");
                if (proxy_list == null && proxy_list.size() == 0) {
                    return;
                }
                List<String> tempList = Lists.newArrayList();
                tempList.stream().forEach(str -> {
                    final String[] split = str.split(":");
                    proxyList.add(new Proxy(split[0], Integer.parseInt(split[1]), this.userName, this.password));
                });
            }
        } catch (Exception e) {
            log.error("Init Error: {}", ExceptionUtils.getStackTrace(e));
        }
    }

    @Override
    public void returnProxy(Proxy proxy, Page page, Task task) {
        String key = getKey(task);
        try {
            this.get(key).put(proxy);
        } catch (InterruptedException e) {
            log.error("ReturnProxy Error: {}", ExceptionUtils.getStackTrace(e));
        }
    }

    @Override
    public Proxy getProxy(Task task) {
        Proxy proxy = null;
        try {
            proxy = this.get(this.getKey(task)).take();
        } catch (InterruptedException e) {
            log.error("GetProxy Error: {}", ExceptionUtils.getStackTrace(e));
        }
        return proxy;
    }

    private ArrayBlockingQueue<Proxy> get(String key) {
        try {
            ArrayBlockingQueue<Proxy> queue = siteProxysMap.get(key);
            if (queue == null) {
                synchronized (siteProxysMapLock) {
                    queue = siteProxysMap.get(key);
                    if (queue == null) {
                        ArrayBlockingQueue<Proxy> proxies = new ArrayBlockingQueue<>(proxyList.size());
                        for (Proxy proxy : proxyList) {
                            proxies.put(proxy);
                        }
                        siteProxysMap.put(key, proxies);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Get Error: {}", ExceptionUtils.getStackTrace(e));
        }
        return siteProxysMap.get(key);
    }

    private String getKey(Task task) {
        final String domain = task != null && task.getSite() != null ? task.getSite().getDomain() : null;
        return StrUtil.isNotBlank(domain) ? domain : MyProxyProvider.class.getName();
    }
}
