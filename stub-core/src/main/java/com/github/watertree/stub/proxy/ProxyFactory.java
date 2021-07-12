package com.github.watertree.stub.proxy;

import com.github.watertree.stub.StubContext;

/**
 * A abstract to create proxy for a class
 */
public interface ProxyFactory {
    <T> T createProxy(Class<?> classType, StubContext context);
}
