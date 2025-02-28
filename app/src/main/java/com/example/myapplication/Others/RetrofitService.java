package com.example.myapplication.Others;

import com.example.myapplication.Models.ReqAddComment;
import com.example.myapplication.Models.ReqAddRating;
import com.example.myapplication.Models.ReqLogin;
import com.example.myapplication.Models.ResAddComment;
import com.example.myapplication.Models.ResAddRating;
import com.example.myapplication.Models.ResBrand;
import com.example.myapplication.Models.ResComment;
import com.example.myapplication.Models.ReqSignup;
import com.example.myapplication.Models.ResBillsFull;
import com.example.myapplication.Models.ResLogin;
import com.example.myapplication.Models.ResOnePhone;
import com.example.myapplication.Models.ResPhone;
import com.example.myapplication.Models.ResRating;
import com.example.myapplication.Models.ResSignup;
import com.example.myapplication.Models.ResType;
import com.example.myapplication.Models.ResUser;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface RetrofitService {
    //https://project3-pma1011-backend-q2ql.onrender.com
    //https://project3-pma1011-backend.onrender.com
    public static final String BASE_URL = "https://project3-pma1011-backend-q2ql.onrender.com";

    // login api
    @POST("users/login")
    Call<ResLogin> Login(@Body ReqLogin reqLogin);

    // signup api
    @POST("users/signup")
    Call<ResSignup> signup(@Body ReqSignup reqSignup);

    // get all phones
    @GET("phones/home")
    Call<List<ResPhone>> getAllPhones(@Header("Authorization") String token);  // Add the token header

    // get all types
    @GET("types/allTypes")
    Call<List<ResType>> getAllTypes(@Header("Authorization") String token);

    // get all brand
    @GET("brands/allBrands")
    Call<List<ResBrand>> getAllBrands(@Header("Authorization") String token);

    // get a phone detail by phone id
    @GET("phones/onePhone")
    Call<ResOnePhone> getOnePhone(@Header("Authorization") String token, @Query("phoneID") String phoneID);

    // get all comments for a phone by id
    @GET("comments/phoneComment")
    Call<ResComment> getAllCommentsForPhone(@Header("Authorization") String token, @Query("phoneID") String phoneID);

    // get rating score for a phone by id
    @GET("ratings/phoneRating")
    Call<ResRating> getRatingForPhone(@Header("Authorization") String token, @Query("phoneID") String phoneID);

    // add rating for a phone by userID
    @POST("ratings/rate")
    Call<ResAddRating> addRating(@Header("Authorization") String token, @Body ReqAddRating reqAddRating);

    // add comment for a phone by userID
    @POST("comments/comment")
    Call<ResAddComment> addComment(@Header("Authorization") String token, @Body ReqAddComment reqAddComment);

    // get all users
    @GET("users/allUsers")
    Call<List<ResUser>> getAllUsers(@Header("Authorization") String token);

    // find a user by name or address
    @GET("users/findUsers")
    Call<List<ResUser>> findUsers(
            @Header("Authorization") String authToken,  // Bearer token authorization
            @Query("searchUsers") String searchUsers    // Query parameter for username or address
    );

    // sort all users by bought amount, cancelled amount or voucher owned
    @GET("users/sort")
    Call<List<ResUser>> sortUsers(
            @Header("Authorization") String token,   // Pass Authorization token
            @Query("sortBy") String sortBy          // Query parameter for sorting field
    );

    // edit a user's information
    @Multipart
    @POST("users/edit")
    Call<ResUser> editUserInfo(
            @Header("Authorization") String token, // For passing the Bearer token
            @PartMap Map<String, RequestBody> partMap, // For handling dynamic optional fields
            @Part MultipartBody.Part avatarImg // For handling image uploads
    );

    // get all bills from a user
    @GET("bills/userBill")
    Call<ResBillsFull> getUserBills(
            @Header("Authorization") String authToken,
            @Query("userID") String userID
    );

    // sort a phone list by the parameter
    @GET("phones/sort")
    Call<List<ResPhone>> sortPhones(@Header("Authorization") String token, @Query("sortBy") String sortBy);

    // search phones by brand, type and phone name
    @GET("phones/findPhones")
    Call<List<ResPhone>> findPhones(@Header("Authorization") String token, @Query("searchPhones") String searchPhones);
}
