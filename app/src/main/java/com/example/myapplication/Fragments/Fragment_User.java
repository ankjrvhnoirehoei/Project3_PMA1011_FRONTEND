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

import com.example.myapplication.Models.ResUser;
import com.example.myapplication.Others.RetrofitService;
import com.example.myapplication.R;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Fragment_User extends Fragment {
    private LinearLayout logoutButton, profileInfoButton, voucherButton, orderHistoryButton;
    private TextView usernameText, phoneText, addressText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__user, container, false);

        // Ánh xạ UI
        profileInfoButton = view.findViewById(R.id.profileInfo);
        voucherButton = view.findViewById(R.id.yourvoucher);
        logoutButton = view.findViewById(R.id.logout);
        orderHistoryButton = view.findViewById(R.id.yourOrders);
        usernameText = view.findViewById(R.id.username);
        phoneText = view.findViewById(R.id.phoneNumber);
        addressText = view.findViewById(R.id.address);

        // Lấy dữ liệu từ SharedPreferences
        SharedPreferences preferences = requireActivity().getSharedPreferences("AppPrefs", requireActivity().MODE_PRIVATE);
        String token = "Bearer " + preferences.getString("AuthToken", "");
        String userID = preferences.getString("loginUserID", "");

        if (userID == null || userID.trim().isEmpty()) {
            usernameText.setText("Không có dữ liệu người dùng");
            phoneText.setText("-");
            addressText.setText("-");
            return view;
        }

        // Gọi API lấy thông tin người dùng
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);

        retrofitService.getAllUsers(token).enqueue(new Callback<List<ResUser>>() {
            @Override
            public void onResponse(Call<List<ResUser>> call, Response<List<ResUser>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (ResUser user : response.body()) {
                        if (user.getUserID().equalsIgnoreCase(userID.trim())) {
                            usernameText.setText(user.getUsername());
                            phoneText.setText(user.getPhoneNumber());
                            addressText.setText(user.getAddress());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ResUser>> call, Throwable t) {
                Log.e("API Failure", "Lỗi API: " + t.getMessage(), t);
            }
        });

        // Chuyển sang trang UserProfile
        profileInfoButton.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), Activity_UserProfile.class);
            intent.putExtra("username", usernameText.getText().toString());
            intent.putExtra("phoneNumber", phoneText.getText().toString());
            intent.putExtra("address", addressText.getText().toString());
            startActivityForResult(intent, 1);
        });


        orderHistoryButton.setOnClickListener(v -> startActivity(new Intent(requireActivity(), Activity_Order_History.class)));
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == getActivity().RESULT_OK) {
            if (data != null) {
                if (data.hasExtra("username")) {
                    usernameText.setText(data.getStringExtra("username"));
                }
                if (data.hasExtra("phoneNumber")) {
                    phoneText.setText(data.getStringExtra("phoneNumber"));
                }
                if (data.hasExtra("address")) {
                    addressText.setText(data.getStringExtra("address"));
                }
            }
        }
    }
}
