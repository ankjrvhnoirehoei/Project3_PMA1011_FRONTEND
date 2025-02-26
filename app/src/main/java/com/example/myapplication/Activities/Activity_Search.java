package com.example.myapplication.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
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

import java.util.ArrayList;
import java.util.Collections;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setSupportActionBar(findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        phoneAdapter = new Adapter_Phones(new ArrayList<>());
        recyclerView.setAdapter(phoneAdapter);

        progressBar = findViewById(R.id.progressBar);
        txtNoResult = findViewById(R.id.txtNoResult);

        retrofitService = new Retrofit.Builder()
                .baseUrl(RetrofitService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RetrofitService.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.trim().isEmpty()) searchPhones(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.trim().isEmpty()) clearResults();
                return true;
            }
        });
        return true;
    }

    private void searchPhones(String query) {
        showLoading(true);

        retrofitService.searchPhones("Bearer " + getToken(), query).enqueue(new Callback<List<ResPhone>>() {
            @Override
            public void onResponse(@NonNull Call<List<ResPhone>> call, @NonNull Response<List<ResPhone>> response) {
                showLoading(false);
                List<ResPhone> phones = response.body();
                if (response.isSuccessful() && phones != null && !phones.isEmpty()) {
                    phoneAdapter.updateData(phones);
                    txtNoResult.setVisibility(View.GONE);
                } else {
                    clearResults();
                    Toast.makeText(Activity_Search.this, "Không tìm thấy sản phẩm!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ResPhone>> call, @NonNull Throwable t) {
                showLoading(false);
                Toast.makeText(Activity_Search.this, "Lỗi kết nối!", Toast.LENGTH_SHORT).show();
                Log.e("API Error", "Lỗi: " + t.getMessage());
            }
        });
    }

    private void clearResults() {
        phoneAdapter.updateData(Collections.emptyList());
        txtNoResult.setVisibility(View.VISIBLE);
    }

    private void showLoading(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    private String getToken() {
        return getSharedPreferences("MyPrefs", MODE_PRIVATE).getString("token", "");
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
