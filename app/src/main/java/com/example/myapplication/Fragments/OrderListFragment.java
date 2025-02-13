package com.example.myapplication.Fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.myapplication.Adapters.OrderAdapter;
import com.example.myapplication.Models.Order;
import com.example.myapplication.R;
import java.util.ArrayList;
import java.util.List;

public class OrderListFragment extends Fragment {
    private List<Order> orderList;

    public static OrderListFragment newInstance(List<Order> orders) {
        OrderListFragment fragment = new OrderListFragment();
        Bundle args = new Bundle();
        args.putSerializable("orders", (ArrayList<Order>) orders); // Dùng ArrayList để tránh lỗi
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            orderList = (List<Order>) getArguments().getSerializable("orders");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new OrderAdapter(orderList)); // Sửa thành OrderAdapter
        return view;
    }
}
