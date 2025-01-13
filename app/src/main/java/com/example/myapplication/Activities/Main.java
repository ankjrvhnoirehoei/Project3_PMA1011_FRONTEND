package com.example.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Fragments.Fragment_Cart;
import com.example.myapplication.Fragments.Fragment_Home;
import com.example.myapplication.Fragments.Fragment_User;
import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Main extends AppCompatActivity {
    Toolbar toolbar;
    BottomNavigationView bottomNavView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");

        bottomNavView = findViewById(R.id.bottomNav);
        Fragment fragment_df = new Fragment_Home();
        getSupportFragmentManager().beginTransaction().replace(R.id.currentFragment, fragment_df).commit();

        bottomNavView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                if (item.getItemId() == R.id.navHome) {
                    fragment = new Fragment_Home();
                } else if (item.getItemId() == R.id.navCart) {
                    fragment = new Fragment_Cart();
                } else if (item.getItemId() == R.id.navUser) {
                    fragment = new Fragment_User();
                }

                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.currentFragment, fragment).commit();
                    return true;
                }

                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.toolbarNotification) {

            return true;

        } else if (item.getItemId() == R.id.toolbarSearch) {
            startActivity(new Intent(Main.this, Activity_Search.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}