package com.example.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Activity_Voucher extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Spinner spinnerSort;
    private List<Voucher> voucherList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher);

        // Initialize views
        recyclerView = findViewById(R.id.recycler_voucher);
        spinnerSort = findViewById(R.id.spinner_voucher);

        // Sample data
        voucherList = new ArrayList<>();
        voucherList.add(new Voucher("Voucher Tết", 50000));
        voucherList.add(new Voucher("Giảm giá mùa hè", 30000));
        voucherList.add(new Voucher("Voucher Black Friday", 70000));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.voucher_item, parent, false);  // Use custom item layout
                return new VoucherViewHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                Voucher voucher = voucherList.get(position);
                VoucherViewHolder viewHolder = (VoucherViewHolder) holder;
                viewHolder.serialTextView.setText(String.valueOf(position + 1));  // Serial number
                viewHolder.voucherInfoTextView.setText(voucher.getName());
                viewHolder.voucherDiscountTextView.setText("Giảm giá: " + voucher.getDiscountAmount() + " VNĐ");
            }

            @Override
            public int getItemCount() {
                return voucherList.size();
            }

            class VoucherViewHolder extends RecyclerView.ViewHolder {
                TextView serialTextView, voucherInfoTextView, voucherDiscountTextView;

                public VoucherViewHolder(@NonNull View itemView) {
                    super(itemView);
                    serialTextView = itemView.findViewById(R.id.txtSerial);
                    voucherInfoTextView = itemView.findViewById(R.id.txtVoucherInfo);
                    voucherDiscountTextView = itemView.findViewById(R.id.txtVoucherDiscount);
                }
            }
        });

        // Set up Spinner
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.sort_options_price,
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
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Set up Back button

    }

    private void sortByName() {
        Collections.sort(voucherList, Comparator.comparing(Voucher::getName));
    }

    private void sortByDiscount() {
        Collections.sort(voucherList, (v1, v2) -> v2.getDiscountAmount() - v1.getDiscountAmount());
    }

    // Voucher model
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