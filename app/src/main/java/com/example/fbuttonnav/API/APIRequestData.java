package com.example.fbuttonnav.API;

import com.example.fbuttonnav.Response.ResponseUser;

import java.util.Date;

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

    @FormUrlEncoded
    @POST("Pemesanan.php")
    Call<ResponseUser> formPesan(
            @Field("id_daftar") String id_daftar,
            @Field("Nama") String nama,
            @Field("Email") String email,
            @Field("Nomor_ponsel") String no_telp,
            @Field("Alamat") String alamat,
            @Field("Jenis_pemesanan") String jns_pesan,
            @Field("Deskripsi_pemesanan") String des_pesan,
            @Field("id_kategori") int id_kategori,
            @Field("Bukti_pembayaran") String bkt_pembayaran
    );
}
