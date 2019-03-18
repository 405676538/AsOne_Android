package com.example.asone_android.net;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 * @author Liuv
 * @date 2017/4/12
 */

public final class ApiClient {
    private static final String TAG = "ApiClient";
    public static final String baseUrl = "http://192.168.1.102:8000";


    public static ApiList apiList; //普通的接口

    private static long TIME_OUT = 1000 * 30;

    public static void init() {
        apiList = initApiService(ApiList.class);
    }

    private static <T> T initApiService(Class<T> clazz) {

        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        OkHttpClient client = builder
                .connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
                .readTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
//                所有请求加header
                .addInterceptor(chain -> {
                    Request request = chain.request().newBuilder()
                            .build();
                    return chain.proceed(request);
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiClient.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit.create(clazz);
    }

}
