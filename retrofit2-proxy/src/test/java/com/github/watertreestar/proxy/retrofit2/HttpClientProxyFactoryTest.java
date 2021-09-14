package com.github.watertreestar.proxy.retrofit2;

import com.github.watertreestar.proxy.retrofit2.client.TestClient;
import com.github.watertreestar.stub.EnableStubProxy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@SpringBootTest
@RunWith(SpringRunner.class)
@EnableStubProxy
public class HttpClientProxyFactoryTest {
    @Autowired
    private TestClient testClient;

    @Test
    public void test() throws IOException {
       testClient.get().execute().message();
    }
}