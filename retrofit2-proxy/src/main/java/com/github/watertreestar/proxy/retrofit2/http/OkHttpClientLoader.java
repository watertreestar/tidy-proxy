package com.github.watertreestar.proxy.retrofit2.http;

import okhttp3.OkHttpClient;

public interface OkHttpClientLoader {
    OkHttpClient getBaseHttpClient();
}
