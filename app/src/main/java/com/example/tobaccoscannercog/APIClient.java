package com.example.tobaccoscannercog;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static final String BASE_URL = "http://e.pbf.ilkom.unej.ac.id/172410102076/api/";
//    private static final String BASE_URL = "http://192.168.43.31:8080/ImageProcessing/public/api/";
    private static Retrofit mRetrofit = null;

    public static Retrofit getRetrofit() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mRetrofit;
    }
}
