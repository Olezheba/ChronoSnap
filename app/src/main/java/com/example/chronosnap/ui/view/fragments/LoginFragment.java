package com.example.chronosnap.ui.view.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.chronosnap.R;
import com.example.chronosnap.ui.view.activities.MainActivity;
import com.example.chronosnap.databinding.FragmentLoginBinding;
import com.example.chronosnap.utils.ValidCheckers;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {

    private FirebaseAuth auth;
    private FragmentLoginBinding binding;
    private ValidCheckers vc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        auth = FirebaseAuth.getInstance();
        vc = new ValidCheckers();

        binding.loginBtn.setOnClickListener(v -> login());
        binding.regBtn.setOnClickListener(v -> {
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.auth_nav_host_fragment, new RegistrationFragment())
                    .addToBackStack(null)
                    .commit();
        });

        return binding.getRoot();
    }

    private void login(){
        String email = binding.emailEt.getText().toString();
        String password = binding.passwordEt.getText().toString();
        boolean hasErrors = false;

        if (!(vc.isValidEmail(email))){
            binding.emailEt.setError(getResources().getString(R.string.email_input_error));
            hasErrors = true;
        }

        if (TextUtils.isEmpty(password)){
            binding.passwordEt.setError(getResources().getString(R.string.empty_string));
            hasErrors = true;
        }

        if (hasErrors) return;

        binding.loginBtn.setEnabled(false);

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), task -> {
                    binding.loginBtn.setEnabled(true);

                    if (!isAdded() || requireActivity().isFinishing()) return;

                    if (task.isSuccessful()) {
                        SharedPreferences prefs = requireContext().getSharedPreferences("auth_prefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putBoolean("is_logged_in", true);
                        editor.putString("user_email", email);
                        editor.apply();

                        startActivity(new Intent(requireActivity(), MainActivity.class));
                        requireActivity().finish();
                    }else{
                        Toast.makeText(requireContext(), "ОШИБКА ВХОДА", Toast.LENGTH_SHORT).show();
                        binding.loginBtn.setEnabled(true);
                    }
                });
    }

}