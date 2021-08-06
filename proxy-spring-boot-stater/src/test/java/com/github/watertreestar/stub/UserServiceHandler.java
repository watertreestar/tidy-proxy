package com.github.watertreestar.stub;

import com.github.watertreestar.stub.proxy.AbstractStubHandler;

import java.lang.reflect.Method;

public class UserServiceHandler extends AbstractStubHandler {
    @Override
    protected Object handle(Object proxy, Method method, Object[] args) {
        System.out.println("User Service invoke method" + method.getName());
        return null;
    }
}
