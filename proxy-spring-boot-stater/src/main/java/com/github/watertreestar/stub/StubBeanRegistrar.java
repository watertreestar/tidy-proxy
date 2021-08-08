package com.github.watertreestar.stub;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class StubBeanRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes(EnableStubProxy.class.getName(), true);
        AnnotationAttributes attrs = AnnotationAttributes.fromMap(annotationAttributes);
        if (attrs == null) {
            return;
        }
        String[] pkg = attrs.getStringArray("basePackages");
        ArrayList<String> pkgList = new ArrayList(Arrays.asList(pkg));

        if (pkgList.isEmpty()) {
            pkgList.add(ClassUtils.getPackageName(importingClassMetadata.getClassName()));
        }

        StubClassPathScanner scanner = new StubClassPathScanner(registry, true);
        scanner.scan(StringUtils.toStringArray(pkgList));
    }
}
