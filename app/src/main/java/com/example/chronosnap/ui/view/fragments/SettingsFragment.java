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
import com.example.chronosnap.ui.view.adapters.CategoryAdapter;
import com.example.chronosnap.ui.view.dialogs.CategoryDialog;
import com.example.chronosnap.ui.viewmodel.SettingsVM;

import java.util.Map;

public class SettingsFragment extends Fragment {

    FragmentSettingsBinding binding;
    NavController navController;
    SettingsVM vm;
    CategoryAdapter categoryAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        vm = new ViewModelProvider(requireActivity()).get(SettingsVM.class);
        vm.loadSettingsFromPreferences(requireContext());
        binding.setViewModel(vm);

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);

        categoryAdapter = new CategoryAdapter(requireContext(), vm.getAllCategoriesList());
        binding.categoryList.setAdapter(categoryAdapter);

        binding.editInfoButton.setOnClickListener(v -> {
            navController.navigate(R.id.action_to_edit_info);
        });

        binding.logOutBtn.setOnClickListener(v -> {
            vm.logOut(getContext());
        });

        binding.notifSwitch.setUseMaterialThemeColors(true);
        binding.notifSwitch.setOnClickListener(v -> {
            vm.enableNotification(requireContext(), binding.notifSwitch.isChecked());
        });

        binding.addCategoryLayout.setOnClickListener(v -> {
            CategoryDialog dialog = CategoryDialog.newInstance(true, null);
            dialog.show(getParentFragmentManager(), "CategoryDialog");
            dialog.setSaveListener(new CategoryDialog.SaveListener() {
                @Override
                public void onSave(Map<String, Integer> categories) {
                    vm.updateAllCategories(categories);
                }
                @Override
                public void onDelete() {}
            });
        });

        return binding.getRoot();
    }
}