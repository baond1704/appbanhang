package com.example.shippo.retrofit;

import com.example.shippo.model.LoaiSpModel;
import com.example.shippo.model.SanPhamMoiModel;
import com.example.shippo.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl(Utils.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @GET("getloaisp.php")
    Call<LoaiSpModel> Getloaisanpham();

    @GET("getspmoi.php")
    Call<SanPhamMoiModel> Getsanphammoi();

    @GET("chitiet.php")
    Call<SanPhamMoiModel> GetSanPham(
            @Query("page") int page,
            @Query("loai") int loai
    );


}
