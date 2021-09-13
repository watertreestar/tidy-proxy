package com.github.watertreestar.proxy.retrofit2;

import com.github.watertreestar.proxy.retrofit2.annotation.EnableHttpClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableHttpClient
public class Retrofit2Application {
    public static void main(String[] args) {
        SpringApplication.run(Retrofit2Application.class, args);
    }
}
