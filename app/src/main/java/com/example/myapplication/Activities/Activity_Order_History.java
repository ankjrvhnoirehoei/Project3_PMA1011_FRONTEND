package com.example.myapplication.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.Adapters.Adapter_Order;
import com.example.myapplication.Models.Order;
import com.example.myapplication.R;
import java.util.ArrayList;
import java.util.List;

public class Activity_Order_History extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Adapter_Order orderAdapter;
    private List<Order> orderList;
    private Button btnAll, btnInTransit, btnCanceled, btnReceived;
    private LinearLayout btnBack; // Thêm biến cho nút quay lại

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        // Ánh xạ RecyclerView và các nút lọc đơn hàng
        recyclerView = findViewById(R.id.recycler_orders);
        btnAll = findViewById(R.id.btn_all);
        btnInTransit = findViewById(R.id.btn_in_transit);
        btnCanceled = findViewById(R.id.btn_canceled);
        btnReceived = findViewById(R.id.btn_received);
        btnBack = findViewById(R.id.btn_back); // Ánh xạ nút Quay lại

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Dummy data mô phỏng danh sách đơn hàng
        orderList = new ArrayList<>();
        orderList.add(new Order("iPhone 15 Pro Max", "Đang vận chuyển", "05/02/2025", 1));
        orderList.add(new Order("Samsung Galaxy S23", "Đã hủy", "02/02/2025", 2));
        orderList.add(new Order("Xiaomi 13 Ultra", "Đã nhận", "01/02/2025", 1));

        // Khởi tạo Adapter và gán vào RecyclerView
        orderAdapter = new Adapter_Order(orderList);
        recyclerView.setAdapter(orderAdapter);

        // Xử lý khi nhấn các nút lọc
        btnAll.setOnClickListener(v -> filterOrders("Tất cả"));
        btnInTransit.setOnClickListener(v -> filterOrders("Đang vận chuyển"));
        btnCanceled.setOnClickListener(v -> filterOrders("Đã hủy"));
        btnReceived.setOnClickListener(v -> filterOrders("Đã nhận"));

        // Xử lý khi nhấn nút Quay lại
        btnBack.setOnClickListener(v -> finish()); // Đóng Activity và quay lại Fragment trước
    }

    private void filterOrders(String status) {
        List<Order> filteredList = new ArrayList<>();
        for (Order order : orderList) {
            if (status.equals("Tất cả") || order.getStatus().equals(status)) {
                filteredList.add(order);
            }
        }
        orderAdapter.updateList(filteredList);
    }
}
