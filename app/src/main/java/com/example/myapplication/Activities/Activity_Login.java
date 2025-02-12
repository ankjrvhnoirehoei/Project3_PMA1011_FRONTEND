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

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
    TextView btnLogin;
    RetrofitService retrofitService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtPassword = findViewById(R.id.txtPassword);
        txtUsername = findViewById(R.id.txtUsername);
        btnLogin = findViewById(R.id.btnLogin);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitService = retrofit.create(RetrofitService.class);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = txtUsername.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();
                if (validateInputs(username, password)) {
                    login(username, password);
                }

//                Intent intent = new Intent(Activity_Login.this, Main.class);
//                startActivity(intent);
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
        // Prepare the login request
        ReqLogin reqLogin = new ReqLogin(username, password);

        // Make the login API call
        Call<ResLogin> call = retrofitService.Login(reqLogin);
        call.enqueue(new Callback<ResLogin>() {
            @Override
            public void onResponse(Call<ResLogin> call, Response<ResLogin> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ResLogin resLogin = response.body();

                    if (resLogin.getStatus().equals("true")) {
                        // Check for the token and refreshToken
                        String token = resLogin.getToken();
                        String refreshToken = resLogin.getRefreshToken();
                        String userID = resLogin.getUserID();

                        if (token != null && refreshToken != null) {
                            // Login successful
                            handleLoginSuccess(resLogin);

                            // Save token in SharedPreferences
                            SharedPreferences preferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("AuthToken", token);
                            editor.putString("RefreshToken", refreshToken); // Save refresh token as well
                            editor.putString("loginUserID", userID);
                            editor.apply();
                        } else {
                            // If tokens are missing, something went wrong, handle it
                            Toast.makeText(Activity_Login.this, "Failed to retrieve tokens. Please try again.", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        // Handle specific error messages from backend (banned, invalid credentials, etc.)
                        String message = resLogin.getMessage();
                        if (message.contains("banned")) {
                            // User is banned
                            Toast.makeText(Activity_Login.this, "Your account is banned. Please contact support.", Toast.LENGTH_LONG).show();
                        } else {
                            // Handle other error cases
                            Toast.makeText(Activity_Login.this, message, Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    // Handle HTTP status codes, especially 403 for banned users
                    if (response.code() == 403) {
                        Toast.makeText(Activity_Login.this, "Your account is banned. Please contact support.", Toast.LENGTH_LONG).show();
                    } else {
                        // Handle other response errors (like 401 unauthorized, etc.)
                        Toast.makeText(Activity_Login.this, "Invalid credentials. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResLogin> call, Throwable t) {
                // Show failure message (network issues, etc.)
                Toast.makeText(Activity_Login.this, "Login failed: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    private void handleLoginSuccess(ResLogin resLogin) {
        // Message on success
        if (txtUsername.getText().toString().equals("admin") && txtPassword.getText().toString().equals("admin")) {
            Log.d("LoginActivity", "Admin login detected");
            Toast.makeText(Activity_Login.this, "Admin login successful!", Toast.LENGTH_SHORT).show();
            Toast.makeText(Activity_Login.this, "Login successful!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Activity_Login.this, Activity_Admin_Main.class);
            startActivity(intent);
            finish();
        } else {
            Log.d("LoginActivity", "Regular user login detected");
            Toast.makeText(Activity_Login.this, "Login successful!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Activity_Login.this, Main.class);
            startActivity(intent);
            finish(); // Close this activity so the user cannot go back to login screen
        }
    }
}