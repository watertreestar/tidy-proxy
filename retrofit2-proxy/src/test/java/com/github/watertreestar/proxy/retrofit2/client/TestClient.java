package com.github.watertreestar.proxy.retrofit2.client;

import com.github.watertreestar.proxy.retrofit2.annotation.HttpClient;
import retrofit2.http.GET;

@HttpClient(baseUrl = "https://baidu.com/")
public interface TestClient {

    @GET("/")
    public String get();
}
