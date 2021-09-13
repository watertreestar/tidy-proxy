package com.github.watertreestar.proxy.retrofit2.annotation;

import com.github.watertreestar.stub.EnableStubProxy;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableStubProxy
public @interface EnableHttpClient {
    @AliasFor(value = "value", annotation = EnableStubProxy.class)
    String[] value() default {};

    @AliasFor(value = "basePackages", annotation = EnableStubProxy.class)
    String[] basePackages() default {};
}
