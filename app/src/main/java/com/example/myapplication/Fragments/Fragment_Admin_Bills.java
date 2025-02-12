package com.example.myapplication.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Adapters.Adapter_Admin_All_Bills;
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

public class Fragment_Admin_Bills extends Fragment {
    RecyclerView rvBillsManagement;
    Adapter_Admin_All_Bills adapterAdminAllBills;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment__admin__bills, container, false);


        rvBillsManagement = v.findViewById(R.id.rvBillsManagement);
        rvBillsManagement.setLayoutManager(new LinearLayoutManager(getContext()));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SharedPreferences preferences = requireContext().getSharedPreferences("AppPrefs", MODE_PRIVATE);
        String token = preferences.getString("AuthToken", null);

        if(token != null){
            RetrofitService retrofitService = retrofit.create(RetrofitService.class);

            retrofitService.getAllBills("Bearer " + token).enqueue(new Callback<List<ResBill>>() {
                @Override
                public void onResponse(Call<List<ResBill>> call, Response<List<ResBill>> response) {
                    if(response.isSuccessful() && response.body() != null){
                        Log.d("Response", "Bills received: " + response.body().size());
                        Log.d("Bills List", response.body().toString());
                        ArrayList<ResBill> billList = new ArrayList<>(response.body());
                        adapterAdminAllBills = new Adapter_Admin_All_Bills(getContext(), billList);
                        rvBillsManagement.setAdapter(adapterAdminAllBills);
                    } else {
                        Log.e("Response", "Error: " + response.code() + " Message: " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<List<ResBill>> call, Throwable throwable) {
                    Log.e("API Failure", "Error: " + throwable.getMessage(), throwable);
                }
            });
        } else {
            Log.e("AuthError", "Token is missing or expired.");
        }

        return v;
    }
}