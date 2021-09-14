package com.github.watertreestar.proxy.retrofit2.annotation;

import okhttp3.Interceptor;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(HttpInterceptors.class)
public @interface HttpInterceptor {
    Class<? extends Interceptor> value() default Interceptor.class;

    int index() default 0;
}
