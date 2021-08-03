package com.github.watertreestar.stub;

import com.github.watertreestar.stub.annotation.ProxyStub;
import com.github.watertreestar.stub.proxy.AbstractStubHandler;
import com.github.watertreestar.stub.proxy.ProxyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ReflectionUtils;

import java.util.Arrays;
import java.util.Set;

/**
 * Bean definition scanner for interface annotated by {@link com.github.watertreestar.stub.annotation.ProxyStub}
 */
public class StubClassPathScanner extends ClassPathBeanDefinitionScanner {
    private static Logger logger = LoggerFactory.getLogger(StubClassPathScanner.class);

    public StubClassPathScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public StubClassPathScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters) {
        super(registry, useDefaultFilters);
    }

    public StubClassPathScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters, Environment environment) {
        super(registry, useDefaultFilters, environment);
    }

    public StubClassPathScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters, Environment environment, ResourceLoader resourceLoader) {
        super(registry, useDefaultFilters, environment, resourceLoader);
    }

    @Override
    protected void registerDefaultFilters() {
        addIncludeFilter(new AnnotationTypeFilter(ProxyStub.class, true, false));
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent()
                && !beanDefinition.getMetadata().isAnnotation();
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> definitions = super.doScan(basePackages);
        if(definitions.isEmpty()) {
            logger.warn("Not found stub proxy in package "+ Arrays.toString(basePackages) + ". check configuration");
        }
        for (BeanDefinitionHolder holder : definitions) {
            processBeanDefinition(holder);
        }
        return definitions;
    }

    private void processBeanDefinition(BeanDefinitionHolder holder) {
        GenericBeanDefinition beanDefinition = (GenericBeanDefinition) holder.getBeanDefinition();
        String beanClassName = beanDefinition.getBeanClassName();
        MultiValueMap<String, Object> allAnnotationAttributes = ((ScannedGenericBeanDefinition) beanDefinition).getMetadata().getAllAnnotationAttributes(ProxyStub.class.getName());

        Class<? extends AbstractStubHandler> handler = (Class) allAnnotationAttributes.getFirst("handler");
        Class<? extends ProxyFactory> proxyFactory = (Class) allAnnotationAttributes.getFirst("proxyFactory");

        Class<?> interfaceClass = null;
        try {
            interfaceClass = Class.forName(beanClassName);
        } catch (ClassNotFoundException e) {
        }
        if (interfaceClass == null) {
            logger.warn("Class " + beanClassName + " load class failed");
            return;
        }

        beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(beanDefinition.getBeanClassName());

        beanDefinition.getPropertyValues().add("proxyFactory", proxyFactory);
        beanDefinition.getPropertyValues().add("handler", handler);

        beanDefinition.setBeanClass(BaseFactoryBean.class);
    }

    static class BaseFactoryBean<T> implements FactoryBean<T>, InitializingBean, ApplicationListener<ApplicationEvent>,
            ApplicationContextAware {

        private ApplicationContext context;

        private Class<T> serviceInterface;

        private Class<? extends ProxyFactory> proxyFactory;

        private Class<? extends AbstractStubHandler> handler;

        public BaseFactoryBean(Class<T> serviceInterface) {
            this.serviceInterface = serviceInterface;
        }

        @Override
        public T getObject() throws Exception {
            StubContext context = new StubContext(serviceInterface, proxyFactory, handler);
            ProxyFactory factoryInstance = ReflectionUtils.accessibleConstructor(proxyFactory).newInstance();
            Object instance = factoryInstance.createProxy(serviceInterface, context);
            return (T) instance;
        }

        @Override
        public Class<?> getObjectType() {
            return serviceInterface;
        }

        @Override
        public void afterPropertiesSet() throws Exception {
            if (serviceInterface == null) {
                throw new IllegalStateException("ServiceInterface can not be null");
            }
        }

        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            this.context = applicationContext;
        }

        @Override
        public void onApplicationEvent(ApplicationEvent event) {

        }

        public void setProxyFactory(Class<? extends ProxyFactory> proxyFactory) {
            this.proxyFactory = proxyFactory;
        }

        public void setHandler(Class<? extends AbstractStubHandler> handler) {
            this.handler = handler;
        }
    }
}
