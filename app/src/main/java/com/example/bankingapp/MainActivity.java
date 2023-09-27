package com.example.bankingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.bankingapp.fragments.HomeFragment;
import com.example.bankingapp.fragments.PaymentsFragment;
import com.example.bankingapp.fragments.ReportsFragment;
import com.example.bankingapp.fragments.TransactionsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    TransactionsFragment transactionsFragment = new TransactionsFragment();
    PaymentsFragment paymentsFragment = new PaymentsFragment();
    ReportsFragment reportsFragment = new ReportsFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bottomNavigationView = findViewById(R.id.botton_nav_bar);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();

                        return true;
                    case R.id.transactions:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, transactionsFragment).commit();
                        return true;
                    case R.id.payments:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, paymentsFragment).commit();
                        return true;
                    case R.id.reports:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, reportsFragment).commit();
                        return true;
                }
                return false;
            }
        });


    }
}