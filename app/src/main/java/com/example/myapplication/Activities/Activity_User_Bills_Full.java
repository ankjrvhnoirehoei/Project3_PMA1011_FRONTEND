package com.example.myapplication.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapters.Adapter_User_All_Bills;
import com.example.myapplication.Models.ResBill;
import com.example.myapplication.R;

import java.util.ArrayList;

public class Activity_User_Bills_Full extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Adapter_User_All_Bills adapterBills;
    private ArrayList<ResBill> billsList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_bills_full);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get the list of bills passed from the previous activity
        billsList = (ArrayList<ResBill>) getIntent().getSerializableExtra("bills");

        // Set up the adapter and attach it to the RecyclerView
        adapterBills = new Adapter_User_All_Bills(this, billsList);
        recyclerView.setAdapter(adapterBills);
    }
}