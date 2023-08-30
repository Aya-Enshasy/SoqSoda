package com.lira_turkish.dollarstocks.services.api;

import static com.lira_turkish.dollarstocks.utils.AppContent.url;

import com.lira_turkish.dollarstocks.utils.AppController;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static Retrofit getRetrofit() {
        OkHttpClient client = getClient();
        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    private static OkHttpClient getClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new AddHeaderInterceptor())
                .addInterceptor(new AuthInterceptor(AppController.getInstance()))
                .connectTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .retryOnConnectionFailure(true)
                .build();
        client.connectionPool().evictAll();
        return client;
    }

    private static class AddHeaderInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {

            Request.Builder builder = chain.request().newBuilder();
            builder.header("X-Requested-With", "XMLHttpRequest");
            builder.header("Content-Type", "application/json");
            builder.header("Connection", "close");
            builder.header("Authorization", "key=AAAA1mkJlHc:APA91bH9Kb606hR3XZqKbQzz6XG2nKicfi9vrQcCCK9ODh4VIn8QYslNXLIduNUNLQPV6CkDK3oIuVxW8vtLPjQAslSWFTjtBzAryZo3LfJRh0UKNWXzexQnrcA98PyIs43yjzCYpgT9");
//            builder.header("lang", "en");
            return chain.proceed(builder.build());
        }
    }
}

