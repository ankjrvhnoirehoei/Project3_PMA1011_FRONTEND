package com.example.myapplication.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.myapplication.Activities.Activity_Login;
import com.example.myapplication.Activities.Activity_Order_History;
import com.example.myapplication.Activities.Activity_UserProfile;
import com.example.myapplication.Activities.Activity_Voucher;
import com.example.myapplication.R;

public class Fragment_User extends Fragment {
    private LinearLayout logoutButton, profileInfoButton, voucherButton, statisticsButton, settingsButton, orderHistoryButton;
    private TextView usernameText, phoneText, addressText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__user, container, false);

        // Ánh xạ các phần tử
        profileInfoButton = view.findViewById(R.id.profileInfo);
        voucherButton = view.findViewById(R.id.yourvoucher);
        logoutButton = view.findViewById(R.id.logout);
        orderHistoryButton = view.findViewById(R.id.yourOrders); // Thêm ánh xạ nút đơn hàng

        usernameText = view.findViewById(R.id.username);
        phoneText = view.findViewById(R.id.phoneNumber);
        addressText = view.findViewById(R.id.address);

        // Kiểm tra null để tránh lỗi
        if (statisticsButton == null) {
            Log.e("Fragment_User", "statisticsButton bị null! Kiểm tra R.id.statistics trong XML.");
        }

        // Lấy dữ liệu từ SharedPreferences
        SharedPreferences preferences = requireActivity().getSharedPreferences("UserPrefs", requireActivity().MODE_PRIVATE);
        String fullName = preferences.getString("username", "Nguyễn Văn A");
        String phoneNumber = preferences.getString("phoneNumber", "Chưa có số điện thoại");
        String address = preferences.getString("address", "Chưa có địa chỉ");

        // Cập nhật giao diện
        if (usernameText != null) usernameText.setText(fullName);
        if (phoneText != null) phoneText.setText(phoneNumber);
        if (addressText != null) addressText.setText(address);

        profileInfoButton.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), Activity_UserProfile.class);
            startActivity(intent);
        });

        voucherButton.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), Activity_Voucher.class);
            startActivity(intent);
        });

        // Sửa lỗi statisticsButton bị null
        if (statisticsButton != null) {
            statisticsButton.setOnClickListener(v -> {
                Intent intent = new Intent(requireActivity(), Activity_Order_History.class);
                startActivity(intent);
            });
        }

        // Xử lý sự kiện click cho "Đơn hàng của bạn"
        if (orderHistoryButton != null) {
            orderHistoryButton.setOnClickListener(v -> {
                Intent intent = new Intent(requireActivity(), Activity_Order_History.class);
                startActivity(intent);
            });
        }

        // Xử lý sự kiện đăng xuất
        logoutButton.setOnClickListener(v -> logout());

        return view;
    }

    private void logout() {
        SharedPreferences preferences = requireActivity().getSharedPreferences("UserPrefs", requireActivity().MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(requireActivity(), Activity_Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        requireActivity().finish();
    }
}
