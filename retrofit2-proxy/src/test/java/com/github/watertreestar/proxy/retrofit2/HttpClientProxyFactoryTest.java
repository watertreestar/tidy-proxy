package com.github.watertreestar.proxy.retrofit2;

import com.github.watertreestar.proxy.retrofit2.client.TestClient;
import junit.framework.TestCase;

public class HttpClientProxyFactoryTest extends TestCase {

    public void testCreateProxy() {
        HttpClientProxyFactory factory = new HttpClientProxyFactory();

        TestClient client = factory.createProxy(TestClient.class, null);
    }
}