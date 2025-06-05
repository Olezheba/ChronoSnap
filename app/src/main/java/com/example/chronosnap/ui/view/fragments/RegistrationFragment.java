package com.example.chronosnap.ui.view.fragments;

import static android.content.Context.MODE_PRIVATE;
import static androidx.databinding.DataBindingUtil.setContentView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.chronosnap.domain.entities.User;
import com.example.chronosnap.R;
import com.example.chronosnap.ui.view.activities.MainActivity;
import com.example.chronosnap.databinding.FragmentRegistrationBinding;
import com.example.chronosnap.utils.ValidCheckers;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationFragment extends Fragment {

    private FirebaseAuth auth;
    private FragmentRegistrationBinding binding;
    DatabaseReference db;
    private ValidCheckers vc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentRegistrationBinding.inflate(getLayoutInflater());
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();
        vc = new ValidCheckers();

        binding.loginBtn.setOnClickListener(v -> registration());
        binding.lgnBtn.setOnClickListener(v -> {
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.auth_nav_host_fragment, new LoginFragment())
                    .addToBackStack(null)
                    .commit();
        });

        return binding.getRoot();
    }

    private void registration() {
        String un = binding.usernameEt.getText().toString();
        String email = binding.emailEt.getText().toString();
        String password = binding.passwordEt.getText().toString();

        boolean hasErrors = false;

        if (!(vc.isValidEmail(email))) {
            binding.emailEt.setError(getResources().getString(R.string.email_input_error));
            hasErrors = true;
        }

        if (!(vc.isValidPassword(password))) {
            binding.passwordEt.setError(getResources().getString(R.string.password_input_error));
            hasErrors = true;
        }

        if (hasErrors) return;

        binding.loginBtn.setEnabled(false);

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        binding.loginBtn.setEnabled(true);

                        if (!isAdded() || requireActivity().isFinishing()) return;

                        String uid = auth.getCurrentUser().getUid();
                        User newUser = new User(un, email, password);
                        db.child("users").child(uid).setValue(newUser);

                        FirebaseUser firebaseUser = auth.getCurrentUser();
                        UserProfileChangeRequest profileupdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(un)
                                .build();

                        firebaseUser.updateProfile(profileupdates)
                                .addOnCompleteListener(profileTask -> {
                                    if (!profileTask.isSuccessful()) {
                                        Toast.makeText(getContext(),
                                                "Не удалось создать пользователя",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                })

                        SharedPreferences prefs = requireContext().getSharedPreferences
                                ("auth_prefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putBoolean("is_logged_in", true);
                        editor.putString("user_email", email);
                        editor.apply();

                        startActivity(new Intent(requireActivity(), MainActivity.class));
                        requireActivity().finish();
                    } else {
                        Toast.makeText(requireContext(), "ОШИБКА РЕГИСТРАЦИИ",
                                Toast.LENGTH_SHORT).show();
                        binding.loginBtn.setEnabled(true);
                    }
                });
    }


}