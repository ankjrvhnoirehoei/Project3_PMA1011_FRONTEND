package com.example.myapplication.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapters.Activity_Product_Detail;
import com.example.myapplication.Models.ResPhone;
import com.example.myapplication.R;
import java.util.ArrayList;
import java.util.List;

public class Activity_Search extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Activity_ProductAdapter productAdapter;
    private List<ResPhone> productList;
    private SearchView searchView;
    private Spinner filterSpinner;
    private List<ResPhone> filteredProductList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);
        filterSpinner = findViewById(R.id.filterSpinner);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productList = new ArrayList<>(); // Initialize with your data
        filteredProductList = new ArrayList<>(productList);
        productAdapter = new Activity_ProductAdapter(this, filteredProductList, this::onProductClick);
        recyclerView.setAdapter(productAdapter);

        // Set up search view listener
        setupSearchView();

        // Set up spinner with filter options
        setupFilterSpinner();

        // Tạo dữ liệu giả (bạn cần thay bằng dữ liệu thực)
        loadDummyData();
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterProducts(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterProducts(newText);
                return false;
            }
        });
    }

    private void setupFilterSpinner() {
        // Tạo danh sách bộ lọc
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.filter_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(adapter);

        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                applyFilter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });
    }

    private void loadDummyData() {
        // Thêm dữ liệu giả vào productList (tải dữ liệu thực vào đây)
        // productList.add(new ResPhone(...));
        productAdapter.notifyDataSetChanged();
    }

    private void filterProducts(String query) {
        filteredProductList.clear();
        for (ResPhone product : productList) {
            if (product.getPhoneName().toLowerCase().contains(query.toLowerCase())) {
                filteredProductList.add(product);
            }
        }
        productAdapter.notifyDataSetChanged();
    }

    private void applyFilter() {
        // Áp dụng bộ lọc theo lựa chọn của người dùng (ví dụ theo giá, theo thương hiệu, ...)
        String selectedFilter = filterSpinner.getSelectedItem().toString();
        if (selectedFilter.equals("Price: Low to High")) {
            // Sắp xếp theo giá
            filteredProductList.sort((p1, p2) -> Double.compare(p1.getPhonePrice(), p2.getPhonePrice()));
        } else if (selectedFilter.equals("Price: High to Low")) {
            // Sắp xếp theo giá ngược lại
            filteredProductList.sort((p1, p2) -> Double.compare(p2.getPhonePrice(), p1.getPhonePrice()));
        }
        productAdapter.notifyDataSetChanged();
    }

    private void onProductClick(ResPhone product) {
        Intent intent = new Intent(Activity_Search.this, Activity_Product_Detail.class);
        intent.putExtra("product_id", product.getPhoneID());
        startActivity(intent);
    }
}
