package com.example.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Models.ReqSignup;
import com.example.myapplication.Models.ResSignup;
import com.example.myapplication.R;
import com.example.myapplication.Others.RetrofitService;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_SignUp extends AppCompatActivity {
    private EditText edtUsername, edtEmail, edtPassword, edtConfirmPassword, edtPhone;
    private Button btnSignUp;
    private ProgressBar progressBar;
    private TextView tvLogin;
    private RetrofitService signUpService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Ánh xạ UI
        edtUsername = findViewById(R.id.edtUsername);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        edtPhone = findViewById(R.id.edtPhone);
        btnSignUp = findViewById(R.id.btnSignUp);
        progressBar = findViewById(R.id.progressBar);
        tvLogin = findViewById(R.id.tvLogin);



        // Chuyển sang màn hình đăng nhập
        tvLogin.setOnClickListener(v -> {
            startActivity(new Intent(Activity_SignUp.this, Activity_Login.class));
            finish();
        });

        // Xử lý đăng ký
        btnSignUp.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String username = edtUsername.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String confirmPassword = edtConfirmPassword.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();

        // Kiểm tra dữ liệu nhập vào
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(phone)) {
            showToast("Vui lòng nhập đầy đủ thông tin");
            return;
        }
        if (!password.equals(confirmPassword)) {
            showToast("Mật khẩu không khớp");
            return;
        }
        if (!phone.matches("\\d{10,11}")) {
            showToast("Số điện thoại không hợp lệ");
            return;
        }

        // Hiển thị progress bar
        progressBar.setVisibility(View.VISIBLE);
        btnSignUp.setEnabled(false);

        // Gửi yêu cầu đăng ký
        Call<ResSignup> call = signUpService.signup(new ReqSignup(username, email, password, phone));
        call.enqueue(new Callback<ResSignup>() {
            @Override
            public void onResponse(Call<ResSignup> call, Response<ResSignup> response) {
                progressBar.setVisibility(View.GONE);
                btnSignUp.setEnabled(true);

                if (response.isSuccessful() && response.body() != null) {
                    showToast(response.body().getMessage());
                    startActivity(new Intent(Activity_SignUp.this, Activity_Login.class));
                    finish();
                } else {
                    showToast("Đăng ký thất bại, thử lại sau!");
                }
            }

            @Override
            public void onFailure(Call<ResSignup> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                btnSignUp.setEnabled(true);
                showToast("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(Activity_SignUp.this, message, Toast.LENGTH_SHORT).show();
    }
}
