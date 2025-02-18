package com.example.myapplication.Activities;

import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapters.Adapter_Phones;
import com.example.myapplication.Models.ResPhone;
import com.example.myapplication.Others.RetrofitService;
import com.example.myapplication.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_Search extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Adapter_Phones phoneAdapter;
    private SearchView searchView;
    private RetrofitService retrofitService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchView = findViewById(R.id.searchView);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitService = retrofit.create(RetrofitService.class);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchPhones(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchPhones(newText);
                return false;
            }
        });
    }

    private void searchPhones(String query) {
        String token = "Bearer " + getSavedToken(); // Lấy token đã lưu

        Call<List<ResPhone>> call = retrofitService.searchPhones(token, query);
        call.enqueue(new Callback<List<ResPhone>>() {
            @Override
            public void onResponse(Call<List<ResPhone>> call, Response<List<ResPhone>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    phoneAdapter = new Adapter_Phones(response.body());
                    recyclerView.setAdapter(phoneAdapter);
                } else {
                    Toast.makeText(Activity_Search.this, "Lỗi khi lấy dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ResPhone>> call, Throwable t) {
                Toast.makeText(Activity_Search.this, "Kết nối thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Hàm giả lập lấy token từ SharedPreferences hoặc biến tạm
    private String getSavedToken() {
        return "your_saved_token"; // Thay bằng cách lấy token thực tế
    }
}
