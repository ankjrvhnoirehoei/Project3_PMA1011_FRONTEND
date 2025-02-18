package com.example.myapplication.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Models.ReqSignup;
import com.example.myapplication.Models.ResSignup;
import com.example.myapplication.Others.RetrofitService;
import com.example.myapplication.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_SignUp extends AppCompatActivity {

    private EditText editUsername, editPassword, editAddress, editPhoneNumber;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Khởi tạo các view
        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        editAddress = findViewById(R.id.editAddress);
        editPhoneNumber = findViewById(R.id.editPhoneNumber);
        btnSignUp = findViewById(R.id.btnSignUp);

        // Xử lý sự kiện nhấn nút Đăng ký
        btnSignUp.setOnClickListener(v -> {
            String username = editUsername.getText().toString();
            String password = editPassword.getText().toString();
            String address = editAddress.getText().toString();
            String phoneNumber = editPhoneNumber.getText().toString();

            if (username.isEmpty() || password.isEmpty() || address.isEmpty() || phoneNumber.isEmpty()) {
                Toast.makeText(Activity_SignUp.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            ReqSignup reqSignup = new ReqSignup(username, password, address, phoneNumber);
            signup(reqSignup);
        });
    }

    private void signup(ReqSignup reqSignup) {
        // Khởi tạo Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitService.BASE_URL)  // Đặt URL gốc của API
                .addConverterFactory(GsonConverterFactory.create())  // Chuyển đổi JSON thành đối tượng
                .build();

        // Tạo RetrofitService từ Retrofit
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);

        // Gọi API thông qua RetrofitService
        retrofitService.signup(reqSignup).enqueue(new Callback<ResSignup>() {
            @Override
            public void onResponse(Call<ResSignup> call, Response<ResSignup> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getStatus()) {
                    Toast.makeText(Activity_SignUp.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    // Có thể chuyển sang màn hình chính sau khi đăng ký thành công
                } else {
                    Toast.makeText(Activity_SignUp.this, "Đăng ký thất bại: " + (response.body() != null ? response.body().getMessage() : "Lỗi không xác định"), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResSignup> call, Throwable t) {
                Toast.makeText(Activity_SignUp.this, "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
