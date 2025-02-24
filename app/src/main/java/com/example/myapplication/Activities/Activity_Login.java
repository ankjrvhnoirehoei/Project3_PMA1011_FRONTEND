package com.example.myapplication.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Models.ReqLogin;
import com.example.myapplication.Models.ResLogin;
import com.example.myapplication.Others.RetrofitService;
import com.example.myapplication.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_Login extends AppCompatActivity {
    EditText txtUsername, txtPassword;
    TextView btnSignup;
    Button btnLogin;
    RetrofitService retrofitService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Ánh xạ các view
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignUp); // Đảm bảo ID đúng

        // Cấu hình Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitService = retrofit.create(RetrofitService.class);

        // Sự kiện khi nhấn nút "Đăng ký"
        btnSignup.setOnClickListener(v -> {
            Log.d("Activity_Login", "Nút Sign Up được bấm"); // Kiểm tra log
            Intent intent = new Intent(Activity_Login.this, Activity_SignUp.class);
            startActivity(intent);
        });

        // Xử lý sự kiện đăng nhập
        btnLogin.setOnClickListener(v -> {
            String username = txtUsername.getText().toString().trim();
            String password = txtPassword.getText().toString().trim();
            if (validateInputs(username, password)) {
                login(username, password);
            }
        });
    }

    private boolean validateInputs(String username, String password) {
        if (TextUtils.isEmpty(username)) {
            txtUsername.setError("Username is required");
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            txtPassword.setError("Password is required");
            return false;
        }
        return true;
    }

    private void login(String username, String password) {
        ReqLogin reqLogin = new ReqLogin(username, password);
        Call<ResLogin> call = retrofitService.Login(reqLogin);

        call.enqueue(new Callback<ResLogin>() {
            @Override
            public void onResponse(Call<ResLogin> call, Response<ResLogin> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    Toast.makeText(Activity_Login.this, "Invalid credentials. Please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }

                ResLogin resLogin = response.body();
                if ("true".equals(resLogin.getStatus())) {
                    saveTokens(resLogin.getToken(), resLogin.getRefreshToken());
                    handleLoginSuccess(username);
                } else {
                    handleLoginError(resLogin.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResLogin> call, Throwable t) {
                Toast.makeText(Activity_Login.this, "Login failed: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("API ERROR: ", t.getMessage());
            }
        });
    }

    private void saveTokens(String token, String refreshToken) {
        SharedPreferences preferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("AuthToken", token);
        editor.putString("RefreshToken", refreshToken);
        editor.apply();
    }

    private void handleLoginSuccess(String username) {
        Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Activity_Login.this, Main.class));
        finish();
    }

    private void handleLoginError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
