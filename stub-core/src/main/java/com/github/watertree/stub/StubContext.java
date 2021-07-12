package com.github.watertree.stub;

import com.github.watertree.stub.proxy.AbstractStubHandler;
import com.github.watertree.stub.proxy.ProxyFactory;

/**
 * 创建stub代理上下文
 */
public class StubContext {
    /**
     * 被代理的类
     */
    private Class<?> clazz;

    /**
     * 用于生成代理的类
     */
    private Class<? extends ProxyFactory> proxyFactory;

    /**
     * 处理调用的类
     */
    private Class<? extends AbstractStubHandler> handler;

    public StubContext(Class<?> clazz, Class<? extends ProxyFactory> proxyFactory, Class<? extends AbstractStubHandler> handler) {
        this.clazz = clazz;
        this.proxyFactory = proxyFactory;
        this.handler = handler;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Class<? extends ProxyFactory> getProxyFactory() {
        return proxyFactory;
    }

    public void setProxyFactory(Class<? extends ProxyFactory> proxyFactory) {
        this.proxyFactory = proxyFactory;
    }

    public Class<? extends AbstractStubHandler> getHandler() {
        return handler;
    }

    public void setHandler(Class<? extends AbstractStubHandler> handler) {
        this.handler = handler;
    }
}
