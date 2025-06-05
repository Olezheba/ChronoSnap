package com.example.chronosnap.ui.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.InputType;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chronosnap.R;
import com.example.chronosnap.databinding.FragmentEditInfoBinding;
import com.example.chronosnap.domain.entities.User;
import com.example.chronosnap.ui.viewmodel.SettingsVM;
import com.example.chronosnap.utils.ValidCheckers;

import java.util.Locale;

public class EditInfoFragment extends Fragment {

    FragmentEditInfoBinding binding;
    NavController navController;
    SettingsVM vm;
    ValidCheckers vc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditInfoBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(requireActivity()).get(SettingsVM.class);
        vc = new ValidCheckers();

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);

        setupTimeTextFilter(binding.staartTimeEt);
        setupTimeTextFilter(binding.finishTimeEt);
        binding.intervalEt.setInputType(InputType.TYPE_CLASS_NUMBER);
        binding.intervalEt.setFilters(new InputFilter[] { new InputFilter.LengthFilter(3) });

        User user = vm.getUser().getValue();
        if (user != null) {
            binding.usernameEt.setText(user.getUsername());
            binding.emailEt.setText(user.getEmail());
            binding.passwordEt.setText(user.getPassword());

            String startTime = String.format(Locale.getDefault(), "%02d:%02d", user.getSettings().getStartHour(), user.getSettings().getStartMinute());
            String finishTime = String.format(Locale.getDefault(), "%02d:%02d", user.getSettings().getFinishHour(), user.getSettings().getFinishMinute());

            binding.staartTimeEt.setText(startTime);
            binding.finishTimeEt.setText(finishTime);

            binding.intervalEt.setText(String.valueOf(user.getSettings().getInterval()));
        }


        binding.saveBtn.setOnClickListener(v -> {
            String id = vm.getUser().getValue().getId();
            String un = binding.usernameEt.getText().toString().trim();
            String email = binding.emailEt.getText().toString().trim();
            String password = binding.passwordEt.getText().toString().trim();
            String startTime = binding.staartTimeEt.getText().toString().trim();
            String finishTime = binding.finishTimeEt.getText().toString().trim();
            String intervalStr = binding.intervalEt.getText().toString().trim();

            int interval = 0;
            int h1 = 0, m1 = 0, h2 = 0, m2 = 0;
            boolean correct = true;

            if (un.equals("")) {
                binding.usernameEt.setError(getString(R.string.empty_string));
                correct = false;
            }

            if (email.equals("")) {
                binding.emailEt.setError(getString(R.string.empty_string));
                correct = false;
            } else if (!vc.isValidEmail(email)) {
                binding.emailEt.setError(getString(R.string.email_input_error));
                correct = false;
            }

            if (password.equals("")) {
                binding.passwordEt.setError(getString(R.string.empty_string));
                correct = false;
            } else if (!vc.isValidPassword(password)) {
                binding.passwordEt.setError(getString(R.string.password_input_error));
                correct = false;
            }

            if (intervalStr.equals("")) {
                binding.intervalEt.setError(getString(R.string.empty_string));
                correct = false;
            } else {
                try {
                    interval = Integer.parseInt(intervalStr);
                } catch (NumberFormatException e) {
                    binding.intervalEt.setError("Введите число");
                    correct = false;
                }
            }

            if (vc.isValidStartAndFinishTime(startTime, finishTime)) {
                try {
                    String[] parts1 = startTime.split(":");
                    String[] parts2 = finishTime.split(":");

                    h1 = Integer.parseInt(parts1[0]);
                    m1 = Integer.parseInt(parts1[1]);
                    h2 = Integer.parseInt(parts2[0]);
                    m2 = Integer.parseInt(parts2[1]);
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    Toast.makeText(getContext(), "Ошибка в формате времени", Toast.LENGTH_SHORT).show();
                    correct = false;
                }
            } else {
                Toast.makeText(getContext(), "Введите корректное время", Toast.LENGTH_SHORT).show();
                correct = false;
            }

            if (correct) {
                User updateUser = new User(
                        id, un, email, password,
                        h1, m1, h2, m2, interval,
                        vm.getUser().getValue().getSettings().isEnabled(),
                        vm.getUser().getValue().getCategories()
                );
                vm.setUser(updateUser);
                navController.navigate(R.id.action_to_settings);
            }
        });

        binding.back.setOnClickListener(v -> {
            navController.navigate(R.id.action_to_settings);
        });

        return binding.getRoot();
    }



    private void setupTimeTextFilter(EditText editText) {
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(5) });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editText.removeTextChangedListener(this);
                String str = s.toString().replace(":", "");
                StringBuilder formatted = new StringBuilder();
                if (str.length()>2){
                    for (int i=0; i< str.length();i++){
                        if (i==2) formatted.append("3");
                        else formatted.append(str.charAt(i));
                    }
                }

                editText.addTextChangedListener(this);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

}