package com.example.myapplication.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class Fragment_Home extends Fragment {
    RecyclerView rvPhone;
    Adapter_Home_All_Phones adapterHomeAllPhones;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment__home, container, false);


        rvPhone.setLayoutManager(new GridLayoutManager(getContext(), 2));

        SharedPreferences preferences = requireContext().getSharedPreferences("AppPrefs", MODE_PRIVATE);
        String token = preferences.getString("AuthToken", null);

        if (token != null) {
            fetchAllPhones(token);
        } else {
            Log.e("AuthError", "Token is missing or expired. Please log in again.");
        }

        return v;
    }

    private void fetchAllPhones(String token) {
        Retrofit retrofit = createRetrofitInstance();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);

        // Pass the token as an Authorization header
        Call<List<ResPhone>> call = retrofitService.getAllPhones("Bearer " + token);

        call.enqueue(new Callback<List<ResPhone>>() {
            @Override
            public void onResponse(Call<List<ResPhone>> call, Response<List<ResPhone>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("Response", "Phones received: " + response.body().size());
                    ArrayList<ResPhone> phoneList = new ArrayList<>(response.body());
                    adapterHomeAllPhones = new Adapter_Home_All_Phones(getContext(), phoneList);
                    rvPhone.setAdapter(adapterHomeAllPhones);
                } else {
                    Log.e("Response", "Error: " + response.code() + " Message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<ResPhone>> call, Throwable t) {
                Log.e("API Failure", "Error: " + t.getMessage(), t);
            }
        });
    }

    private Retrofit createRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(RetrofitService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
