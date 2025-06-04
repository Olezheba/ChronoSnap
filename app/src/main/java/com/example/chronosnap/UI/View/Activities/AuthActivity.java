package com.example.chronosnap.UI.View.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chronosnap.R;
import com.example.chronosnap.UI.View.Fragments.LoginFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null){
            startActivity(new Intent(this, MainActivity.class));
            finish();
            Log.d("AUTH_DEBUG", "Current user: "+ user);
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
