package com.example.myapplication.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
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

                Intent intent = new Intent(Activity_Login.this, Main.class);
                startActivity(intent);
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
                    handleLoginSuccess(resLogin);
                    // Get the token from the response
                    String token = response.body().getToken();  // Assuming the token is returned in the ResLogin object
                    // Save token in a shared preference or global variable for use in future requests
                    // For example, using SharedPreferences
                    SharedPreferences preferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("AuthToken", token);
                    editor.apply();
                } else {
                    // Show error message from server or general error
                    Toast.makeText(Activity_Login.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResLogin> call, Throwable t) {
                // Show failure message
                Toast.makeText(Activity_Login.this, "Login failed: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void handleLoginSuccess(ResLogin resLogin) {
        // Message on success
        Toast.makeText(Activity_Login.this, "Login successful!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Activity_Login.this, Main.class);
        startActivity(intent);
        finish(); // Close this activity so the user cannot go back to login screen
    }
}