package com.github.watertreestar.proxy.retrofit2.client;

import com.github.watertreestar.proxy.retrofit2.annotation.HttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

@HttpClient(baseUrl = "https://baidu.com/")
public interface TestClient {

    @GET("/")
    Call<ResponseBody> get();
}
