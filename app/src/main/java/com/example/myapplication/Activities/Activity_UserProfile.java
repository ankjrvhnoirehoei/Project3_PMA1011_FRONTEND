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

        LinearLayout backButton = findViewById(R.id.backButton);
        fullName = findViewById(R.id.fullName);
        phoneNumber = findViewById(R.id.phoneNumber);
        address = findViewById(R.id.address);
        editFullName = findViewById(R.id.editFullName);
        editPhoneNumber = findViewById(R.id.editPhoneNumber);
        editAddress = findViewById(R.id.editAddress);

        // Nhận dữ liệu từ Fragment_User
        Intent intent = getIntent();
        fullName.setText(intent.getStringExtra("username"));
        phoneNumber.setText(intent.getStringExtra("phoneNumber"));
        address.setText(intent.getStringExtra("address"));

        backButton.setOnClickListener(v -> finish());

        editFullName.setOnClickListener(v -> showEditDialog("Sửa Họ và Tên", fullName, "username"));
        editPhoneNumber.setOnClickListener(v -> showEditDialog("Sửa Số Điện Thoại", phoneNumber, "phoneNumber"));
        editAddress.setOnClickListener(v -> showEditDialog("Sửa Địa Chỉ", address, "address"));
    }

    private void showEditDialog(String title, TextView textView, String key) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);

        final EditText input = new EditText(this);
        input.setText(textView.getText().toString());
        builder.setView(input);

        builder.setPositiveButton("Lưu", (dialog, which) -> {
            String newValue = input.getText().toString();
            textView.setText(newValue);

            SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(key, newValue);
            editor.apply();

            Intent intent = new Intent();
            intent.putExtra(key, newValue);
            setResult(RESULT_OK, intent);
            finish();
        });

        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.cancel());
        builder.show();
    }
}
