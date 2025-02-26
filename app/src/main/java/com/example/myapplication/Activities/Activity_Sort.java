package com.example.myapplication.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

public class Activity_Sort extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Adapter_Phones phoneAdapter;
    private RetrofitService retrofitService;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);

        Toolbar toolbar = findViewById(R.id.toolbars);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar = findViewById(R.id.progressBar);

        retrofitService = new Retrofit.Builder()
                .baseUrl(RetrofitService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RetrofitService.class);

        fetchSortedPhones("phonePrice"); // Mặc định sắp xếp theo giá
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_sort, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.sort_price) {
            fetchSortedPhones("phonePrice");
        } else if (id == R.id.sort_sold) {
            fetchSortedPhones("phoneSold");
        } else if (id == R.id.sort_stock) {
            fetchSortedPhones("phoneStock");
        } else if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchSortedPhones(String sortBy) {
        String token = getSavedToken();
        if (token.isEmpty()) {
            Toast.makeText(this, "Bạn chưa đăng nhập!", Toast.LENGTH_SHORT).show();
            return;
        }

        showLoading(true);
        retrofitService.sortPhones("Bearer " + token, sortBy).enqueue(new Callback<List<ResPhone>>() {
            @Override
            public void onResponse(@NonNull Call<List<ResPhone>> call, @NonNull Response<List<ResPhone>> response) {
                showLoading(false);
                if (response.isSuccessful() && response.body() != null) {
                    updateUI(response.body());
                } else {
                    Toast.makeText(Activity_Sort.this, "Không tìm thấy sản phẩm!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ResPhone>> call, @NonNull Throwable t) {
                showLoading(false);
                Toast.makeText(Activity_Sort.this, "Kết nối thất bại!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showLoading(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    private void updateUI(List<ResPhone> phoneList) {
        if (phoneAdapter == null) {
            phoneAdapter = new Adapter_Phones(phoneList);
            recyclerView.setAdapter(phoneAdapter);
        } else {
            phoneAdapter.updateData(phoneList);
        }
    }

    private String getSavedToken() {
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        return prefs.getString("token", "");
    }
}