package com.github.watertreestar.proxy.retrofit2.annotation;

import okhttp3.Interceptor;

public @interface HttpInterceptor {
    Class<? extends Interceptor> value() default Interceptor.class;

    int index() default 0;
}
