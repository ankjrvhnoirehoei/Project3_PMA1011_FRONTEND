package com.example.myapplication.Activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapters.Adapter_Bill_Details;
import com.example.myapplication.Adapters.Adapter_User_All_Bills;
import com.example.myapplication.Models.ResBill;
import com.example.myapplication.Models.ResBillDetails;
import com.example.myapplication.R;

import java.util.ArrayList;

public class Activity_Admin_All_BillDetails extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Adapter_Bill_Details adapterBills;
    private ArrayList<ResBillDetails> billsList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_all_bill_details);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get the list of bills passed from the previous activity
        billsList = (ArrayList<ResBillDetails>) getIntent().getSerializableExtra("billDetails");

        // Set up the adapter and attach it to the RecyclerView
        adapterBills = new Adapter_Bill_Details(this, billsList);
        recyclerView.setAdapter(adapterBills);
    }
}