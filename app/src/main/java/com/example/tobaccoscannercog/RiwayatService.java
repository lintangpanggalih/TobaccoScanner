package com.example.tobaccoscannercog;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface RiwayatService {
    @GET("android/")
    Call<RiwayatModel> getRiwayat();

    @GET("android/{id}")
    Call<RiwayatModel> getLabelRiwayat(@Path("id") int label);
//
//    @GET("searchid/{id}")
//    Call<RiwayatModel> getIDRiwayat(@Path("id") int label);

    @Multipart
    @POST("imageUpload/")
    Call<ServerResponse> uploadImage(@Part MultipartBody.Part part, @Part("name") RequestBody name);


}
