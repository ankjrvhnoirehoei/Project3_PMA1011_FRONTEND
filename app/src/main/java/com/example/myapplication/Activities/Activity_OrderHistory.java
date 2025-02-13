package com.example.myapplication.Activities;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.example.myapplication.Adapters.OrderPagerAdapter;
import com.example.myapplication.Models.Order;
import com.example.myapplication.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import java.util.ArrayList;
import java.util.List;

public class Activity_OrderHistory extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private TextView tvVoucherInfo;
    private OrderPagerAdapter adapter;
    private List<Order> allOrders, shippingOrders, canceledOrders, receivedOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        initViews();
        loadSampleOrders();
        setupViewPager();
        calculateVoucherInfo();
    }

    private void initViews() {
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        tvVoucherInfo = findViewById(R.id.tvVoucherInfo);
    }

    private void setupViewPager() {
        List<List<Order>> categorizedOrders = new ArrayList<>();
        categorizedOrders.add(allOrders);
        categorizedOrders.add(shippingOrders);
        categorizedOrders.add(canceledOrders);
        categorizedOrders.add(receivedOrders);

        adapter = new OrderPagerAdapter(this, categorizedOrders);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0: tab.setText("Tất cả"); break;
                case 1: tab.setText("Đang vận chuyển"); break;
                case 2: tab.setText("Đã hủy"); break;
                case 3: tab.setText("Đã nhận"); break;
            }
        }).attach();
    }

    private void loadSampleOrders() {
        allOrders = new ArrayList<>();
        shippingOrders = new ArrayList<>();
        canceledOrders = new ArrayList<>();
        receivedOrders = new ArrayList<>();

        allOrders.add(new Order("Đơn hàng 1", 1500000, "Đã nhận"));
        allOrders.add(new Order("Đơn hàng 2", 2000000, "Đang vận chuyển"));
        allOrders.add(new Order("Đơn hàng 3", 1700000, "Đã hủy"));
        allOrders.add(new Order("Đơn hàng 4", 500000, "Đã nhận"));

        for (Order order : allOrders) {
            switch (order.getStatus()) {
                case "Đang vận chuyển": shippingOrders.add(order); break;
                case "Đã hủy": canceledOrders.add(order); break;
                case "Đã nhận": receivedOrders.add(order); break;
            }
        }
    }

    private void calculateVoucherInfo() {
        if (allOrders.isEmpty()) {
            tvVoucherInfo.setText("Bạn chưa có đơn hàng nào.");
            return;
        }

        int totalAmountSpent = 0;
        int totalProductsBought = 0;

        for (Order order : allOrders) {
            if (!order.getStatus().equals("Đã hủy")) {
                totalAmountSpent += order.getPrice();
                totalProductsBought++;
            }
        }

        int neededAmount = 5000000 - (totalAmountSpent % 5000000);
        int neededProducts = 5 - (totalProductsBought % 5);

        if (neededAmount == 5000000) neededAmount = 0;
        if (neededProducts == 5) neededProducts = 0;

        tvVoucherInfo.setText(String.format(
                "Bạn cần mua thêm %d sản phẩm hoặc %dđ để nhận voucher mới",
                neededProducts, neededAmount));
    }
}
