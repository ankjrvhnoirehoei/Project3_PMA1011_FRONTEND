package com.example.myapplication.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.Activities.Activity_User_Profile;
import com.example.myapplication.Models.ResUser;
import com.example.myapplication.Others.RetrofitService;
import com.example.myapplication.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_User#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_User extends Fragment {

    ImageView avatar;
    TextView username, phoneNumber, address;
    LinearLayout profileInfo, yourvoucher, yourOrders, storeInfo, logout;
    private ActivityResultLauncher<Intent> launcher;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_User() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_User.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_User newInstance(String param1, String param2) {
        Fragment_User fragment = new Fragment_User();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity_User_Profile.RESULT_OK){
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(RetrofitService.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RetrofitService retrofitService = retrofit.create(RetrofitService.class);

                // get token and userID from SharedPreferences
                SharedPreferences sharedPreferences = requireContext().getSharedPreferences("AppPrefs", MODE_PRIVATE);
                String token = "Bearer " + sharedPreferences.getString("AuthToken", null);
                String userID = sharedPreferences.getString("loginUserID", null);

                retrofitService.getOneUser(token, userID).enqueue(new Callback<ResUser>() {
                    @Override
                    public void onResponse(Call<ResUser> call, Response<ResUser> response) {
                        if(response.isSuccessful() && response.body() != null){
                            String usersImage = response.body().getAvatarImg();

                            if (usersImage != null && !usersImage.isEmpty()) {
                                // Get the image, which is a base64 encoded string
                                String base64Image = usersImage;

                                // Check if the base64 string contains metadata and strip it
                                if (base64Image.startsWith("data:image")) {
                                    base64Image = base64Image.substring(base64Image.indexOf(",") + 1);
                                }

                                // Convert the base64 string into a Bitmap
                                try {
                                    byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
                                    Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                                    // Set the decoded Bitmap to the ImageView
                                    avatar.setImageBitmap(decodedBitmap);
                                } catch (IllegalArgumentException e) {
                                    e.printStackTrace();
                                    // If there's an error decoding the image, use the default "user.png"
                                    avatar.setImageResource(R.drawable.user);
                                }
                            } else {
                                // If there are no images, use the default "user.png"
                                avatar.setImageResource(R.drawable.user);
                            }

                            // showing username, phoneNumber and address from user
                            username.setText(response.body().getUsername());
                            phoneNumber.setText(response.body().getPhoneNumber());
                            address.setText(response.body().getAddress());

                        } else {
                            Log.e("Response", "Error: " + response.code() + " Message: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResUser> call, Throwable throwable) {
                        Log.e("API Failure", "Error: " + throwable.getMessage(), throwable);
                    }
                });
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment__user, container, false);
        avatar = v.findViewById(R.id.avatar);
        phoneNumber = v.findViewById(R.id.phoneNumber);
        address = v.findViewById(R.id.address);
        profileInfo = v.findViewById(R.id.profileInfo);
        yourvoucher = v.findViewById(R.id.yourvoucher);
        yourOrders = v.findViewById(R.id.yourOrders);
        storeInfo = v.findViewById(R.id.storeInfo);
        logout = v.findViewById(R.id.logout);
        username = v.findViewById(R.id.username);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);

        // get token and userID from SharedPreferences
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("AppPrefs", MODE_PRIVATE);
        String token = "Bearer " + sharedPreferences.getString("AuthToken", null);
        String userID = sharedPreferences.getString("loginUserID", null);

        retrofitService.getOneUser(token, userID).enqueue(new Callback<ResUser>() {
            @Override
            public void onResponse(Call<ResUser> call, Response<ResUser> response) {
                if(response.isSuccessful() && response.body() != null){
                    String usersImage = response.body().getAvatarImg();

                    if (usersImage != null && !usersImage.isEmpty()) {
                        // Get the image, which is a base64 encoded string
                        String base64Image = usersImage;

                        // Check if the base64 string contains metadata and strip it
                        if (base64Image.startsWith("data:image")) {
                            base64Image = base64Image.substring(base64Image.indexOf(",") + 1);
                        }

                        // Convert the base64 string into a Bitmap
                        try {
                            byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
                            Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                            // Set the decoded Bitmap to the ImageView
                            avatar.setImageBitmap(decodedBitmap);
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                            // If there's an error decoding the image, use the default "user.png"
                            avatar.setImageResource(R.drawable.user);
                        }
                    } else {
                        // If there are no images, use the default "user.png"
                        avatar.setImageResource(R.drawable.user);
                    }

                    // showing username, phoneNumber and address from user
                    username.setText(response.body().getUsername());
                    phoneNumber.setText(response.body().getPhoneNumber());
                    address.setText(response.body().getAddress());

                } else {
                    Log.e("Response", "Error: " + response.code() + " Message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ResUser> call, Throwable throwable) {
                Log.e("API Failure", "Error: " + throwable.getMessage(), throwable);
            }
        });

        profileInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), Activity_User_Profile.class);
                launcher.launch(i);
            }
        });
        return v;
    }

}