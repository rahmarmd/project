package com.example.fbuttonnav.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroServer {
    private static final String baseUrl = "http://192.168.1.5/API_Android/";
    private static Retrofit retrofit;
    private static RetroServer retroServer;

    public static Retrofit koneksiRetrofit() {
        if (retrofit== null){
            retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();

        }
        return retrofit ;
    }
    public APIRequestData getAPI() {
        return retrofit.create(APIRequestData.class);
    }
}
