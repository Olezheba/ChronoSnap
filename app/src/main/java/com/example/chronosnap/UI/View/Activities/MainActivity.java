package com.example.chronosnap.UI.View.Activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.chronosnap.R;
import com.example.chronosnap.UI.ViewModel.StopwatchVM;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    StopwatchVM stopwatchVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();

        NavigationUI.setupWithNavController(bottomNav, navController);

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