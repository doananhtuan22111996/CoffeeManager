package com.tuan.coffeemanager.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitMain {

    private static Retrofit retrofit;

    public static Retrofit newInstance() {
        if (retrofit == null) {
            return retrofit = new Retrofit.Builder()
                    .baseUrl(Api.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        } else {
            return retrofit;
        }
    }
}
