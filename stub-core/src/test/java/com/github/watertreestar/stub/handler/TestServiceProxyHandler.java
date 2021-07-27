package com.github.watertreestar.stub.handler;

import com.github.watertreestar.stub.proxy.AbstractStubHandler;

import java.lang.reflect.Method;

public class TestServiceProxyHandler extends AbstractStubHandler {

    @Override
    protected Object handle(Object proxy, Method method, Object[] args) {
        System.out.println(proxy);
        System.out.println("Proxy for test service");
        return null;
    }
}
