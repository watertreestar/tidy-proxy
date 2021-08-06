# tidy-proxy

基于Spring为类生成一个stub proxy

## 背景

利用Spring动态注册bean的能力，向Context中注册一个代理

## Spring Boot支持

提供stub代理的开关配置

- [x] 提供`@EnableStubProxy`来启用或者禁用代理
- [ ] 单个Stub的enable支持

## Spring中如何在启动的时候动态注册Bean

