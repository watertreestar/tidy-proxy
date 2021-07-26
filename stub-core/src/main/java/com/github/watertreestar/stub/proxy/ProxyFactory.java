package com.github.watertreestar.stub.proxy;

import com.github.watertreestar.stub.StubContext;

/**
 * A abstract to create proxy for a class
 */
public interface ProxyFactory {
    <T> T createProxy(Class<?> classType, StubContext context);
}
