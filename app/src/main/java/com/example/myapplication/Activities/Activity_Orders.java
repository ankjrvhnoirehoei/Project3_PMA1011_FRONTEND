package com.example.myapplication.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Models.ReqLogin;
import com.example.myapplication.Models.ReqSignup;
import com.example.myapplication.Models.ResLogin;
import com.example.myapplication.Models.ResPhone;
import com.example.myapplication.Models.ResSignup;
import com.example.myapplication.Models.ResUser;
import com.example.myapplication.R;
import com.example.myapplication.Others.RetrofitService;
import com.example.myapplication.Models.ResBillsFull;
import com.example.myapplication.Models.ResBillDetails;
import com.example.myapplication.Models.ResBill;
import com.example.myapplication.Adapters.Adapter_Order;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Activity_Orders extends AppCompatActivity {
    private RecyclerView rvOrders;
    private TextView tvVoucherInfo;
    private Adapter_Order ordersAdapter;
    private List<ResBillDetails> orderList = new ArrayList<>();
    private String authToken, userID;
    private RetrofitService apiService = new RetrofitService() {
        @Override
        public Call<ResLogin> Login(ReqLogin reqLogin) {
            return null;
        }

        @Override
        public Call<ResSignup> signup(ReqSignup reqSignup) {
            return null;
        }

        @Override
        public Call<List<ResPhone>> getAllPhones(String token) {
            return null;
        }

        @Override
        public Call<List<ResUser>> getAllUsers(String token) {
            return null;
        }

        @Override
        public Call<List<ResUser>> findUsers(String authToken, String searchUsers) {
            return null;
        }

        @Override
        public Call<List<ResUser>> sortUsers(String token, String sortBy) {
            return null;
        }

        @Override
        public Call<ResUser> editUserInfo(String token, Map<String, RequestBody> partMap, MultipartBody.Part avatarImg) {
            return null;
        }

        @Override
        public Call<ResBillsFull> getUserBills(String authToken, String userID) {
            return null;
        }

        @Override
        public Call<List<ResBill>> getAllBills(String token) {
            return null;
        }

        @Override
        public Call<List<ResBillDetails>> getBillDetails(String authToken, String billID) {
            return null;
        }

        @Override
        public Call<List<ResPhone>> searchPhones(String authToken, String searchQuery) {
            return null;
        }
    }; // ✅ Không dùng getInstance()

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        // ✅ Lấy token & userID từ SharedPreferences
        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        authToken = prefs.getString("authToken", "");
        userID = prefs.getString("userID", "");

        tvVoucherInfo = findViewById(R.id.tvVoucherInfo);
        rvOrders = findViewById(R.id.rvOrders);
        rvOrders.setLayoutManager(new LinearLayoutManager(this));
        rvOrders.setAdapter(ordersAdapter = new Adapter_Order(orderList));

        if (!authToken.isEmpty() && !userID.isEmpty()) loadOrders();
        else tvVoucherInfo.setText("Bạn cần đăng nhập!");
    }

    private void loadOrders() {
        apiService.getUserBills(authToken, userID).enqueue(new Callback<ResBillsFull>() {
            @Override
            public void onResponse(Call<ResBillsFull> call, Response<ResBillsFull> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ResBill> bills = response.body().getBills(); // ✅ Đúng kiểu dữ liệu
                    if (!bills.isEmpty()) fetchDetails(bills);
                    else tvVoucherInfo.setText("Không có đơn hàng!");
                } else tvVoucherInfo.setText("Lỗi tải đơn hàng!");
            }

            @Override public void onFailure(Call<ResBillsFull> call, Throwable t) {
                tvVoucherInfo.setText("Lỗi kết nối!");
            }
        });
    }

    private void fetchDetails(List<ResBill> bills) {
        for (ResBill bill : bills) {
            apiService.getBillDetails(authToken, bill.getBillID()).enqueue(new Callback<List<ResBillDetails>>() {
                @Override
                public void onResponse(Call<List<ResBillDetails>> call, Response<List<ResBillDetails>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        orderList.addAll(response.body()); // ✅ Đúng kiểu dữ liệu
                        ordersAdapter.notifyDataSetChanged();
                        updateVoucherInfo();
                    }
                }

                @Override public void onFailure(Call<List<ResBillDetails>> call, Throwable t) {}
            });
        }
    }

    private void updateVoucherInfo() {
        int totalMoney = 0, totalProducts = 0;
        for (ResBillDetails order : orderList) {
            totalMoney += order.getTotalPrice();
            totalProducts += order.getQuantity();
        }

        int moneyNeeded = 5000000 - (totalMoney % 5000000);
        int productsNeeded = 5 - (totalProducts % 5);

        tvVoucherInfo.setText("Mua thêm " + moneyNeeded + "đ hoặc " +
                productsNeeded + " sản phẩm để nhận voucher!");
    }
}
