package com.example.myapplication.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Activities.Activity_ProductDetail;
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
    Adapter_Home_All_Phones.OnClickListener listener;
    ArrayList<ResPhone> phoneList;
    SharedPreferences preferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment__home, container, false);


        rvPhone = v.findViewById(R.id.rvAllPhones);
        rvPhone.setLayoutManager(new GridLayoutManager(getContext(), 2));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        preferences = requireContext().getSharedPreferences("AppPrefs", MODE_PRIVATE);
        String token = preferences.getString("AuthToken", null);

        if (token != null) {
            RetrofitService retrofitService = retrofit.create(RetrofitService.class);

            // Pass the token as an Authorization header
            Call<List<ResPhone>> call = retrofitService.getAllPhones("Bearer " + token);

            call.enqueue(new Callback<List<ResPhone>>() {
                @Override
                public void onResponse(Call<List<ResPhone>> call, Response<List<ResPhone>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        // Set the adapter with the retrieved movie list
                        Log.d("Response", "Phones received: " + response.body().size());
                        Log.d("Phones List", response.body().toString());
                        phoneList = new ArrayList<>(response.body());
                            Adapter_Home_All_Phones adapter = new Adapter_Home_All_Phones(getContext(), phoneList, listener);
                        rvPhone.setAdapter(adapter);

                    } else {
                        Log.e("Response", "Error: " + response.code() + " Message: " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<List<ResPhone>> call, Throwable t) {
                    // Handle failure here
                    Log.e("API Failure", "Error: " + t.getMessage(), t);
                }
            });
        } else {
            Log.e("AuthError", "Token is missing or expired.");
        }

        return v;
    }
    private void setOnClickListener(){
        listener = new Adapter_Home_All_Phones.OnClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent i = new Intent(getContext(), Activity_ProductDetail.class);
                String phoneID = phoneList.get(position).getPhoneID();
                preferences = requireContext().getSharedPreferences("AppPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("sharedPhoneID", phoneID);
                editor.apply();
                startActivity(i);
            }
        };
    }
}