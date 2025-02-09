package com.example.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Activity_Voucher extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Adapter_voucher adapter;
    private List<Voucher> voucherList;
    private Spinner spinnerSort;
    private View TextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher); // Ensure this layout exists

        TextView = findViewById(R.id.titleVoucher); // Ensure this ID exists in layout
        spinnerSort = findViewById(R.id.spinnerSort); // Ensure this ID exists in layout

        // Sample data
        voucherList = new ArrayList<>();
        voucherList.add(new Voucher("Voucher Tết", 50000));
        voucherList.add(new Voucher("Giảm giá mùa hè", 30000));
        voucherList.add(new Voucher("Voucher Black Friday", 70000));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter_voucher(voucherList);
        recyclerView.setAdapter(adapter);

        // Set up Spinner
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.sort_options, // Ensure this array exists in res/values/arrays.xml
                android.R.layout.simple_spinner_item
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSort.setAdapter(spinnerAdapter);

        // Handle item selection for sorting
        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    sortByName();
                } else {
                    sortByDiscount();
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Thiết lập sự kiện cho nút "Quay lại"
        Button backButton = findViewById(R.id.btnBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mở lại Activity_fragment_user khi nhấn vào "Quay lại"
                Intent intent = new Intent(Activity_Voucher.this, Activity_fragment_user.class);
                startActivity(intent);
                finish(); // Kết thúc Activity hiện tại
            }
        });
    }

    private void sortByName() {
        Collections.sort(voucherList, Comparator.comparing(Voucher::getName));
    }

    private void sortByDiscount() {
        Collections.sort(voucherList, (v1, v2) -> v2.getDiscountAmount() - v1.getDiscountAmount());
    }

    // Adapter_voucher.java
    public static class Adapter_voucher extends RecyclerView.Adapter<Adapter_voucher.VoucherViewHolder> {
        private List<Voucher> voucherList;

        public Adapter_voucher(List<Voucher> voucherList) {
            this.voucherList = voucherList;
        }

        @NonNull
        @Override
        public VoucherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_2, parent, false);
            return new VoucherViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull VoucherViewHolder holder, int position) {
            Voucher voucher = voucherList.get(position);
            holder.text1.setText(voucher.getName());
            holder.text2.setText("Giảm giá: " + voucher.getDiscountAmount() + " VNĐ");
        }

        @Override
        public int getItemCount() {
            return voucherList.size();
        }

        static class VoucherViewHolder extends RecyclerView.ViewHolder {
            TextView text1, text2;

            public VoucherViewHolder(@NonNull View itemView) {
                super(itemView);
                text1 = itemView.findViewById(android.R.id.text1);
                text2 = itemView.findViewById(android.R.id.text2);
            }
        }
    }

    // Voucher.java
    public static class Voucher {
        private String name;
        private int discountAmount;

        public Voucher(String name, int discountAmount) {
            this.name = name;
            this.discountAmount = discountAmount;
        }

        public String getName() {
            return name;
        }

        public int getDiscountAmount() {
            return discountAmount;
        }
    }
}
