package com.example.myapplication.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapters.Adapter_User_All_Bills;
import com.example.myapplication.Models.ResBill;
import com.example.myapplication.Others.RetrofitService;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_User_Bills_Full extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Adapter_User_All_Bills adapterBills;
    private ArrayList<ResBill> billsList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_bills_full);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get the list of bills passed from the previous activity
        billsList = (ArrayList<ResBill>) getIntent().getSerializableExtra("bills");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);

        // get token, phoneId and userID from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        String token = "Bearer " + sharedPreferences.getString("AuthToken", null);

        retrofitService.allBills(token).enqueue(new Callback<List<ResBill>>() {
            @Override
            public void onResponse(Call<List<ResBill>> call, Response<List<ResBill>> response) {
                if(response.isSuccessful() && response.body() != null){
                    billsList = new ArrayList<>(response.body());

                } else {
                    Log.e("Response", "Error: " + response.code() + " Message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<ResBill>> call, Throwable throwable) {
                Log.e("API Failure", "Error: " + throwable.getMessage(), throwable);
            }
        });
        // Set up the adapter and attach it to the RecyclerView
        adapterBills = new Adapter_User_All_Bills(getApplication(), billsList);
        recyclerView.setAdapter(adapterBills);
    }
}