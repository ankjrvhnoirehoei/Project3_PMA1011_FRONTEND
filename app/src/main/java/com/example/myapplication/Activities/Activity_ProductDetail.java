package com.example.myapplication.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.Models.Ratings;
import com.example.myapplication.Models.ReqAddRating;
import com.example.myapplication.Models.ResAddRating;
import com.example.myapplication.Models.ResOnePhone;
import com.example.myapplication.Models.ResRating;
import com.example.myapplication.Others.RetrofitService;
import com.example.myapplication.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_ProductDetail extends AppCompatActivity {

    ImageView productMainImage, productImage1, productImage2, productImage3, productImage4, productImage5, ratingImage;
    TextView tv_productName, tv_productPrice, tv_productRating, tv_productBrand, tv_productType, tv_productDescription;
    Button bt_commentSection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        productMainImage = findViewById(R.id.productMainImage);
        productImage1 = findViewById(R.id.productImage1);
        productImage2 = findViewById(R.id.productImage2);
        productImage3 = findViewById(R.id.productImage3);
        productImage4 = findViewById(R.id.productImage4);
        productImage5 = findViewById(R.id.productImage5);
        ratingImage = findViewById(R.id.ratingImage);
        tv_productName = findViewById(R.id.tv_productName);
        tv_productPrice = findViewById(R.id.tv_productPrice);
        tv_productRating = findViewById(R.id.tv_productRating);
        tv_productBrand = findViewById(R.id.tv_productBrand);
        tv_productType = findViewById(R.id.tv_productType);
        tv_productDescription = findViewById(R.id.tv_productDescription);
        bt_commentSection = findViewById(R.id.bt_commentSection);

        // moving user to comment section screen
        bt_commentSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(), Activity_ProductCommentsSection.class);
                startActivity(in);
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);

        // get token, phoneId and userID from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        String token = "Bearer" + sharedPreferences.getString("AuthToken", null);
        String phoneID = sharedPreferences.getString("sharedPhoneID", null);
        String userID = sharedPreferences.getString("loginUserID", null);

        // click to show the rating dialog
        ratingImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_ProductDetail.this);
                LayoutInflater inf = Activity_ProductDetail.this.getLayoutInflater();
                View dialogView = inf.inflate(R.layout.dialog_user_rating, null);
                builder.setView(dialogView);

                AlertDialog dialog = builder.create();

                ImageView img_star1, img_star2, img_star3, img_star4, img_star5;
                TextView tv_userRatingScore;
                Button btn_commit;
                int[] ratingValue = {1, 2, 3, 4, 5};

                img_star1 = dialogView.findViewById(R.id.img_star1);
                img_star2 = dialogView.findViewById(R.id.img_star2);
                img_star3 = dialogView.findViewById(R.id.img_star3);
                img_star4 = dialogView.findViewById(R.id.img_star4);
                img_star5 = dialogView.findViewById(R.id.img_star5);
                tv_userRatingScore = dialogView.findViewById(R.id.tv_userRatingScore);
                btn_commit = dialogView.findViewById(R.id.btn_commit);

                // clicking any 1 of 5 star in the rating dialog will create a new rating for the rating model
                img_star1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // set image for the clicked following stars
                        img_star1.setImageResource(R.drawable.rating_star);
                        // click a star will get it accordant rating score
                        ReqAddRating reqAddRating = new ReqAddRating(userID, phoneID, ratingValue[0]);
                        retrofitService.addRating(token, reqAddRating).enqueue(new Callback<ResAddRating>() {
                            @Override
                            public void onResponse(Call<ResAddRating> call, Response<ResAddRating> response) {
                                if(response.isSuccessful() && response.body() != null){
                                    ResAddRating resAddRating = response.body();
                                    tv_userRatingScore.setText(ratingValue[0] + "/5");
                                    btn_commit.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if(!resAddRating.getStatus().equals("true")){
                                                Toast.makeText(Activity_ProductDetail.this, "Cảm ơn bạn đã đánh giá sản phẩm!", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            } else {
                                                Toast.makeText(Activity_ProductDetail.this, "Đã xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            }
                                        }
                                    });
                                } else {
                                    Log.e("Response", "Error: " + response.code() + " Message: " + response.message());
                                }
                            }

                            @Override
                            public void onFailure(Call<ResAddRating> call, Throwable throwable) {
                                Log.e("API Failure", "Error: " + throwable.getMessage(), throwable);
                            }
                        });
                    }
                });

                img_star2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // set image for the clicked following stars
                        img_star1.setImageResource(R.drawable.rating_star);
                        img_star2.setImageResource(R.drawable.rating_star);
                        // click a star will get it accordant rating score
                        ReqAddRating reqAddRating = new ReqAddRating(userID, phoneID, ratingValue[1]);
                        retrofitService.addRating(token, reqAddRating).enqueue(new Callback<ResAddRating>() {
                            @Override
                            public void onResponse(Call<ResAddRating> call, Response<ResAddRating> response) {
                                if(response.isSuccessful() && response.body() != null){
                                    ResAddRating resAddRating = response.body();
                                    tv_userRatingScore.setText(ratingValue[1] + "/5");
                                    btn_commit.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if(!resAddRating.getStatus().equals("true")){
                                                Toast.makeText(Activity_ProductDetail.this, "Cảm ơn bạn đã đánh giá sản phẩm!", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            } else {
                                                Toast.makeText(Activity_ProductDetail.this, "Đã xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            }
                                        }
                                    });
                                } else {
                                    Log.e("Response", "Error: " + response.code() + " Message: " + response.message());
                                }
                            }

                            @Override
                            public void onFailure(Call<ResAddRating> call, Throwable throwable) {
                                Log.e("API Failure", "Error: " + throwable.getMessage(), throwable);
                            }
                        });
                    }
                });

                img_star3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // set image for the clicked following stars
                        img_star1.setImageResource(R.drawable.rating_star);
                        img_star2.setImageResource(R.drawable.rating_star);
                        img_star3.setImageResource(R.drawable.rating_star);
                        // click a star will get it accordant rating score
                        ReqAddRating reqAddRating = new ReqAddRating(userID, phoneID, ratingValue[2]);
                        retrofitService.addRating(token, reqAddRating).enqueue(new Callback<ResAddRating>() {
                            @Override
                            public void onResponse(Call<ResAddRating> call, Response<ResAddRating> response) {
                                if(response.isSuccessful() && response.body() != null){
                                    ResAddRating resAddRating = response.body();
                                    tv_userRatingScore.setText(ratingValue[2] + "/5");
                                    btn_commit.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if(!resAddRating.getStatus().equals("true")){
                                                Toast.makeText(Activity_ProductDetail.this, "Cảm ơn bạn đã đánh giá sản phẩm!", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            } else {
                                                Toast.makeText(Activity_ProductDetail.this, "Đã xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            }
                                        }
                                    });
                                } else {
                                    Log.e("Response", "Error: " + response.code() + " Message: " + response.message());
                                }
                            }

                            @Override
                            public void onFailure(Call<ResAddRating> call, Throwable throwable) {
                                Log.e("API Failure", "Error: " + throwable.getMessage(), throwable);
                            }
                        });
                    }
                });

                img_star4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // set image for the clicked following stars
                        img_star1.setImageResource(R.drawable.rating_star);
                        img_star2.setImageResource(R.drawable.rating_star);
                        img_star3.setImageResource(R.drawable.rating_star);
                        img_star4.setImageResource(R.drawable.rating_star);
                        // click a star will get it accordant rating score
                        ReqAddRating reqAddRating = new ReqAddRating(userID, phoneID, ratingValue[3]);
                        retrofitService.addRating(token, reqAddRating).enqueue(new Callback<ResAddRating>() {
                            @Override
                            public void onResponse(Call<ResAddRating> call, Response<ResAddRating> response) {
                                if(response.isSuccessful() && response.body() != null){
                                    ResAddRating resAddRating = response.body();
                                    tv_userRatingScore.setText(ratingValue[3] + "/5");
                                    btn_commit.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if(!resAddRating.getStatus().equals("true")){
                                                Toast.makeText(Activity_ProductDetail.this, "Cảm ơn bạn đã đánh giá sản phẩm!", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            } else {
                                                Toast.makeText(Activity_ProductDetail.this, "Đã xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            }
                                        }
                                    });
                                } else {
                                    Log.e("Response", "Error: " + response.code() + " Message: " + response.message());
                                }
                            }

                            @Override
                            public void onFailure(Call<ResAddRating> call, Throwable throwable) {
                                Log.e("API Failure", "Error: " + throwable.getMessage(), throwable);
                            }
                        });
                    }
                });

                img_star5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // set image for the clicked following stars
                        img_star1.setImageResource(R.drawable.rating_star);
                        img_star2.setImageResource(R.drawable.rating_star);
                        img_star3.setImageResource(R.drawable.rating_star);
                        img_star4.setImageResource(R.drawable.rating_star);
                        img_star5.setImageResource(R.drawable.rating_star);
                        // click a star will get it accordant rating score
                        ReqAddRating reqAddRating = new ReqAddRating(userID, phoneID, ratingValue[4]);
                        retrofitService.addRating(token, reqAddRating).enqueue(new Callback<ResAddRating>() {
                            @Override
                            public void onResponse(Call<ResAddRating> call, Response<ResAddRating> response) {
                                if(response.isSuccessful() && response.body() != null){
                                    ResAddRating resAddRating = response.body();
                                    tv_userRatingScore.setText(ratingValue[4] + "/5");
                                    btn_commit.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if(!resAddRating.getStatus().equals("true")){
                                                Toast.makeText(Activity_ProductDetail.this, "Cảm ơn bạn đã đánh giá sản phẩm!", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            } else {
                                                Toast.makeText(Activity_ProductDetail.this, "Đã xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            }
                                        }
                                    });
                                } else {
                                    Log.e("Response", "Error: " + response.code() + " Message: " + response.message());
                                }
                            }

                            @Override
                            public void onFailure(Call<ResAddRating> call, Throwable throwable) {
                                Log.e("API Failure", "Error: " + throwable.getMessage(), throwable);
                            }
                        });
                    }
                });
            }
        });

        /// click to show the rating dialog
        tv_productRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_ProductDetail.this);
                LayoutInflater inf = Activity_ProductDetail.this.getLayoutInflater();
                View dialogView = inf.inflate(R.layout.dialog_user_rating, null);
                builder.setView(dialogView);

                AlertDialog dialog = builder.create();

                ImageView img_star1, img_star2, img_star3, img_star4, img_star5;
                TextView tv_userRatingScore;
                Button btn_commit;
                int[] ratingValue = {1, 2, 3, 4, 5};

                img_star1 = dialogView.findViewById(R.id.img_star1);
                img_star2 = dialogView.findViewById(R.id.img_star2);
                img_star3 = dialogView.findViewById(R.id.img_star3);
                img_star4 = dialogView.findViewById(R.id.img_star4);
                img_star5 = dialogView.findViewById(R.id.img_star5);
                tv_userRatingScore = dialogView.findViewById(R.id.tv_userRatingScore);
                btn_commit = dialogView.findViewById(R.id.btn_commit);

                img_star1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        img_star1.setImageResource(R.drawable.rating_star);
                        ReqAddRating reqAddRating = new ReqAddRating(userID, phoneID, ratingValue[0]);
                        retrofitService.addRating(token, reqAddRating).enqueue(new Callback<ResAddRating>() {
                            @Override
                            public void onResponse(Call<ResAddRating> call, Response<ResAddRating> response) {
                                if(response.isSuccessful() && response.body() != null){
                                    ResAddRating resAddRating = response.body();
                                    tv_userRatingScore.setText(ratingValue[0] + "/5");
                                    btn_commit.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if(!resAddRating.getStatus().equals("true")){
                                                Toast.makeText(Activity_ProductDetail.this, "Cảm ơn bạn đã đánh giá sản phẩm!", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            } else {
                                                Toast.makeText(Activity_ProductDetail.this, "Đã xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            }
                                        }
                                    });
                                } else {
                                    Log.e("Response", "Error: " + response.code() + " Message: " + response.message());
                                }
                            }

                            @Override
                            public void onFailure(Call<ResAddRating> call, Throwable throwable) {
                                Log.e("API Failure", "Error: " + throwable.getMessage(), throwable);
                            }
                        });
                    }
                });

                img_star2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        img_star1.setImageResource(R.drawable.rating_star);
                        img_star2.setImageResource(R.drawable.rating_star);
                        ReqAddRating reqAddRating = new ReqAddRating(userID, phoneID, ratingValue[1]);
                        retrofitService.addRating(token, reqAddRating).enqueue(new Callback<ResAddRating>() {
                            @Override
                            public void onResponse(Call<ResAddRating> call, Response<ResAddRating> response) {
                                if(response.isSuccessful() && response.body() != null){
                                    ResAddRating resAddRating = response.body();
                                    tv_userRatingScore.setText(ratingValue[1] + "/5");
                                    btn_commit.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if(!resAddRating.getStatus().equals("true")){
                                                Toast.makeText(Activity_ProductDetail.this, "Cảm ơn bạn đã đánh giá sản phẩm!", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            } else {
                                                Toast.makeText(Activity_ProductDetail.this, "Đã xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            }
                                        }
                                    });
                                } else {
                                    Log.e("Response", "Error: " + response.code() + " Message: " + response.message());
                                }
                            }

                            @Override
                            public void onFailure(Call<ResAddRating> call, Throwable throwable) {
                                Log.e("API Failure", "Error: " + throwable.getMessage(), throwable);
                            }
                        });
                    }
                });

                img_star3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        img_star1.setImageResource(R.drawable.rating_star);
                        img_star2.setImageResource(R.drawable.rating_star);
                        img_star3.setImageResource(R.drawable.rating_star);
                        ReqAddRating reqAddRating = new ReqAddRating(userID, phoneID, ratingValue[2]);
                        retrofitService.addRating(token, reqAddRating).enqueue(new Callback<ResAddRating>() {
                            @Override
                            public void onResponse(Call<ResAddRating> call, Response<ResAddRating> response) {
                                if(response.isSuccessful() && response.body() != null){
                                    ResAddRating resAddRating = response.body();
                                    tv_userRatingScore.setText(ratingValue[2] + "/5");
                                    btn_commit.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if(!resAddRating.getStatus().equals("true")){
                                                Toast.makeText(Activity_ProductDetail.this, "Cảm ơn bạn đã đánh giá sản phẩm!", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            } else {
                                                Toast.makeText(Activity_ProductDetail.this, "Đã xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            }
                                        }
                                    });
                                } else {
                                    Log.e("Response", "Error: " + response.code() + " Message: " + response.message());
                                }
                            }

                            @Override
                            public void onFailure(Call<ResAddRating> call, Throwable throwable) {
                                Log.e("API Failure", "Error: " + throwable.getMessage(), throwable);
                            }
                        });
                    }
                });

                img_star4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        img_star1.setImageResource(R.drawable.rating_star);
                        img_star2.setImageResource(R.drawable.rating_star);
                        img_star3.setImageResource(R.drawable.rating_star);
                        img_star4.setImageResource(R.drawable.rating_star);
                        ReqAddRating reqAddRating = new ReqAddRating(userID, phoneID, ratingValue[3]);
                        retrofitService.addRating(token, reqAddRating).enqueue(new Callback<ResAddRating>() {
                            @Override
                            public void onResponse(Call<ResAddRating> call, Response<ResAddRating> response) {
                                if(response.isSuccessful() && response.body() != null){
                                    ResAddRating resAddRating = response.body();
                                    tv_userRatingScore.setText(ratingValue[3] + "/5");
                                    btn_commit.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if(!resAddRating.getStatus().equals("true")){
                                                Toast.makeText(Activity_ProductDetail.this, "Cảm ơn bạn đã đánh giá sản phẩm!", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            } else {
                                                Toast.makeText(Activity_ProductDetail.this, "Đã xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            }
                                        }
                                    });
                                } else {
                                    Log.e("Response", "Error: " + response.code() + " Message: " + response.message());
                                }
                            }

                            @Override
                            public void onFailure(Call<ResAddRating> call, Throwable throwable) {
                                Log.e("API Failure", "Error: " + throwable.getMessage(), throwable);
                            }
                        });
                    }
                });

                img_star5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        img_star1.setImageResource(R.drawable.rating_star);
                        img_star2.setImageResource(R.drawable.rating_star);
                        img_star3.setImageResource(R.drawable.rating_star);
                        img_star4.setImageResource(R.drawable.rating_star);
                        img_star5.setImageResource(R.drawable.rating_star);
                        ReqAddRating reqAddRating = new ReqAddRating(userID, phoneID, ratingValue[4]);
                        retrofitService.addRating(token, reqAddRating).enqueue(new Callback<ResAddRating>() {
                            @Override
                            public void onResponse(Call<ResAddRating> call, Response<ResAddRating> response) {
                                if(response.isSuccessful() && response.body() != null){
                                    ResAddRating resAddRating = response.body();
                                    tv_userRatingScore.setText(ratingValue[4] + "/5");
                                    btn_commit.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if(!resAddRating.getStatus().equals("true")){
                                                Toast.makeText(Activity_ProductDetail.this, "Cảm ơn bạn đã đánh giá sản phẩm!", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            } else {
                                                Toast.makeText(Activity_ProductDetail.this, "Đã xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            }
                                        }
                                    });
                                } else {
                                    Log.e("Response", "Error: " + response.code() + " Message: " + response.message());
                                }
                            }

                            @Override
                            public void onFailure(Call<ResAddRating> call, Throwable throwable) {
                                Log.e("API Failure", "Error: " + throwable.getMessage(), throwable);
                            }
                        });
                    }
                });
            }
        });



        // show all information of a phone by it ID
        retrofitService.getOnePhone(token, phoneID).enqueue(new Callback<ResOnePhone>() {
            @Override
            public void onResponse(Call<ResOnePhone> call, Response<ResOnePhone> response) {
                if(response.isSuccessful() && response.body() != null){
                    ResOnePhone resOnePhone = response.body();
                    tv_productName.setText(resOnePhone.getPhoneName());
                    tv_productPrice.setText(resOnePhone.getPhonePrice() + " VND");
                    tv_productBrand.setText(resOnePhone.getPhoneBrand());
                    tv_productType.setText(resOnePhone.getPhoneType());
                    tv_productDescription.setText(resOnePhone.getPhoneDescription());

                    // put all the images of a phone in their place
                    ArrayList<String> phoneImages = resOnePhone.getImage();

                    if (phoneImages != null && !phoneImages.isEmpty()) {
                        // Get the first image, which is a base64 encoded string
                        String base64MainImage = phoneImages.get(0);
                        String base64Image1 = phoneImages.get(1);
                        String base64Image2 = phoneImages.get(2);
                        String base64Image3 = phoneImages.get(3);
                        String base64Image4 = phoneImages.get(4);
                        String base64Image5 = phoneImages.get(5);


                        // Check if the base64 string contains metadata and strip it
                        if (base64MainImage.startsWith("data:image")) {
                            base64MainImage = base64MainImage.substring(base64MainImage.indexOf(",") + 1);
                        }
                        if (base64Image1.startsWith("data:image")) {
                            base64Image1 = base64Image1.substring(base64Image1.indexOf(",") + 1);
                        }
                        if (base64Image2.startsWith("data:image")) {
                            base64Image2 = base64Image2.substring(base64Image2.indexOf(",") + 1);
                        }
                        if (base64Image3.startsWith("data:image")) {
                            base64Image3 = base64Image3.substring(base64Image3.indexOf(",") + 1);
                        }
                        if (base64Image4.startsWith("data:image")) {
                            base64Image4 = base64Image4.substring(base64Image4.indexOf(",") + 1);
                        }
                        if (base64Image5.startsWith("data:image")) {
                            base64Image5 = base64Image5.substring(base64Image5.indexOf(",") + 1);
                        }

                        // Convert the base64 string into a Bitmap
                        try {
                            byte[] decodedMainImageString = Base64.decode(base64MainImage, Base64.DEFAULT);
                            Bitmap decodedMainImageBitmap = BitmapFactory.decodeByteArray(decodedMainImageString, 0, decodedMainImageString.length);
                            byte[] decodedImage1String = Base64.decode(base64Image1, Base64.DEFAULT);
                            Bitmap decodedImage1Bitmap = BitmapFactory.decodeByteArray(decodedImage1String, 0, decodedImage1String.length);
                            byte[] decodedImage2String = Base64.decode(base64Image2, Base64.DEFAULT);
                            Bitmap decodedImage2Bitmap = BitmapFactory.decodeByteArray(decodedImage2String, 0, decodedImage1String.length);
                            byte[] decodedImage3String = Base64.decode(base64Image3, Base64.DEFAULT);
                            Bitmap decodedImage3Bitmap = BitmapFactory.decodeByteArray(decodedImage3String, 0, decodedImage1String.length);
                            byte[] decodedImage4String = Base64.decode(base64Image4, Base64.DEFAULT);
                            Bitmap decodedImage4Bitmap = BitmapFactory.decodeByteArray(decodedImage4String, 0, decodedImage1String.length);
                            byte[] decodedImage5String = Base64.decode(base64Image5, Base64.DEFAULT);
                            Bitmap decodedImage5Bitmap = BitmapFactory.decodeByteArray(decodedImage5String, 0, decodedImage1String.length);

                            // Set the decoded Bitmap main image of product to the ImageView
                            productMainImage.setImageBitmap(decodedMainImageBitmap);
                            // If user click in one of five image below, change the main image with the clicked image
                            productImage1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    productMainImage.setImageBitmap(decodedImage1Bitmap);
                                }
                            });
                            productImage2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    productMainImage.setImageBitmap(decodedImage2Bitmap);
                                }
                            });
                            productImage3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    productMainImage.setImageBitmap(decodedImage3Bitmap);
                                }
                            });
                            productImage4.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    productMainImage.setImageBitmap(decodedImage4Bitmap);
                                }
                            });
                            productImage5.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    productMainImage.setImageBitmap(decodedImage5Bitmap);
                                }
                            });
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                            // If there's an error decoding the image, use the default "no_image.png"
                            productMainImage.setImageResource(R.drawable.no_image);
                            productImage1.setImageResource(R.drawable.no_image);
                            productImage2.setImageResource(R.drawable.no_image);
                            productImage3.setImageResource(R.drawable.no_image);
                            productImage4.setImageResource(R.drawable.no_image);
                            productImage5.setImageResource(R.drawable.no_image);
                        }
                    } else {
                        // If there are no images, use the default "no_image.png"
                        productMainImage.setImageResource(R.drawable.no_image);
                        productImage1.setImageResource(R.drawable.no_image);
                        productImage2.setImageResource(R.drawable.no_image);
                        productImage3.setImageResource(R.drawable.no_image);
                        productImage4.setImageResource(R.drawable.no_image);
                        productImage5.setImageResource(R.drawable.no_image);
                    }
                } else {
                    Log.e("Response", "Error: " + response.code() + " Message: " + response.message());
                }

            }

            @Override
            public void onFailure(Call<ResOnePhone> call, Throwable throwable) {
                Log.e("API Failure", "Error: " + throwable.getMessage(), throwable);
            }
        });

        // show average rating score of a product out of 5
        retrofitService.getRatingForPhone(token, phoneID).enqueue(new Callback<ResRating>() {
            @Override
            public void onResponse(Call<ResRating> call, Response<ResRating> response) {
                if(response.isSuccessful() && response.body() != null){
                    ResRating resRating = response.body();
                    tv_productRating.setText(resRating.getAverageRating() + "/5");
                    ArrayList<Ratings> ratingList = resRating.getRatings();
                    for(int i = 0; i< ratingList.size(); i++){
                        if(ratingList.get(i).getUserID().equals(userID)){
                            ratingImage.setClickable(false);
                            tv_productRating.setClickable(false);
                        }
                    }
                } else {
                    Log.e("Response", "Error: " + response.code() + " Message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ResRating> call, Throwable throwable) {
                Log.e("API Failure", "Error: " + throwable.getMessage(), throwable);
            }
        });
    }

}