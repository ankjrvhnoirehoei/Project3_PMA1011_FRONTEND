package com.example.myapplication.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.Models.ResUser;
import com.example.myapplication.Others.RetrofitService;
import com.example.myapplication.R;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Activity_User_Profile extends AppCompatActivity {

    ImageView avatarImage;
    EditText fullName, phoneNumber, address, changePassword;
    ImageButton editFullName, editPhoneNumber, editAddress, editPassword;
    LinearLayout backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        avatarImage = findViewById(R.id.avatarImage);
        fullName = findViewById(R.id.fullName);
        phoneNumber = findViewById(R.id.phoneNumber);
        address = findViewById(R.id.address);
        changePassword = findViewById(R.id.changePassword);
        editFullName = findViewById(R.id.editFullName);
        editPhoneNumber = findViewById(R.id.editPhoneNumber);
        editAddress = findViewById(R.id.editAddress);
        editPassword = findViewById(R.id.editPassword);
        backButton = findViewById(R.id.backButton);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);

        // get token and userID from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
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
                            avatarImage.setImageBitmap(decodedBitmap);
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                            // If there's an error decoding the image, use the default "user.png"
                            avatarImage.setImageResource(R.drawable.user);
                        }
                    } else {
                        // If there are no images, use the default "user.png"
                        avatarImage.setImageResource(R.drawable.user);
                    }

                    // showing username, phoneNumber and address from user
                    fullName.setText(response.body().getUsername());
                    phoneNumber.setText(response.body().getPhoneNumber());
                    address.setText(response.body().getAddress());
                    changePassword.setText(response.body().getPassword());
                } else {
                    Log.e("Response", "Error: " + response.code() + " Message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ResUser> call, Throwable throwable) {
                Log.e("API Failure", "Error: " + throwable.getMessage(), throwable);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        editFullName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, RequestBody> partMap = new HashMap<>();
                partMap.put("userID", createPartFromString(userID));
                if(fullName != null){
                    partMap.put("username", createPartFromString(fullName.getText().toString()));
                }
                retrofitService.editUserInfo(token, partMap, null).enqueue(new Callback<ResUser>() {
                    @Override
                    public void onResponse(Call<ResUser> call, Response<ResUser> response) {
                        if(response.isSuccessful() && response.body() != null){
                            Toast.makeText(Activity_User_Profile.this, "Đã cập nhật thành công", Toast.LENGTH_SHORT).show();
                            retrofitService.getOneUser(token, userID).enqueue(new Callback<ResUser>() {
                                @Override
                                public void onResponse(Call<ResUser> call, Response<ResUser> r) {
                                    if(r.isSuccessful() && r.body() != null){
                                        fullName.setText(r.body().getUsername());
                                    } else {
                                        Log.e("Response", "Error: " + response.code() + " Message: " + response.message());
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResUser> call, Throwable throwable) {
                                    Log.e("API Failure", "Error: " + throwable.getMessage(), throwable);
                                }
                            });
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

        editAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, RequestBody> partMap = new HashMap<>();
                partMap.put("userID", createPartFromString(userID));
                if(address != null){
                    partMap.put("address", createPartFromString(address.getText().toString()));
                }
                retrofitService.editUserInfo(token, partMap, null).enqueue(new Callback<ResUser>() {
                    @Override
                    public void onResponse(Call<ResUser> call, Response<ResUser> response) {
                        if(response.isSuccessful() && response.body() != null){
                            Toast.makeText(Activity_User_Profile.this, "Đã cập nhật thành công", Toast.LENGTH_SHORT).show();
                            retrofitService.getOneUser(token, userID).enqueue(new Callback<ResUser>() {
                                @Override
                                public void onResponse(Call<ResUser> call, Response<ResUser> r) {
                                    if(r.isSuccessful() && r.body() != null){
                                        address.setText(r.body().getAddress());
                                    } else {
                                        Log.e("Response", "Error: " + response.code() + " Message: " + response.message());
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResUser> call, Throwable throwable) {
                                    Log.e("API Failure", "Error: " + throwable.getMessage(), throwable);
                                }
                            });
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

        editPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, RequestBody> partMap = new HashMap<>();
                partMap.put("userID", createPartFromString(userID));
                if(phoneNumber != null){
                    partMap.put("phoneNumber", createPartFromString(phoneNumber.getText().toString()));
                }
                retrofitService.editUserInfo(token, partMap, null).enqueue(new Callback<ResUser>() {
                    @Override
                    public void onResponse(Call<ResUser> call, Response<ResUser> response) {
                        if(response.isSuccessful() && response.body() != null){
                            Toast.makeText(Activity_User_Profile.this, "Đã cập nhật thành công", Toast.LENGTH_SHORT).show();
                            retrofitService.getOneUser(token, userID).enqueue(new Callback<ResUser>() {
                                @Override
                                public void onResponse(Call<ResUser> call, Response<ResUser> r) {
                                    if(r.isSuccessful() && r.body() != null){
                                        phoneNumber.setText(r.body().getPhoneNumber());
                                    } else {
                                        Log.e("Response", "Error: " + response.code() + " Message: " + response.message());
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResUser> call, Throwable throwable) {
                                    Log.e("API Failure", "Error: " + throwable.getMessage(), throwable);
                                }
                            });
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

        editPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, RequestBody> partMap = new HashMap<>();
                partMap.put("userID", createPartFromString(userID));
                if(changePassword != null){
                    partMap.put("password", createPartFromString(changePassword.getText().toString()));
                }
                retrofitService.editUserInfo(token, partMap, null).enqueue(new Callback<ResUser>() {
                    @Override
                    public void onResponse(Call<ResUser> call, Response<ResUser> response) {
                        if(response.isSuccessful() && response.body() != null){
                            Toast.makeText(Activity_User_Profile.this, "Đã cập nhật thành công", Toast.LENGTH_SHORT).show();
                            retrofitService.getOneUser(token, userID).enqueue(new Callback<ResUser>() {
                                @Override
                                public void onResponse(Call<ResUser> call, Response<ResUser> r) {
                                    if(r.isSuccessful() && r.body() != null){
                                        changePassword.setText(r.body().getPassword());
                                    } else {
                                        Log.e("Response", "Error: " + response.code() + " Message: " + response.message());
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResUser> call, Throwable throwable) {
                                    Log.e("API Failure", "Error: " + throwable.getMessage(), throwable);
                                }
                            });
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

    private RequestBody createPartFromString(String value) {
        return RequestBody.create(MediaType.parse("text/plain"), value);
    }

    @Override
    public void finish(){
        Intent resultIntent = new Intent();
        setResult(RESULT_OK, resultIntent);
        super.finish();
    }
}