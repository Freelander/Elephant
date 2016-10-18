/*
 * Copyright 2016 Freelander
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jun.elephant.api;

import android.text.TextUtils;

import com.jun.elephant.BuildConfig;
import com.jun.elephant.util.JLog;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jun on 2016/7/27.
 */
public class Networks {

    private static final int DEFAULT_TIMEOUT = 5;

    private static Retrofit retrofit;

    private static TokenApi mTokenApi;

    private static TopicApi mTopicApi;

    private static UserApi mUserApi;

    private static String mToken = "";

    public static String getToken() {
        return mToken;
    }

    public static void setToken(String mToken) {
        Networks.mToken = mToken;
    }

    private static Networks mNetworks;

    public static Networks getInstance() {
        if (mNetworks == null) {
            mNetworks = new Networks();
        }
        return mNetworks;
    }

    public TokenApi getTokenApi() {
        return mTokenApi == null ? configRetrofit(TokenApi.class, true): mTokenApi;
    }


    public  TopicApi getTopicApi() {
        return mTopicApi == null ? configRetrofit(TopicApi.class, false) : mTopicApi;
    }

    public UserApi getUserApi() {
        return mUserApi == null ? configRetrofit(UserApi.class, false) : mUserApi;
    }

    private <T> T configRetrofit(Class<T> service, boolean isGetToken) {
        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_BASE_URL)
                .client(configClient(isGetToken))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit.create(service);

    }

    private OkHttpClient configClient(final boolean isGetToken) {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();

        //为所有请求添加头部 Header 配置的拦截器
        Interceptor headerIntercept = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                builder.addHeader("X-Client-Platform", "Android");
                builder.addHeader("X-Client-Version", BuildConfig.VERSION_NAME);
                builder.addHeader("X-Client-Build", String.valueOf(BuildConfig.VERSION_CODE));

                builder.removeHeader("Accept");
                if (isGetToken) {
                    builder.addHeader("Accept", "application/vnd.PHPHub.v1+json");
                } else {
                    builder.addHeader("Accept", "application/vnd.OralMaster.v1+json");
                }

                if (!TextUtils.isEmpty(mToken)) {
                    builder.addHeader("Authorization", "Bearer " + mToken);
                }

                Request request = builder.build();

                return chain.proceed(request);
            }
        };

        // Log信息拦截器
        if (BuildConfig.LOG_DEBUG) {
            Interceptor loggingIntercept = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    Response response = chain.proceed(request);
                    ResponseBody responseBody = response.body();
                    BufferedSource source = responseBody.source();
                    source.request(Long.MAX_VALUE); // Buffer the entire body.
                    Buffer buffer = source.buffer();
                    Charset UTF8 = Charset.forName("UTF-8");
                    JLog.logJ("REQUEST_JSON", buffer.clone().readString(UTF8));
                    JLog.logi("REQUEST_URL", request.toString());
                    return response;
                }
            };
            okHttpClient.addInterceptor(loggingIntercept);
        }

        okHttpClient.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        okHttpClient.addNetworkInterceptor(headerIntercept);

        return okHttpClient.build();
    }

}
