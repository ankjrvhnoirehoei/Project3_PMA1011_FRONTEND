package com.example.myapplication.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.myapplication.Adapters.Adapter_Admin_All_Users;
import com.example.myapplication.Models.ResUser;
import com.example.myapplication.Models.ResUser;
import com.example.myapplication.Others.RetrofitService;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Fragment_Admin_Users extends Fragment {
    RecyclerView rvUser;
    Adapter_Admin_All_Users adapterAdminAllUsers;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment__admin__users, container, false);

        rvUser = v.findViewById(R.id.rvUsersManagement);
        rvUser.setLayoutManager(new LinearLayoutManager(getContext()));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SharedPreferences preferences = requireContext().getSharedPreferences("AppPrefs", MODE_PRIVATE);
        String token = preferences.getString("AuthToken", null);

        if (token != null) {
            RetrofitService retrofitService = retrofit.create(RetrofitService.class);

            // Pass the token as an Authorization header
            Call<List<ResUser>> call = retrofitService.getAllUsers("Bearer " + token);

            call.enqueue(new Callback<List<ResUser>>() {
                @Override
                public void onResponse(Call<List<ResUser>> call, Response<List<ResUser>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        // Set the adapter with the retrieved movie list
                        Log.d("Response", "Users received: " + response.body().size());
                        Log.d("Users List", response.body().toString());
                        ArrayList<ResUser> userList = new ArrayList<>(response.body());
                        adapterAdminAllUsers = new Adapter_Admin_All_Users(getContext(), userList);
                        rvUser.setAdapter(adapterAdminAllUsers);
                    } else {
                        Log.e("Response", "Error: " + response.code() + " Message: " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<List<ResUser>> call, Throwable t) {
                    // Handle failure here
                    Log.e("API Failure", "Error: " + t.getMessage(), t);
                }
            });
        } else {
            Log.e("AuthError", "Token is missing or expired.");
        }

        return v;
    }
}