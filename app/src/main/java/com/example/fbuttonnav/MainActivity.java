package com.example.fbuttonnav;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigation = findViewById(R.id.button_navigation);

        sessionManager = new SessionManager(MainActivity.this);
        if (!sessionManager.getLogin()){
            moveToLogin();
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new BerandaFragment()).commit();

        bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                switch (item.getItemId()){
                    case R.id.nav_beranda:
                        selectedFragment = new BerandaFragment();
                        break;
                    case R.id.nav_daftar:
                        selectedFragment = new DaftarFragment();
                        break;
                    case R.id.nav_form:
                        selectedFragment = new FormFragment();
                        break;
                    case R.id.nav_profil:
                        selectedFragment = new ProfilFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();


                return true;
            }
        });
    }

    public void moveToLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}