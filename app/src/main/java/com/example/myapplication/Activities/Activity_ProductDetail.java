package com.example.myapplication.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.myapplication.Models.ResBrand;
import com.example.myapplication.Models.ResOnePhone;
import com.example.myapplication.Models.ResRating;
import com.example.myapplication.Models.ResType;
import com.example.myapplication.Others.RetrofitService;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_ProductDetail extends AppCompatActivity {

    ImageView productMainImage, productImage1, productImage2, productImage3, productImage4, productImage5;
    TextView tv_productName, tv_productPrice, tv_productRating, tv_productBrand, tv_productType, tv_productDescription;
    Button bt_commentSection;
    LinearLayout ratingSection;
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
        tv_productName = findViewById(R.id.tv_productName);
        tv_productPrice = findViewById(R.id.tv_productPrice);
        tv_productRating = findViewById(R.id.tv_productRating);
        tv_productBrand = findViewById(R.id.tv_productBrand);
        tv_productType = findViewById(R.id.tv_productType);
        tv_productDescription = findViewById(R.id.tv_productDescription);
        bt_commentSection = findViewById(R.id.bt_commentSection);
        ratingSection = findViewById(R.id.ratingSection);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);

        // get token, phoneId and userID from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        String token = "Bearer " + sharedPreferences.getString("AuthToken", null);
        String phoneID = sharedPreferences.getString("sharedPhoneID", null);
        String userID = sharedPreferences.getString("loginUserID", null);

        // moving user to comment section screen
        bt_commentSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(), Activity_ProductCommentsSection.class);
                startActivity(in);
            }
        });

        // click to show the rating dialog
        ratingSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("RatingSection Click: ", "RatingSection clicked");
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

                // Create a drawable of RatingStar image to be use as a sample to check the condition logic
                @SuppressLint("UseCompatLoadingForDrawables") Drawable StarDrawable = getResources().getDrawable(R.drawable.rating_star);

                // clicking any 1 of 5 star in the rating dialog will create a new rating for the rating model
                img_star1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // set image for the clicked following stars
                        img_star1.setImageResource(R.drawable.rating_star);
                        // when clicked a star will check if higher rating star are checked or not
                        if(img_star2.getDrawable() != null && img_star2.getDrawable().getConstantState() != null && img_star2.getDrawable().getConstantState().equals(StarDrawable.getConstantState())
                                || img_star3.getDrawable() != null && img_star3.getDrawable().getConstantState() != null && img_star3.getDrawable().getConstantState().equals(StarDrawable.getConstantState())
                                || img_star4.getDrawable() != null && img_star4.getDrawable().getConstantState() != null && img_star4.getDrawable().getConstantState().equals(StarDrawable.getConstantState())
                                || img_star5.getDrawable() != null && img_star5.getDrawable().getConstantState() != null && img_star5.getDrawable().getConstantState().equals(StarDrawable.getConstantState())){
                            // if the condition is true then set all the higher rating star image to be unchecked star one
                            img_star2.setImageResource(R.drawable.unchecked_rating_star);
                            img_star3.setImageResource(R.drawable.unchecked_rating_star);
                            img_star4.setImageResource(R.drawable.unchecked_rating_star);
                            img_star5.setImageResource(R.drawable.unchecked_rating_star);
                        }
                        // click a star will get it accordant rating score
                        tv_userRatingScore.setText(ratingValue[0] + "/5");
                        // click commit button will create a new rating for rating schema by the userID and phoneID
                        ReqAddRating reqAddRating = new ReqAddRating(userID, phoneID, ratingValue[0]);
                        btn_commit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                retrofitService.addRating(token, reqAddRating).enqueue(new Callback<ResAddRating>() {
                                    @Override
                                    public void onResponse(Call<ResAddRating> call, Response<ResAddRating> response) {
                                        if(response.isSuccessful() && response.body() != null){
                                            ResAddRating resAddRating = response.body();
                                            if(resAddRating.getStatus().equals("true")){
                                                Toast.makeText(Activity_ProductDetail.this, "Cảm ơn bạn đã đánh giá sản phẩm!", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            } else {
                                                Toast.makeText(Activity_ProductDetail.this, "Đã xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            }
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
//                        retrofitService.addRating(token, reqAddRating).enqueue(new Callback<ResAddRating>() {
//                            @Override
//                            public void onResponse(Call<ResAddRating> call, Response<ResAddRating> response) {
//                                if(response.isSuccessful() && response.body() != null){
//                                    ResAddRating resAddRating = response.body();
//                                    tv_userRatingScore.setText(ratingValue[0] + "/5");
//                                    btn_commit.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View view) {
//                                            if(resAddRating.getStatus().equals("true")){
//                                                Toast.makeText(Activity_ProductDetail.this, "Cảm ơn bạn đã đánh giá sản phẩm!", Toast.LENGTH_SHORT).show();
//                                                dialog.dismiss();
//                                            } else{
//                                                Toast.makeText(Activity_ProductDetail.this, "Đã xảy ra lỗi!", Toast.LENGTH_SHORT).show();
//                                                dialog.dismiss();
//                                            }
//                                        }
//                                    });
//                                } else {
//                                    Log.e("Response", "Error: " + response.code() + " Message: " + response.message());
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<ResAddRating> call, Throwable throwable) {
//                                Log.e("API Failure", "Error: " + throwable.getMessage(), throwable);
//                            }
//                        });
                    }
                });

                img_star2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // set image for the clicked following stars
                        img_star1.setImageResource(R.drawable.rating_star);
                        img_star2.setImageResource(R.drawable.rating_star);
                        if(img_star3.getDrawable() != null && img_star3.getDrawable().getConstantState() != null && img_star3.getDrawable().getConstantState().equals(StarDrawable.getConstantState())
                                || img_star4.getDrawable() != null && img_star4.getDrawable().getConstantState() != null && img_star4.getDrawable().getConstantState().equals(StarDrawable.getConstantState())
                                || img_star5.getDrawable() != null && img_star5.getDrawable().getConstantState() != null && img_star5.getDrawable().getConstantState().equals(StarDrawable.getConstantState())){
                            img_star3.setImageResource(R.drawable.unchecked_rating_star);
                            img_star4.setImageResource(R.drawable.unchecked_rating_star);
                            img_star5.setImageResource(R.drawable.unchecked_rating_star);
                        }
                        // click a star will get it accordant rating score
                        tv_userRatingScore.setText(ratingValue[1] + "/5");
                        // click commit button will create a new rating for rating schema by the userID and phoneID
                        ReqAddRating reqAddRating = new ReqAddRating(userID, phoneID, ratingValue[1]);
                        btn_commit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                retrofitService.addRating(token, reqAddRating).enqueue(new Callback<ResAddRating>() {
                                    @Override
                                    public void onResponse(Call<ResAddRating> call, Response<ResAddRating> response) {
                                        if(response.isSuccessful() && response.body() != null){
                                            ResAddRating resAddRating = response.body();
                                            if(resAddRating.getStatus().equals("true")){
                                                Toast.makeText(Activity_ProductDetail.this, "Cảm ơn bạn đã đánh giá sản phẩm!", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            } else {
                                                Toast.makeText(Activity_ProductDetail.this, "Đã xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            }
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

                img_star3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // set image for the clicked following stars
                        img_star1.setImageResource(R.drawable.rating_star);
                        img_star2.setImageResource(R.drawable.rating_star);
                        img_star3.setImageResource(R.drawable.rating_star);
                        if(img_star4.getDrawable() != null && img_star4.getDrawable().getConstantState() != null && img_star4.getDrawable().getConstantState().equals(StarDrawable.getConstantState())
                                || img_star5.getDrawable() != null && img_star5.getDrawable().getConstantState() != null && img_star5.getDrawable().getConstantState().equals(StarDrawable.getConstantState())){
                            img_star4.setImageResource(R.drawable.unchecked_rating_star);
                            img_star5.setImageResource(R.drawable.unchecked_rating_star);
                        }
                        // click a star will get it accordant rating score
                        tv_userRatingScore.setText(ratingValue[2] + "/5");
                        // click commit button will create a new rating for rating schema by the userID and phoneID
                        ReqAddRating reqAddRating = new ReqAddRating(userID, phoneID, ratingValue[2]);
                        btn_commit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                retrofitService.addRating(token, reqAddRating).enqueue(new Callback<ResAddRating>() {
                                    @Override
                                    public void onResponse(Call<ResAddRating> call, Response<ResAddRating> response) {
                                        if(response.isSuccessful() && response.body() != null){
                                            ResAddRating resAddRating = response.body();
                                            if(resAddRating.getStatus().equals("true")){
                                                Toast.makeText(Activity_ProductDetail.this, "Cảm ơn bạn đã đánh giá sản phẩm!", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            } else {
                                                Toast.makeText(Activity_ProductDetail.this, "Đã xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            }
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

                img_star4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // set image for the clicked following stars
                        img_star1.setImageResource(R.drawable.rating_star);
                        img_star2.setImageResource(R.drawable.rating_star);
                        img_star3.setImageResource(R.drawable.rating_star);
                        img_star4.setImageResource(R.drawable.rating_star);
                        if(img_star5.getDrawable() != null && img_star5.getDrawable().getConstantState() != null && img_star5.getDrawable().getConstantState().equals(StarDrawable.getConstantState())){
                            img_star5.setImageResource(R.drawable.unchecked_rating_star);
                        }
                        // click a star will get it accordant rating score
                        tv_userRatingScore.setText(ratingValue[3] + "/5");
                        // click commit button will create a new rating for rating schema by the userID and phoneID
                        ReqAddRating reqAddRating = new ReqAddRating(userID, phoneID, ratingValue[3]);
                        btn_commit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                retrofitService.addRating(token, reqAddRating).enqueue(new Callback<ResAddRating>() {
                                    @Override
                                    public void onResponse(Call<ResAddRating> call, Response<ResAddRating> response) {
                                        if(response.isSuccessful() && response.body() != null){
                                            ResAddRating resAddRating = response.body();
                                            if(resAddRating.getStatus().equals("true")){
                                                Toast.makeText(Activity_ProductDetail.this, "Cảm ơn bạn đã đánh giá sản phẩm!", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            } else {
                                                Toast.makeText(Activity_ProductDetail.this, "Đã xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            }
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
                        tv_userRatingScore.setText(ratingValue[4] + "/5");
                        // click commit button will create a new rating for rating schema by the userID and phoneID
                        ReqAddRating reqAddRating = new ReqAddRating(userID, phoneID, ratingValue[4]);
                        btn_commit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                retrofitService.addRating(token, reqAddRating).enqueue(new Callback<ResAddRating>() {
                                    @Override
                                    public void onResponse(Call<ResAddRating> call, Response<ResAddRating> response) {
                                        if(response.isSuccessful() && response.body() != null){
                                            ResAddRating resAddRating = response.body();
                                            if(resAddRating.getStatus().equals("true")){
                                                Toast.makeText(Activity_ProductDetail.this, "Cảm ơn bạn đã đánh giá sản phẩm!", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            } else {
                                                Toast.makeText(Activity_ProductDetail.this, "Đã xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            }
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
                dialog.show();
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
                    // call API to get all brands
                    retrofitService.getAllBrands(token).enqueue(new Callback<List<ResBrand>>() {
                        @Override
                        public void onResponse(Call<List<ResBrand>> call, Response<List<ResBrand>> response) {
                            if(response.isSuccessful() && response.body() != null){
                                ArrayList<ResBrand> brandList = new ArrayList<ResBrand>(response.body());
                                Log.d("productBrandID; ", resOnePhone.getPhoneBrand());
                                boolean chkBrand = false;
                                // get every brandID from brand list then compare with product's brandID
                                for(int i = 0; i<brandList.size(); i++){
                                    Log.d("brandID: ", brandList.get(i).getBrandID());
                                    if(brandList.get(i).getBrandID().equals(resOnePhone.getPhoneBrand())){
                                        // if the condition is true, set text and break from for loop
                                        tv_productBrand.setText("Hãng sản phẩm: " + brandList.get(i).getBrand());
                                        chkBrand = true;
                                        break;
                                    }
                                }
                                if(resOnePhone.getPhoneBrand().isEmpty()){
                                    tv_productBrand.setText("Sản phẩm hiện chưa cập nhật hãng sản phẩm");
                                }
                                if(!chkBrand){
                                    tv_productBrand.setText("Chưa có hãng sản phẩm này");
                                }
                            } else {
                                Log.e("Response", "Error: " + response.code() + " Message: " + response.message());
                            }
                        }
                        @Override
                        public void onFailure(Call<List<ResBrand>> call, Throwable throwable) {
                            Log.e("API Failure", "Error: " + throwable.getMessage(), throwable);
                        }
                    });
                    retrofitService.getAllTypes(token).enqueue(new Callback<List<ResType>>() {
                        @Override
                        public void onResponse(Call<List<ResType>> call, Response<List<ResType>> response) {
                            if(response.isSuccessful() && response.body() != null){
                                ArrayList<ResType> typeList = new ArrayList<ResType>(response.body());
                                Log.d("productTypeID; ", resOnePhone.getPhoneType());
                                boolean chkType = false;
                                for(int i = 0; i<typeList.size(); i++){
                                    Log.d("typeID: ", typeList.get(i).getTypeID());
                                    if(typeList.get(i).getTypeID().equals(resOnePhone.getPhoneType())){
                                        tv_productType.setText("Loại sản phẩm: " + typeList.get(i).getType());
                                        chkType = true;
                                        break;
                                    }
                                }
                                if(resOnePhone.getPhoneType().isEmpty()){
                                    tv_productType.setText("Sản phẩm hiện chưa cập nhật loại sản phẩm");
                                }
                                if(!chkType){
                                    tv_productType.setText("Chưa có loại sản phẩm này");
                                }
                            } else {
                                Log.e("Response", "Error: " + response.code() + " Message: " + response.message());
                            }
                        }
                        @Override
                        public void onFailure(Call<List<ResType>> call, Throwable throwable) {
                            Log.e("API Failure", "Error: " + throwable.getMessage(), throwable);
                        }
                    });
                    tv_productDescription.setText(resOnePhone.getPhoneDescription());

                    // put all the images of a phone in their place
                    ArrayList<String> phoneImages = resOnePhone.getImage();

                    if (phoneImages != null && !phoneImages.isEmpty()) {
                        // Get the first image, which is a base64 encoded string
                        String base64MainImage = phoneImages.get(0);
                        ImageView[] imageViews = {productImage1, productImage2, productImage3, productImage4, productImage5};

                        // Check if the base64 string contains metadata and strip it
                        if (base64MainImage.startsWith("data:image")) {
                            base64MainImage = base64MainImage.substring(base64MainImage.indexOf(",") + 1);
                        }

                        // Convert the base64 string into a Bitmap
                        try {
                            byte[] decodedMainImageString = Base64.decode(base64MainImage, Base64.DEFAULT);
                            Bitmap decodedMainImageBitmap = BitmapFactory.decodeByteArray(decodedMainImageString, 0, decodedMainImageString.length);
                            // Set the decoded Bitmap of product to the ImageView
                            productMainImage.setImageBitmap(decodedMainImageBitmap);
                            for(int i = 1; i <= 5; i++){
                                if(i<phoneImages.size()){
                                    String base64Image = phoneImages.get(i);
                                    if(base64Image.startsWith("data:image")) {
                                        base64Image = base64Image.substring(base64Image.indexOf(",") + 1);
                                    }
                                    byte[] decodedImageString = Base64.decode(base64Image, Base64.DEFAULT);
                                    Bitmap decodedImageBitmap = BitmapFactory.decodeByteArray(decodedImageString, 0, decodedImageString.length);

                                    imageViews[i-1].setImageBitmap(decodedImageBitmap);
                                    imageViews[i-1].setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            productMainImage.setImageBitmap(decodedImageBitmap);
                                        }
                                    });
                                } else {
                                    imageViews[i-1].setImageResource(R.drawable.no_image);
                                }
                            }
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                            Log.e("API ERROR: ", e.getMessage());
                            // If there's an error decoding the image, use the default "no_image.png"
                            productMainImage.setImageResource(R.drawable.no_image);
                            productImage1.setImageResource(R.drawable.no_image);
                            productImage2.setImageResource(R.drawable.no_image);
                            productImage3.setImageResource(R.drawable.no_image);
                            productImage4.setImageResource(R.drawable.no_image);
                            productImage5.setImageResource(R.drawable.no_image);
                        }
                    } else {
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

        // show average rating score of a product
        retrofitService.getRatingForPhone(token, phoneID).enqueue(new Callback<ResRating>() {
            @Override
            public void onResponse(Call<ResRating> call, Response<ResRating> response) {
                if(response.isSuccessful() && response.body() != null){
                    ResRating resRating = response.body();
                    tv_productRating.setText(resRating.getAverageRating() + "/5");
                    ArrayList<Ratings> ratingList = resRating.getRatings();
                    for(int i = 0; i< ratingList.size(); i++){
                        if(ratingList.get(i).getUserID().equals(userID) && ratingList.get(i).getPhoneID().equals(phoneID)){
//                            ratingSection.setClickable(false);
                        }
                    }
                    if(response.code() == 404){
                        tv_productRating.setText("0/5");
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