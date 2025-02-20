package com.example.myapplication.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapters.Adapter_All_Comments;
import com.example.myapplication.Models.Comments;
import com.example.myapplication.Models.ReqAddComment;
import com.example.myapplication.Models.ResAddComment;
import com.example.myapplication.Models.ResComment;
import com.example.myapplication.Others.RetrofitService;
import com.example.myapplication.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_ProductCommentsSection extends AppCompatActivity {

    EditText edt_userComment;
    Button btn_send;
    RecyclerView rv_allComment;
    Adapter_All_Comments adapterAllComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_comments_section);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edt_userComment = findViewById(R.id.edt_userComment);
        btn_send = findViewById(R.id.btn_send);
        rv_allComment = findViewById(R.id.rv_allComment);

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

        rv_allComment.setLayoutManager(new LinearLayoutManager(Activity_ProductCommentsSection.this));

        retrofitService.getAllCommentsForPhone(token, phoneID).enqueue(new Callback<ResComment>() {
            @Override
            public void onResponse(Call<ResComment> call, Response<ResComment> response) {
                if(response.isSuccessful() && response.body() != null){
                    Log.d("Response", "Comments received: " + response.body().getComments().size());
                    Log.d("Comments List", response.body().getComments().toString());
                    ArrayList<Comments> commentList = response.body().getComments();
                    adapterAllComments = new Adapter_All_Comments(Activity_ProductCommentsSection.this, commentList);
                    rv_allComment.setAdapter(adapterAllComments);
                } else {
                    Log.e("Response", "Error: " + response.code() + " Message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ResComment> call, Throwable throwable) {
                Log.e("API Failure", "Error: " + throwable.getMessage(), throwable);
            }
        });
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReqAddComment reqAddComment = new ReqAddComment(edt_userComment.getText().toString(), userID, phoneID);
                retrofitService.addComment(token, reqAddComment).enqueue(new Callback<ResAddComment>() {
                    @Override
                    public void onResponse(Call<ResAddComment> call, Response<ResAddComment> response) {
                        if(response.isSuccessful() && response.body() != null){
                            if(response.body().getStatus().equals("true")){
                                Toast.makeText(Activity_ProductCommentsSection.this, "Cảm ơn bạn đã bình luận", Toast.LENGTH_SHORT).show();
                                edt_userComment.setText("");
                            } else {
                                Toast.makeText(Activity_ProductCommentsSection.this, "Đã xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.e("Response", "Error: " + response.code() + " Message: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResAddComment> call, Throwable throwable) {
                        Log.e("API Failure", "Error: " + throwable.getMessage(), throwable);
                    }
                });
            }
        });
    }
}