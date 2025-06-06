package com.example.chronosnap.ui.view.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.chronosnap.R;
import com.example.chronosnap.data.system.USM;
import com.example.chronosnap.ui.viewmodel.SettingsVM;
import com.example.chronosnap.ui.viewmodel.StopwatchVM;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    StopwatchVM stopwatchVM;
    SettingsVM settingsVM;
    USM usm;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();

        NavigationUI.setupWithNavController(bottomNav, navController);

        try {
            usm = new USM(this);
            usm.writeDown(this);
        } catch (SecurityException e) {
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            startActivity(intent);
        }

        settingsVM = new ViewModelProvider(this).get(SettingsVM.class);
        stopwatchVM = new ViewModelProvider(this).get(StopwatchVM.class);
        stopwatchVM.isRunning().observe(this, isRunning -> {
            if (isRunning != null) {
                if (isRunning) {
                    if (bottomNav.getVisibility()== BottomNavigationView.VISIBLE){
                        bottomNav.animate()
                                .alpha(0f)
                                .setDuration(400)
                                .withEndAction(() -> bottomNav.setVisibility(View.INVISIBLE))
                                .start();
                    }
                } else {
                    if (bottomNav.getVisibility()== BottomNavigationView.INVISIBLE) {
                        bottomNav.animate()
                                .alpha(1f)
                                .setDuration(400)
                                .withEndAction(() -> bottomNav.setVisibility(View.VISIBLE))
                                .start();
                    }
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}