package com.example.myapplication.Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.Adapters.Adapter_Order;
import com.example.myapplication.Models.ResBillDetails;
import com.example.myapplication.R;
import java.util.ArrayList;
import java.util.List;

public class Activity_Order_History extends AppCompatActivity implements Adapter_Order.OnOrderActionListener {
    private RecyclerView recyclerView;
    private Adapter_Order orderAdapter;
    private List<ResBillDetails> orderList;
    private Button btnAll, btnInTransit, btnCanceled, btnReceived;
    private LinearLayout btnBack;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        // Ánh xạ view
        recyclerView = findViewById(R.id.recycler_orders);
        btnAll = findViewById(R.id.btn_all);
        btnInTransit = findViewById(R.id.btn_in_transit);
        btnCanceled = findViewById(R.id.btn_canceled);
        btnReceived = findViewById(R.id.btn_received);
        btnBack = findViewById(R.id.btn_back);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Danh sách đơn hàng (giả lập)
        orderList = new ArrayList<>();
        orderList.add(new ResBillDetails("1", "1001", "P001", "iPhone 15 Pro Max", "Đang vận chuyển", "05/02/2025", 1, 35000000));
        orderList.add(new ResBillDetails("2", "1002", "P002", "Samsung Galaxy S23", "Đã hủy", "02/02/2025", 2, 20000000));
        orderList.add(new ResBillDetails("3", "1003", "P003", "Xiaomi 13 Ultra", "Đã nhận", "01/02/2025", 1, 18000000));

        // Khởi tạo Adapter và gán vào RecyclerView
        orderAdapter = new Adapter_Order(orderList);
        recyclerView.setAdapter(orderAdapter);

        // Xử lý sự kiện nhấn nút lọc
        btnAll.setOnClickListener(v -> filterOrders("Tất cả"));
        btnInTransit.setOnClickListener(v -> filterOrders("Đang vận chuyển"));
        btnCanceled.setOnClickListener(v -> filterOrders("Đã hủy"));
        btnReceived.setOnClickListener(v -> filterOrders("Đã nhận"));

        // Nút quay lại
        btnBack.setOnClickListener(v -> finish());
    }

    private void filterOrders(String status) {
        List<ResBillDetails> filteredList = new ArrayList<>();
        for (ResBillDetails order : orderList) {
            if (status.equals("Tất cả") || order.getStatus().equals(status)) {
                filteredList.add(order);
            }
        }
        orderAdapter.updateList(filteredList);
    }

    // Xử lý hủy đơn hàng
    @Override
    public void onCancelOrder(String billID) {
        for (ResBillDetails order : orderList) {
            if (order.getBillID().equals(billID)) {
                order.setStatus("Đã hủy");
            }
        }
        orderAdapter.updateList(orderList);
    }

    // Xử lý bảo hành
    @Override
    public void onRequestWarranty(String billID) {
        // Thực hiện logic gửi yêu cầu bảo hành
    }
}
