package com.example.myapplication.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Activities.Activity_ProductDetail;
import com.example.myapplication.Adapters.Adapter_Admin_All_Phones;
import com.example.myapplication.Adapters.Adapter_Home_All_Phones;
import com.example.myapplication.Models.ResPhone;
import com.example.myapplication.Others.RetrofitService;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Fragment_Admin_Phones extends Fragment {
    RecyclerView rvPhonesManagement;
    ArrayList<ResPhone> phonesList;
    Adapter_Admin_All_Phones adapter;
    SharedPreferences sharedPreferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment__admin__phones, container, false);

        rvPhonesManagement = v.findViewById(R.id.rvPhonesManagement);
        rvPhonesManagement.setLayoutManager(new LinearLayoutManager(getContext()));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);

        // get token, phoneId and userID from SharedPreferences
        sharedPreferences = requireContext().getSharedPreferences("AppPrefs", MODE_PRIVATE);
        String token = "Bearer " + sharedPreferences.getString("AuthToken", null);

        retrofitService.getAllPhones(token).enqueue(new Callback<List<ResPhone>>() {
            @Override
            public void onResponse(Call<List<ResPhone>> call, Response<List<ResPhone>> response) {
                if(response.isSuccessful() && response.body() != null){
                    phonesList = new ArrayList<>(response.body());
                    adapter = new Adapter_Admin_All_Phones(getContext(), phonesList);
                    rvPhonesManagement.setAdapter(adapter);
                } else {
                    Log.e("Response", "Error: " + response.code() + " Message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<ResPhone>> call, Throwable throwable) {
                Log.e("API Failure", "Error: " + throwable.getMessage(), throwable);
            }
        });
        return v;
    }
}