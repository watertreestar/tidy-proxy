package com.github.watertreestar.stub;

import com.github.watertreestar.stub.handler.TestServiceProxyHandler;
import com.github.watertreestar.stub.proxy.DefaultJDKProxyFactory;
import com.github.watertreestar.stub.proxy.ProxyFactory;
import org.junit.Test;

public class StubProxyTest {
    @Test
    public void testProxy() {
        ProxyFactory proxyFactory = new DefaultJDKProxyFactory();
        StubContext context = new StubContext(TestService.class,DefaultJDKProxyFactory.class, TestServiceProxyHandler.class);
        TestService testService = proxyFactory.createProxy(TestService.class,context);
        testService.hello();
    }
}
