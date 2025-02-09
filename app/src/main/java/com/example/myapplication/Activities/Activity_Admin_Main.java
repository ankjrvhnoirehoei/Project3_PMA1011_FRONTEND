package com.example.myapplication.Activities;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Fragments.Fragment_Admin_Bills;
import com.example.myapplication.Fragments.Fragment_Admin_Phones;
import com.example.myapplication.Fragments.Fragment_Admin_Users;
import com.example.myapplication.Fragments.Fragment_Cart;
import com.example.myapplication.Fragments.Fragment_Home;
import com.example.myapplication.Fragments.Fragment_User;
import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Activity_Admin_Main extends AppCompatActivity {
    BottomNavigationView bottomNavView;
    private static final String TAG = "AdminMainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        Log.d(TAG, "Admin activity opened successfully");
        bottomNavView = findViewById(R.id.bottomNav);
        Fragment fragment_df = new Fragment_Admin_Users();
        getSupportFragmentManager().beginTransaction().replace(R.id.currentFragment, fragment_df).commit();

        bottomNavView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                if (item.getItemId() == R.id.navUsers) {
                    fragment = new Fragment_Admin_Users();
                    Log.d(TAG, "Loading fragment: " + fragment.getClass().getSimpleName());
                } else if (item.getItemId() == R.id.navBills) {
                    fragment = new Fragment_Admin_Bills();
                } else if (item.getItemId() == R.id.navPhones) {
                    fragment = new Fragment_Admin_Phones();
                }

                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.currentFragment, fragment).commit();
                    return true;
                }

                return false;
            }
        });
    }
}