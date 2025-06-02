package com.example.chronosnap.UI.View.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chronosnap.R;
import com.example.chronosnap.UI.View.Fragments.LoginFragment;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getSharedPreferences("auth_prefs", MODE_PRIVATE);
        boolean isLoggedIn = prefs.getBoolean("is_logged_in", false);

        if (isLoggedIn){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        setContentView(R.layout.activity_auth);

        if (getSupportFragmentManager().findFragmentById(R.id.auth_nav_host_fragment)==null) {
            getSupportFragmentManager().
                    beginTransaction().
                    add(R.id.auth_nav_host_fragment, new LoginFragment()).
                    commit();
        }
    }

}
