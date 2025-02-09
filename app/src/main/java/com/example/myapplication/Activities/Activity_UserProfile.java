package com.example.myapplication.Activities;

import android.content.DialogInterface;
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
        setContentView(R.layout.fragment__user);

        // Ánh xạ các view
        LinearLayout backButton = findViewById(R.id.backButton); // Sửa từ Button -> LinearLayout
        fullName = findViewById(R.id.fullName);
        phoneNumber = findViewById(R.id.phoneNumber);
        address = findViewById(R.id.address);
        editFullName = findViewById(R.id.editFullName);
        editPhoneNumber = findViewById(R.id.editPhoneNumber);
        editAddress = findViewById(R.id.editAddress);

        // Lấy dữ liệu từ SharedPreferences
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        fullName.setText("Họ và Tên: " + prefs.getString("username", "Chưa có tên"));

        // Nút trở lại
        backButton.setOnClickListener(v -> finish());

        // Sự kiện chỉnh sửa
        editFullName.setOnClickListener(v -> showEditDialog("Sửa Họ và Tên", fullName));
    }

    // Hộp thoại chỉnh sửa thông tin
    private void showEditDialog(String title, TextView textView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);

        final EditText input = new EditText(this);
        String currentText = textView.getText().toString();
        String valueOnly = currentText.contains(":") ? currentText.split(": ", 2)[1] : currentText;
        input.setText(valueOnly);
        builder.setView(input);

        builder.setPositiveButton("Lưu", (dialog, which) -> {
            String newName = input.getText().toString();
            textView.setText("Họ và Tên: " + newName);

            // Lưu vào SharedPreferences
            SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("username", newName);
            editor.apply();

            // Gửi kết quả về Activity_fragment_user
            Intent intent = new Intent();
            intent.putExtra("updatedName", newName);
            setResult(RESULT_OK, intent);
            finish();
        });

        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}
