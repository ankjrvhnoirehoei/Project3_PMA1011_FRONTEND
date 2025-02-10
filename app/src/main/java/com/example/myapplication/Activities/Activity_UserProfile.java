package com.example.myapplication.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;

public class Activity_UserProfile extends AppCompatActivity {

    private TextView fullName, phoneNumber, address;
    private ImageButton editFullName, editPhoneNumber, editAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // Ánh xạ các view
        LinearLayout backButton = findViewById(R.id.backButton);
        fullName = findViewById(R.id.fullName);
        phoneNumber = findViewById(R.id.phoneNumber);
        address = findViewById(R.id.address);
        editFullName = findViewById(R.id.editFullName);
        editPhoneNumber = findViewById(R.id.editPhoneNumber);
        editAddress = findViewById(R.id.editAddress);

        // Lấy dữ liệu từ SharedPreferences
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        fullName.setText("Họ và Tên: " + prefs.getString("username", "Nguyễn Văn A"));
        phoneNumber.setText("Số điện thoại: " + prefs.getString("phone", "Chưa có số"));
        address.setText("Địa chỉ: " + prefs.getString("address", "Chưa có địa chỉ"));

        // Nút trở lại
        backButton.setOnClickListener(v -> finish());

        // Sự kiện chỉnh sửa
        editFullName.setOnClickListener(v -> showEditDialog("Sửa Họ và Tên", fullName, "username"));
        editPhoneNumber.setOnClickListener(v -> showEditDialog("Sửa Số Điện Thoại", phoneNumber, "phone"));
        editAddress.setOnClickListener(v -> showEditDialog("Sửa Địa Chỉ", address, "address"));
    }

    // Hộp thoại chỉnh sửa thông tin
    private void showEditDialog(String title, TextView textView, String key) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);

        final EditText input = new EditText(this);
        String currentText = textView.getText().toString();
        String valueOnly = currentText.contains(":") ? currentText.split(": ", 2)[1] : currentText;
        input.setText(valueOnly);
        builder.setView(input);

        builder.setPositiveButton("Lưu", (dialog, which) -> {
            String newValue = input.getText().toString();
            textView.setText(title.split(" ")[1] + ": " + newValue);

            // Lưu vào SharedPreferences
            SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(key, newValue);
            editor.apply();

            // Gửi kết quả về Activity_fragment_user
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        });

        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}
