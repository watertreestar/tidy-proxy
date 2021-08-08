# tidy-proxy

基于Spring为类生成一个stub proxy

## 背景

利用Spring动态注册bean的能力，向Context中注册一个代理

## Spring Boot支持

提供stub代理的开关配置

- [x] 提供`@EnableStubProxy`来启用或者禁用代理
- [ ] 单个Stub的enable支持

## Spring中如何在启动的时候动态注册Bean

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

