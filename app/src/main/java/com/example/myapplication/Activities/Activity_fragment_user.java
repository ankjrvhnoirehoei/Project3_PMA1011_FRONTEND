package com.example.myapplication.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;

public class Activity_fragment_user extends AppCompatActivity {

    Button logoutButton, profileInfoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_user);

        // Tham chiếu đến các phần tử trong layout
        logoutButton = findViewById(R.id.logout);
        profileInfoButton = findViewById(R.id.profileInfo); // Đảm bảo ID khớp với XML

        // Sự kiện bấm nút đăng xuất
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        // Sự kiện bấm vào "Thông tin cá nhân"
        profileInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mở Activity_UserProfile khi nhấn vào "Thông tin cá nhân"
                Intent intent = new Intent(Activity_fragment_user.this, Activity_UserProfile.class);
                startActivity(intent);
            }
        });
    }

    private void logout() {
        // Xóa token đã lưu trong SharedPreferences
        SharedPreferences preferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear(); // Xóa toàn bộ dữ liệu (bao gồm token)
        editor.apply();

        // Chuyển hướng về màn hình đăng nhập
        Intent intent = new Intent(Activity_fragment_user.this, Activity_Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK); // Xóa stack của các activity trước đó
        startActivity(intent);

        // Kết thúc Activity hiện tại
        finish();
    }
}
