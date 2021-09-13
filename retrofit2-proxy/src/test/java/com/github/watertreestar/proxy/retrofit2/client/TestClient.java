package com.github.watertreestar.proxy.retrofit2.client;

import com.github.watertreestar.proxy.retrofit2.annotation.HttpClient;
import retrofit2.Response;
import retrofit2.http.GET;

import java.util.Optional;

@HttpClient(baseUrl = "https://baidu.com/")
public interface TestClient {

    @GET("/")
    Response<Optional> get();
}
