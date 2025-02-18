package com.example.myapplication.Others;

import com.example.myapplication.Models.ReqLogin;
import com.example.myapplication.Models.ReqSignup;
import com.example.myapplication.Models.ReqUser;
import com.example.myapplication.Models.ResBill;
import com.example.myapplication.Models.ResBillDetails;
import com.example.myapplication.Models.ResBillsFull;
import com.example.myapplication.Models.ResLogin;
import com.example.myapplication.Models.ResPhone;
import com.example.myapplication.Models.ResSignup;
import com.example.myapplication.Models.ResUser;

import java.util.ArrayList;
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
    public static final String BASE_URL = "https://project3-pma1011-backend.onrender.com";


    // login api
    @POST("users/login")
    Call<ResLogin> Login(@Body ReqLogin reqLogin);

    // signup api
    @POST("users/signup")
    Call<ResSignup> signup(@Body ReqSignup reqSignup);

    // get all phones
    @GET("phones/home")
    Call<List<ResPhone>> getAllPhones(@Header("Authorization") String token);  // Add the token header

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

    // get all bills
    @GET("bills/all")
    Call<List<ResBill>> getAllBills(@Header("Authorization") String token);

    // get all the details from a bill
    @GET("billdetails/billFullDetails")
    Call<List<ResBillDetails>> getBillDetails(
            @Header("Authorization") String authToken,
            @Query("billID") String billID
    );
    @GET("phones/findPhones")
    Call<List<ResPhone>> searchPhones(
            @Header("Authorization") String authToken,
            @Query("searchPhones") String searchQuery
    );

}
