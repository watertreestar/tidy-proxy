package com.github.watertreestar.stub;

import com.github.watertreestar.stub.annotation.ProxyStub;
import com.github.watertreestar.stub.handler.TestServiceProxyHandler;

@ProxyStub(handler = TestServiceProxyHandler.class)
public interface  TestService {
    void hello();
}
