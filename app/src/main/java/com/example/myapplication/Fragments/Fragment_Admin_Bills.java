package com.example.myapplication.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Models.ResBill;
import com.example.myapplication.R;

import java.util.ArrayList;

public class Fragment_Admin_Bills extends Fragment {
    RecyclerView rvBillsManagement;
    ArrayList<ResBill> billsList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment__admin__bills, container, false);
        rvBillsManagement = v.findViewById(R.id.rvBillsManagement);
        rvBillsManagement.setLayoutManager(new LinearLayoutManager(getContext()));
        return v;
    }
}