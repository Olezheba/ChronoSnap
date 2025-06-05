package com.example.chronosnap.ui.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chronosnap.R;
import com.example.chronosnap.databinding.FragmentSettingsBinding;
import com.example.chronosnap.ui.viewmodel.SettingsVM;

public class SettingsFragment extends Fragment {

    FragmentSettingsBinding binding;
    NavController navController;
    SettingsVM vm = new SettingsVM();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        vm = new ViewModelProvider(requireActivity()).get(SettingsVM.class);
        binding.setViewModel(vm);

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);

        binding.editInfoButton.setOnClickListener(v -> {
            navController.navigate(R.id.action_to_edit_info);
        });

        binding.logOutBtn.setOnClickListener(v -> {
            vm.logOut();
        });

        return binding.getRoot();
    }
}