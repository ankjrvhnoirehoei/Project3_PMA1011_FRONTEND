package com.example.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;

public class Activity_Current extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current); // Đảm bảo layout đúng

        // Khai báo và gán viewVouchers từ layout
        Button viewVouchers = findViewById(R.id.viewVouchers); // Đảm bảo ID này tồn tại trong XML

        // Thiết lập sự kiện khi nhấn vào viewVouchers
        viewVouchers.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_Current.this, Activity_Voucher.class);
            startActivity(intent);
        });
    }
}
