package com.github.watertree.stub.proxy;

import com.github.watertree.stub.StubContext;
import com.github.watertree.stub.StubException;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 基于原生JDK来生成stub代理
 */
public class DefaultJDKProxyFactory implements ProxyFactory {
    @Override
    public <T> T createProxy(Class<?> classType, StubContext context) {
        Class<? extends AbstractStubHandler> handlerClass = context.getHandler();
        AbstractStubHandler handler = this.handlerInstance(handlerClass);
        return (T)Proxy.newProxyInstance(
                ClassUtils.getDefaultClassLoader(),
                new Class[]{classType},
                StubInvocationHandler.newInstance(handler,context));
    }

    /**
     * 获取一个handler实例
     *
     * @param type
     * @param <T>
     * @return
     */
    protected <T> T handlerInstance(Class<? extends T> type) {
        try {
            return type.newInstance();
        } catch (Exception e) {
            throw new StubException("Exception while create ProxyHandler instance",e);
        }
    }

    /**
     * 用于JDK动态代理的InvocationHandler
     */
    static class StubInvocationHandler implements InvocationHandler {
        AbstractStubHandler handler;
        StubContext context;

        public StubInvocationHandler(AbstractStubHandler handler, StubContext context) {
            this.handler = handler;
            this.context = context;
        }

        public static StubInvocationHandler newInstance(AbstractStubHandler handler,StubContext context) {
            return new StubInvocationHandler(handler,context);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if(ReflectionUtils.isToStringMethod(method)) {
                return "ProxyStub:"+ ClassUtils.classNamesToString(handler.getClass());
            }
            if(ReflectionUtils.isObjectMethod(method)) {
                method.invoke(proxy,args);
            }
            return handler.handle(context,proxy,method,args);
        }
    }
}
