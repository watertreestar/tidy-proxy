package com.github.watertreestar.stub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableStubProxy
public class ProxyStubApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(ProxyStubApplication.class,args);
        UserService service = context.getBean(UserService.class);
        service.study();
    }
}
