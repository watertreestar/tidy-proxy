package com.github.watertreestar.proxy.retrofit2.annotation;

import com.github.watertreestar.proxy.retrofit2.HttpClientProxyFactory;
import com.github.watertreestar.stub.annotation.ProxyStub;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ProxyStub(proxyFactory = HttpClientProxyFactory.class)
public @interface HttpClient {
    String baseUrl() default "";

    String value() default "";
}