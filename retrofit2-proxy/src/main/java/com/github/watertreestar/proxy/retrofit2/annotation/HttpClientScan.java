package com.github.watertreestar.proxy.retrofit2.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * HttpClient注解扫描路径
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HttpClientScan {
    @AliasFor(value = "value")
    String[] value() default {};

    @AliasFor(value = "basePackages")
    String[] basePackages() default {};

    Class<?>[] basePackageClasses() default {};
}
