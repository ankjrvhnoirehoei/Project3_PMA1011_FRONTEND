package com.example.myapplication.Fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.myapplication.Activities.Activity_Login;
import com.example.myapplication.Activities.Activity_OrderHistory;


import com.example.myapplication.R;

public class Fragment_User extends Fragment {

    public Fragment_User() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__user, container, false);

        // Ánh xạ view
        ImageView userImage = view.findViewById(R.id.user_image);
        TextView userName = view.findViewById(R.id.user_name);
        Button btnEditInfo = view.findViewById(R.id.edit_user_info);
        Button btnViewOrders = view.findViewById(R.id.view_purchased_products);
        Button btnViewVouchers = view.findViewById(R.id.view_vouchers);
        Button btnContactInfo = view.findViewById(R.id.contact_info);
        Button btnLogout = view.findViewById(R.id.logout);

        // Hiển thị tên người dùng (nếu không cần lấy từ session thì dùng chuỗi cố định)
        userName.setText("Người dùng mẫu");

        // Sự kiện nút "Chỉnh sửa thông tin"


        // Sự kiện nút "Sản phẩm đã mua"
        btnViewOrders.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Activity_OrderHistory.class);
            startActivity(intent);
        });

        // Sự kiện nút "Xem Voucher"
        btnViewVouchers.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), VoucherListActivity.class);
            startActivity(intent);
        });

        // Sự kiện nút "Thông tin liên hệ"


        // Sự kiện nút "Đăng xuất"
        btnLogout.setOnClickListener(v -> {
            // Hiển thị thông báo đăng xuất
            Toast.makeText(getActivity(), "Đăng xuất thành công!", Toast.LENGTH_SHORT).show();

            // Điều hướng về màn hình đăng nhập và xóa stack
            Intent intent = new Intent(getActivity(), Activity_Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        return view;
    }
}
