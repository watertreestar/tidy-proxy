package com.github.watertreestar.proxy.retrofit2.http;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import java.util.concurrent.TimeUnit;

/**
 * 加载OkHttpClient
 * 主要是为了自定义配置OkHttp
 */
public class DefaultOkHttpClientLoader implements OkHttpClientLoader {
    private static volatile OkHttpClient HTTP_CLIENT;

    @Override
    public OkHttpClient getBaseHttpClient() {
        if (HTTP_CLIENT == null) {
            synchronized (DefaultOkHttpClientLoader.class) {
                if (HTTP_CLIENT == null) {
                    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT);
                    httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
                    HTTP_CLIENT = new OkHttpClient.Builder()
                            .addInterceptor(httpLoggingInterceptor)
                            .readTimeout(5, TimeUnit.SECONDS)
                            .connectTimeout(5, TimeUnit.SECONDS)
                            .writeTimeout(5, TimeUnit.SECONDS)
                            .build();
                }
            }
        }
        return HTTP_CLIENT;
    }
}
