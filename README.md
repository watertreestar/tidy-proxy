# tidy-proxy

基于Spring为类生成一个proxy，可以自定义proxy目标类，自定义生成proxy的factory

## 背景

一开始想实现一个方便的local stub，不用为了要模拟一个stub的方法而专门定义一个类去实现接口，然后就抽象出来这个可以在Spring上下文中为接口生成代理类的lib
利用Spring动态注册bean的能力，向Context中注册一个代理。

## 原理

目前默认使用JDK来生成proxy，所以只能用在接口上，但是大多数的场景也是需要运用在接口上。

## 项目结构

```
- proxy-core  生成代理的逻辑，其它依赖
- proxy-spring-boot-starter  在spring boot下基于注解生成proxy
- retrofit2-proxy  使用proxy-spring-boot-starter和retrofit2生成HTTP请求代理
```

## Spring Boot支持

提供stub代理的开关配置

- [x] 提供`@EnableStubProxy`来启用或者禁用代理
- [ ] 单个Stub的enable支持
- [x] 基于tidy-proxy实现retrofit2的代理

## Spring中如何动态注册Bean

1. 可以在任何获得了BeanDefinitionRegistry或者SingletonBeanRegistry的普通bean实例的地方进行动态注册

> 但是如果bean不是在BeanFactoryPostProcessor中被注册，那么该bean则无法被**BeanPostProcessor**处理，即无法对其应用aop、Bean Validation等功能

- SingletonBeanRegistry#registerSingleton

- BeanDefinitionRegistry#registerBeanDefinition


2. 在BeanFactoryPostProcessor中进行动态注册

> 在Spring容器的启动过程中，BeanFactory载入bean的定义后会立刻执行BeanFactoryPostProcessor，此时动态注册bean，则可以保证动态注册的bean被BeanPostProcessor处理，并且可以保证其的实例化和初始化总是先于依赖它的bean

- BeanDefinitionRegistryPostProcessor,该实现中可以获取BeanDefinitionRegistry实例
- BeanFactoryPostProcessor，该实现中获得SingletonBeanRegistry实例

3. ImportBeanDefinitionRegistrar

> 使用@Import配合ImportBeanDefinitionRegistrar

实现接口ImportBeanDefinitionRegister，从而获得BeanDefinitionRegistry实例，然后构造一个需要注册的BeanDefinition

4. ClassPathBeanDefinitionScanner

在该类中也是持有了了一个BeanDefinitionRegistry实例

所以，归根结底，我们只要获取了`BeanDefinitionRegistry`或者`SingletonBeanRegistry`的一个实例就可以注册bean到Spring容器中

## retrofit2-proxy

使用tidy-proxy提供动态注册bean的能力，基于retrofit实现了一个类似于feign的组件

