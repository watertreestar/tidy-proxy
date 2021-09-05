package com.github.watertreestar.proxy.retrofit2.http;

import okhttp3.OkHttpClient;

/**
 *
 */
public class DefaultOkHttpClientLoader implements OkHttpClientLoader {
    @Override
    public OkHttpClient getBaseHttpClient() {
        return null;
    }
}
