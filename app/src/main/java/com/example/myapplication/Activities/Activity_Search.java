package com.example.myapplication.Activities;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapters.Adapter_Phones;
import com.example.myapplication.Models.ResPhone;
import com.example.myapplication.Others.RetrofitService;
import com.example.myapplication.R;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_Search extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Adapter_Phones phoneAdapter;
    private RetrofitService retrofitService;
    private ProgressBar progressBar;
    private TextView txtNoResult;
    private static final String TAG = "Activity_Search";
    private Handler searchHandler = new Handler();
    private Runnable searchRunnable;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar = findViewById(R.id.progressBar);
        txtNoResult = findViewById(R.id.txtNoResult);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitService = retrofit.create(RetrofitService.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchPhones(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (searchRunnable != null) {
                    searchHandler.removeCallbacks(searchRunnable);
                }
                searchRunnable = () -> searchPhones(newText);
                searchHandler.postDelayed(searchRunnable, 500); // Giảm tần suất gọi API
                return false;
            }
        });
        return true;
    }

    private void searchPhones(String query) {
        if (query.isEmpty()) {
            recyclerView.setAdapter(null);
            txtNoResult.setVisibility(View.GONE);
            return;
        }

        String token = getSavedToken();
        if (token.isEmpty()) {
            Toast.makeText(this, "Bạn chưa đăng nhập!", Toast.LENGTH_SHORT).show();
            return;
        }
        token = "Bearer " + token;

        progressBar.setVisibility(View.VISIBLE);
        txtNoResult.setVisibility(View.GONE);

        Call<List<ResPhone>> call = retrofitService.searchPhones(token, query);
        call.enqueue(new Callback<List<ResPhone>>() {
            @Override
            public void onResponse(Call<List<ResPhone>> call, Response<List<ResPhone>> response) {
                progressBar.setVisibility(View.GONE);
                Log.d(TAG, "API Response Code: " + response.code());

                if (response.isSuccessful() && response.body() != null) {
                    List<ResPhone> phoneList = response.body();
                    if (phoneList.isEmpty()) {
                        txtNoResult.setVisibility(View.VISIBLE);
                        recyclerView.setAdapter(null);
                    } else {
                        phoneAdapter = new Adapter_Phones(phoneList);
                        recyclerView.setAdapter(phoneAdapter);
                    }
                } else {
                    showApiError(response);
                }
            }

            @Override
            public void onFailure(Call<List<ResPhone>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e(TAG, "Kết nối thất bại: " + t.getMessage());
                Toast.makeText(Activity_Search.this, "Kết nối thất bại: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showApiError(Response<?> response) {
        try {
            String errorBody = response.errorBody().string();
            Log.e(TAG, "Lỗi từ API: " + errorBody);
            Toast.makeText(Activity_Search.this, "Lỗi API: " + errorBody, Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.e(TAG, "Không thể đọc lỗi API", e);
        }
    }

    private String getSavedToken() {
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        return prefs.getString("token", "");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}