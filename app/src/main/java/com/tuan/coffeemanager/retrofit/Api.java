package com.tuan.coffeemanager.retrofit;

import com.tuan.coffeemanager.model.NotificationResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    String BASE_URL = "http://172.16.1.192:8888/";

    @POST("notify")
    @FormUrlEncoded
    Call<NotificationResponse> pushNotification(@Field("token") String token, @Field("msg") String msg);
}
