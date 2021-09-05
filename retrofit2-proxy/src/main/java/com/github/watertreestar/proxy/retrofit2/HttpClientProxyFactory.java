package com.github.watertreestar.proxy.retrofit2;

import com.github.watertreestar.stub.StubContext;
import com.github.watertreestar.stub.proxy.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * HttpClient代理创建
 */
public class HttpClientProxyFactory implements ProxyFactory, ApplicationContextAware {
    @Override
    public <T> T createProxy(Class<?> classType, StubContext context) {
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }
}
