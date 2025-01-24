package com.example.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;

public class Activity_UserProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Button backButton = findViewById(R.id.backButton);

        // Thiết lập sự kiện cho nút trở lại
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mở lại Activity_fragment_user
                Intent intent = new Intent(Activity_UserProfile.this, Activity_fragment_user.class);
                startActivity(intent);
                finish(); // Kết thúc Activity hiện tại
            }
        });
    }
}
