package com.example.myapplication.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.Activities.Activity_ProductDetail;
import com.example.myapplication.Adapters.Adapter_Home_All_Phones;
import com.example.myapplication.Models.ResBrand;
import com.example.myapplication.Models.ResPhone;
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

public class Fragment_Home extends Fragment {
    RecyclerView rvPhone;
    Adapter_Home_All_Phones adapterHomeAllPhones;
    Adapter_Home_All_Phones.OnClickListener listener;
    ArrayList<ResPhone> phoneList, filteredSearchList, filteredSpinnerList;
    SharedPreferences preferences;
    Spinner sp_sort;
    SearchView sv_searchByProductName;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment__home, container, false);

        sp_sort = v.findViewById(R.id.sp_sort);
        sv_searchByProductName = v.findViewById(R.id.sv_searchByProductName);
        rvPhone = v.findViewById(R.id.rvAllPhones);
        rvPhone.setLayoutManager(new GridLayoutManager(getContext(), 2));
        setOnClickListener();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        preferences = requireContext().getSharedPreferences("AppPrefs", MODE_PRIVATE);
        String token = preferences.getString("AuthToken", null);

        if (token != null) {
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
                            adapterHomeAllPhones = new Adapter_Home_All_Phones(getContext(), phoneList, listener);
                        rvPhone.setAdapter(adapterHomeAllPhones);

                        // modify searchView to search for phone name
                        sv_searchByProductName.clearFocus();
                        sv_searchByProductName.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                            @Override
                            public boolean onQueryTextSubmit(String query) {
                                if(!query.isEmpty()){
                                    findPhones(query);
                                } else {
                                    adapterHomeAllPhones.setFilteredList(phoneList);
                                }
                                return true;
                            }

                            @Override
                            public boolean onQueryTextChange(String newText) {
                                if(newText.isEmpty()){
                                    filteredSearchList.clear();
                                    adapterHomeAllPhones.setFilteredList(phoneList);
                                }
//                for(int i = 0; i<phoneList.size(); i++){
//                    Log.d("phoneList: ", phoneList.get(i).getPhoneName());
//                }
//                // check the item's name are contain text from the searchView that user type
//                if(filteredSpinnerList.isEmpty()){
//                    for(ResPhone item : phoneList) {
//                        if (item.getPhoneName().toLowerCase().contains(newText.toLowerCase()) || item.getPhoneBrand().toLowerCase().contains(newText.toLowerCase())) {
//                            // adding to new item list
//                            filteredSearchList.add(item);
//                        }
//                    }
//                } else {
//                    for(ResPhone item : filteredSpinnerList){
//                        if(item.getPhoneName().toLowerCase().contains(newText.toLowerCase()) || item.getPhoneBrand().toLowerCase().contains(newText.toLowerCase())){
//                            // adding to new item list
//                            filteredSearchList.add(item);
//                        }
//                    }
//                }
//                for(int i = 0; i<filteredSearchList.size(); i++){
//                    Log.d("filteredPhoneList: ", filteredSearchList.get(i).getPhoneName());
//                }
//                if(filteredSearchList.isEmpty()){
//                    Toast.makeText(getContext(), "Không tìm thấy điện thoại", Toast.LENGTH_SHORT).show();
//                } else {
//                    // show new item list contains filtered item
//                    adapterHomeAllPhones.setFilteredList(filteredSearchList);
//                }
                                return true;
                            }
                        });
                        String[] spOption = {"Lọc theo hãng", "Lọc theo giá tiền", "Lọc theo tính năng"};
                        ArrayAdapter<String> spOptionAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, spOption);
                        filteredSearchList = new ArrayList<>();
                        sp_sort.setAdapter(spOptionAdapter);
                        sp_sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                if(sp_sort.getItemAtPosition(i).toString().equals(spOption[0])){
                                    if(filteredSearchList.isEmpty()){
                                        retrofitService.getAllBrands("Bearer " + token).enqueue(new Callback<List<ResBrand>>() {
                                            @Override
                                            public void onResponse(Call<List<ResBrand>> call, Response<List<ResBrand>> response) {
                                                if(response.isSuccessful() && response.body() != null){
                                                    filteredSpinnerList = new ArrayList<>();
                                                    for(ResBrand brands : response.body()){
                                                        for(ResPhone item : phoneList){
                                                            if(item.getPhoneBrand().equals(brands.getBrandID())){
                                                                filteredSpinnerList.add(item);
                                                            }
                                                        }
                                                    }
                                                    adapterHomeAllPhones.setFilteredList(filteredSpinnerList);
                                                } else {
                                                    Log.e("Response", "Error: " + response.code() + " Message: " + response.message());
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<List<ResBrand>> call, Throwable throwable) {
                                                Log.e("API Failure", "Error: " + throwable.getMessage(), throwable);
                                            }
                                        });
                                    } else {
                                        retrofitService.getAllBrands("Bearer " + token).enqueue(new Callback<List<ResBrand>>() {
                                            @Override
                                            public void onResponse(Call<List<ResBrand>> call, Response<List<ResBrand>> response) {
                                                if(response.isSuccessful() && response.body() != null){
                                                    filteredSpinnerList = new ArrayList<>();
                                                    for(ResBrand brands : response.body()){
                                                        for(ResPhone item : filteredSearchList){
                                                            if(item.getPhoneBrand().equals(brands.getBrandID())){
                                                                filteredSpinnerList.add(item);
                                                            }
                                                        }
                                                    }
                                                    adapterHomeAllPhones.setFilteredList(filteredSpinnerList);
                                                } else {
                                                    Log.e("Response", "Error: " + response.code() + " Message: " + response.message());
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<List<ResBrand>> call, Throwable throwable) {
                                                Log.e("API Failure", "Error: " + throwable.getMessage(), throwable);
                                            }
                                        });
                                    }
                                } else if (sp_sort.getItemAtPosition(i).toString().equals(spOption[1])){
                                    if(filteredSearchList.isEmpty()){
                                        retrofitService.sortPhones("Bearer " + token, "phonePrice").enqueue(new Callback<List<ResPhone>>() {
                                            @Override
                                            public void onResponse(Call<List<ResPhone>> call, Response<List<ResPhone>> response) {
                                                if(response.isSuccessful() && response.body() != null){
                                                    filteredSpinnerList = new ArrayList<>(response.body());
                                                    adapterHomeAllPhones.setFilteredList(filteredSpinnerList);
                                                } else {
                                                    Log.e("Response", "Error: " + response.code() + " Message: " + response.message());
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<List<ResPhone>> call, Throwable throwable) {
                                                Log.e("API Failure", "Error: " + throwable.getMessage(), throwable);
                                            }
                                        });
                                    } else {
                                        retrofitService.sortPhones("Bearer " + token, "phonePrice").enqueue(new Callback<List<ResPhone>>() {
                                            @Override
                                            public void onResponse(Call<List<ResPhone>> call, Response<List<ResPhone>> response) {
                                                if(response.isSuccessful() && response.body() != null){
                                                    filteredSpinnerList = new ArrayList<>(response.body());
                                                    ArrayList<ResPhone> nonDuplicateList = new ArrayList<>();
                                                    for(ResPhone spinnerItem : filteredSpinnerList){
                                                        for(ResPhone searchItem : filteredSearchList){
                                                            if(spinnerItem.equals(searchItem)){
                                                                nonDuplicateList.add(spinnerItem);
                                                            }
                                                        }
                                                    }
                                                    adapterHomeAllPhones.setFilteredList(nonDuplicateList);
                                                } else {
                                                    Log.e("Response", "Error: " + response.code() + " Message: " + response.message());
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<List<ResPhone>> call, Throwable throwable) {
                                                Log.e("API Failure", "Error: " + throwable.getMessage(), throwable);
                                            }
                                        });
                                    }
                                } else if (sp_sort.getItemAtPosition(i).toString().equals(spOption[2])){
                                    if(filteredSearchList.isEmpty()){
                                        retrofitService.getAllTypes("Bearer " + token).enqueue(new Callback<List<ResType>>() {
                                            @Override
                                            public void onResponse(Call<List<ResType>> call, Response<List<ResType>> response) {
                                                if(response.isSuccessful() && response.body() != null){
                                                    filteredSpinnerList = new ArrayList<>();
                                                    for(ResType types : response.body()){
                                                        for(ResPhone item : phoneList){
                                                            if(item.getPhoneType().equals(types.getTypeID())){
                                                                filteredSpinnerList.add(item);
                                                            }
                                                        }
                                                    }
                                                    adapterHomeAllPhones.setFilteredList(filteredSpinnerList);
                                                } else {
                                                    Log.e("Response", "Error: " + response.code() + " Message: " + response.message());
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<List<ResType>> call, Throwable throwable) {
                                                Log.e("API Failure", "Error: " + throwable.getMessage(), throwable);
                                            }
                                        });
                                    } else {
                                        retrofitService.getAllTypes("Bearer " + token).enqueue(new Callback<List<ResType>>() {
                                            @Override
                                            public void onResponse(Call<List<ResType>> call, Response<List<ResType>> response) {
                                                if(response.isSuccessful() && response.body() != null){
                                                    filteredSpinnerList = new ArrayList<>();
                                                    for(ResType types : response.body()){
                                                        for(ResPhone item : filteredSearchList){
                                                            if(item.getPhoneType().equals(types.getTypeID())){
                                                                filteredSpinnerList.add(item);
                                                            }
                                                        }
                                                    }
                                                    adapterHomeAllPhones.setFilteredList(filteredSpinnerList);
                                                } else {
                                                    Log.e("Response", "Error: " + response.code() + " Message: " + response.message());
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<List<ResType>> call, Throwable throwable) {
                                                Log.e("API Failure", "Error: " + throwable.getMessage(), throwable);
                                            }
                                        });
                                    }
                                }
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
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

    private void findPhones(String text){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        preferences = requireContext().getSharedPreferences("AppPrefs", MODE_PRIVATE);
        String token = preferences.getString("AuthToken", null);
        retrofitService.findPhones("Bearer " + token, text).enqueue(new Callback<List<ResPhone>>() {
            @Override
            public void onResponse(Call<List<ResPhone>> call, Response<List<ResPhone>> response) {
                if(response.isSuccessful() && response.body() != null){
                    filteredSearchList = new ArrayList<>(response.body());
                    adapterHomeAllPhones.setFilteredList(filteredSearchList);
                } else {
                    Log.e("Response", "Error: " + response.code() + " Message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<ResPhone>> call, Throwable throwable) {
                Log.e("API Failure", "Error: " + throwable.getMessage(), throwable);
            }
        });
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