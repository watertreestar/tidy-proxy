package com.github.watertreestar.proxy.retrofit2;

import com.github.watertreestar.proxy.retrofit2.adapter.ResponseAdapterFactory;
import com.github.watertreestar.proxy.retrofit2.annotation.HttpClient;
import com.github.watertreestar.proxy.retrofit2.annotation.HttpInterceptor;
import com.github.watertreestar.proxy.retrofit2.http.DefaultOkHttpClientLoader;
import com.github.watertreestar.proxy.retrofit2.http.OkHttpClientLoader;
import com.github.watertreestar.stub.StubContext;
import com.github.watertreestar.stub.proxy.ProxyFactory;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;
import retrofit2.Retrofit;

import java.util.Comparator;
import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * HttpClient代理创建
 */
public class HttpClientProxyFactory implements ProxyFactory, ApplicationContextAware {
    private static final Logger log = LoggerFactory.getLogger(HttpClientProxyFactory.class);

    private ApplicationContext applicationContext;

    private static final OkHttpClient DEFAULT_HTTP_CLIENT;

    private static final ResponseAdapterFactory RESPONSE_ADAPTER_FACTORY = new ResponseAdapterFactory();

    static {
        ServiceLoader<OkHttpClientLoader> loaders = ServiceLoader.load(OkHttpClientLoader.class);
        Iterator<OkHttpClientLoader> iterator = loaders.iterator();
        OkHttpClientLoader httpClientLoader;
        if (iterator.hasNext()) {
            httpClientLoader = iterator.next();
        } else {
            httpClientLoader = new DefaultOkHttpClientLoader();
        }
        DEFAULT_HTTP_CLIENT = httpClientLoader.getBaseHttpClient();
    }


    @Override
    public <T> T createProxy(Class<T> stubInterface, StubContext context) {
        HttpClient httpClient = AnnotationUtils.getAnnotation(stubInterface, HttpClient.class);
        if (httpClient == null) {
            throw new IllegalStateException(stubInterface.getName() + "not annotated with @HttpClient");
        }
        Call.Factory callFactory = buildCallFactory(stubInterface);
        String baseUrl = buildBaseUrl(httpClient);
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RESPONSE_ADAPTER_FACTORY)
                .callFactory(callFactory);
        log.debug("new Retrofit HttpClient.interface：" + stubInterface + " baseUrl：" + baseUrl);
        Retrofit retrofit = retrofitBuilder.build();
        return retrofit.create(stubInterface);
    }

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private String buildBaseUrl(HttpClient httpClient) {
        String baseUrl = httpClient.baseUrl();
        if (StringUtils.hasText(baseUrl)) {
            baseUrl = baseUrl.trim();
            baseUrl = this.applicationContext.getEnvironment().resolveRequiredPlaceholders(baseUrl);
            if (!baseUrl.endsWith("/")) {
                baseUrl += "/";
            }
        }
        return baseUrl;
    }

    /**
     * 为指定的class构建Call.Factory对象
     *
     * @param stubInterface
     * @param <T>
     * @return
     */
    private <T> Call.Factory buildCallFactory(Class<T> stubInterface) {
        OkHttpClient.Builder builder = DEFAULT_HTTP_CLIENT.newBuilder();

        AnnotationUtils.getRepeatableAnnotations(stubInterface, HttpInterceptor.class)
                .stream()
                .sorted(Comparator.comparing(HttpInterceptor::index, Integer::compareTo))
                .map(it -> applicationContext.getBean(it.value()))
                .forEach(builder::addInterceptor);
        return (Call.Factory) builder.build();
    }
}
