package com.github.watertree.stub.proxy;

import com.github.watertree.stub.StubContext;

import java.lang.reflect.Method;

/**
 * 当被代理对象调用时，委托到该handler
 */
public abstract class AbstractStubHandler {
    public AbstractStubHandler(){}

    protected Object handle(StubContext context, Object proxy, Method method, Object[] args) {
        return this.handle(proxy,method,args);
    }

    protected abstract Object handle(Object proxy, Method method, Object[] args);

}
