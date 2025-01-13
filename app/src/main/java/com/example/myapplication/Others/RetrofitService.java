package com.example.myapplication.Others;

import com.example.myapplication.Models.ReqLogin;
import com.example.myapplication.Models.ResLogin;
import com.example.myapplication.Models.ResPhone;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RetrofitService {
    public static final String BASE_URL = "https://project3-pma1011-backend.onrender.com";

    // login api
    @POST("users/login")
    Call<ResLogin> Login(@Body ReqLogin reqLogin);

    // get all phones
    @GET("phones/home")
    Call<List<ResPhone>> getAllPhones(@Header("Authorization") String token);  // Add the token header
}
