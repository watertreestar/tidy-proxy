package com.github.watertreestar.stub.annotation;

import com.github.watertreestar.stub.proxy.AbstractStubHandler;
import com.github.watertreestar.stub.proxy.DefaultJDKProxyFactory;
import com.github.watertreestar.stub.proxy.ProxyFactory;

import java.lang.annotation.*;

/**
 * 标记为一个类添加创建一个本地的stub代理
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ProxyStub {
    /**
     * 生成代理的factory
     * @return
     */
    Class<? extends ProxyFactory> proxyFactory() default DefaultJDKProxyFactory.class;

    /**
     * stub调用的目标
     * @return
     */
    Class<? extends AbstractStubHandler> handler() default AbstractStubHandler.class;
}
