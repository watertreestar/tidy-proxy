package com.github.watertreestar.stub;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(StubBeanRegistrar.class)
public @interface EnableStubProxy {
    String []basePackages() default {};
}
