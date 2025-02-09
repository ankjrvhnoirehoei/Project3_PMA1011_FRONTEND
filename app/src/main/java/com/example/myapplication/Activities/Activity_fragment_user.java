package com.example.myapplication.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;

public class Activity_fragment_user extends AppCompatActivity {

<<<<<<< Updated upstream
    LinearLayout logoutButton, profileInfoButton, voucherButton, statisticsButton, settingsButton;
    private TextView usernameText; // TextView hiển thị tên
=======
    Button logoutButton, profileInfoButton, yourVideosButton, statisticsButton, settingsButton;
>>>>>>> Stashed changes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_user);

        // Ánh xạ các phần tử
        profileInfoButton = findViewById(R.id.profileInfo);
        voucherButton = findViewById(R.id.yourvoucher); // Đã sửa ID thành yourvoucher
        statisticsButton = findViewById(R.id.statistics);
        settingsButton = findViewById(R.id.settings);
        logoutButton = findViewById(R.id.logout);
<<<<<<< Updated upstream
        usernameText = findViewById(R.id.username); // Đúng ID của TextView hiển thị tên
=======
        profileInfoButton = findViewById(R.id.profileInfo); // Đảm bảo ID khớp với XML
        yourVideosButton = findViewById(R.id.yourVideos); // Thêm dòng này
        statisticsButton = findViewById(R.id.statistics); // Thêm dòng này
        settingsButton = findViewById(R.id.settings); // Thêm dòng này
>>>>>>> Stashed changes

        // Lấy tên từ SharedPreferences
        SharedPreferences preferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        String fullName = preferences.getString("fullName", "Người dùng");
        usernameText.setText(fullName);

        // Xử lý sự kiện khi nhấn vào "Thông tin cá nhân"
        profileInfoButton.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_fragment_user.this, Activity_UserProfile.class);
            startActivityForResult(intent, 1);
        });

        // Xử lý sự kiện khi nhấn vào "Voucher của bạn"
        voucherButton.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_fragment_user.this, Activity_voucher.class);
            startActivity(intent);
        });

<<<<<<< Updated upstream
        // Xử lý sự kiện đăng xuất
        logoutButton.setOnClickListener(v -> logout());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Cập nhật lại tên sau khi sửa trong UserProfile
            SharedPreferences preferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
            String updatedName = preferences.getString("fullName", "Người dùng");
            usernameText.setText(updatedName);
        }
=======
        // Sự kiện bấm vào "Voucher của bạn"
        yourVideosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mở Activity_Voucher khi nhấn vào "Voucher của bạn"
                Intent intent = new Intent(Activity_fragment_user.this, Activity_Voucher.class);
                startActivity(intent);
            }
        });

        // Các sự kiện khác như Thống kê, Cài đặt (Nếu có)
        statisticsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mở Activity_Statistics (hoặc hoạt động tương ứng)
                Intent intent = new Intent(Activity_fragment_user.this, Activity_fragment_user.class);
                startActivity(intent);
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mở Activity_Settings (hoặc hoạt động tương ứng)
                Intent intent = new Intent(Activity_fragment_user.this, Activity_fragment_user.class);
                startActivity(intent);
            }
        });
>>>>>>> Stashed changes
    }

    private void logout() {
        SharedPreferences preferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(Activity_fragment_user.this, Activity_Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
