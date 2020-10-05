package com.doozycod.axs.ApiService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.doozycod.axs.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
  /*  private static Retrofit retrofit = null;
    public static Retrofit getClient(String baseUrl) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }*/

    private static Retrofit.Builder retrofitBuilder = null;
    static HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

    static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();


    public static Retrofit getClient(String baseUrl) {
        httpClient.connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(35, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = null;
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            httpClient.addInterceptor(logging);
        }
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        if (retrofitBuilder == null) {
            retrofitBuilder = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient.build());
        }
        retrofit = retrofitBuilder.build();
        return retrofit;
    }
}
