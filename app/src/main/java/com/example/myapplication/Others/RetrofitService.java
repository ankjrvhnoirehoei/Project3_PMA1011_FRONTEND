package com.example.myapplication.Others;

import com.example.myapplication.Models.ReqAddComment;
import com.example.myapplication.Models.ReqAddRating;
import com.example.myapplication.Models.ReqComment;
import com.example.myapplication.Models.ReqLogin;
import com.example.myapplication.Models.ReqOnePhone;
import com.example.myapplication.Models.ReqRating;
import com.example.myapplication.Models.ResAddComment;
import com.example.myapplication.Models.ResAddRating;
import com.example.myapplication.Models.ResComment;
import com.example.myapplication.Models.ResLogin;
import com.example.myapplication.Models.ResOnePhone;
import com.example.myapplication.Models.ResPhone;
import com.example.myapplication.Models.ResRating;

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

    // get a phone detail by phone id
    @GET("phones/onePhone")
    Call<ResOnePhone> getOnePhone(@Header("Authorization") String token, @Body ReqOnePhone reqOnePhone);

    // get all comments for a phone by id
    @GET("comments/phoneComment")
    Call<ResComment> getAllCommentsForPhone(@Header("Authorization") String token, @Body ReqComment reqComment);

    // get rating point for a phone by id
    @GET("ratings/phoneRating")
    Call<ResRating> getRatingForPhone(@Header("Authorization") String token, @Body ReqRating reqRating);

    // add comment for a phone by userID
    @POST("ratings/rate")
    Call<ResAddRating> addRating(@Header("Authorization") String token, @Body ReqAddRating reqAddRating);

    // add rating for a phone by userID
    @POST("comments/comment")
    Call<ResAddComment> addComment(@Header("Authorization") String token, @Body ReqAddComment reqAddComment);
}
