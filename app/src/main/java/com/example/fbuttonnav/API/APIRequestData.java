package com.example.fbuttonnav.API;

import com.example.fbuttonnav.Response.ResponseUser;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIRequestData {
    @FormUrlEncoded
    @POST("Register.php")
    Call<ResponseUser> userRegister(
            @Field("nama") String name,
            @Field("email") String email,
            @Field("no_ponsel") String no_telp,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("Login.php")
    Call<ResponseUser> checkLogin(
            @Field("email") String email,
            @Field("password") String password
    );
}
