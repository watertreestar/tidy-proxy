package com.github.watertreestar.proxy.retrofit2.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface HttpInterceptors {
    HttpInterceptor[] value();
}
