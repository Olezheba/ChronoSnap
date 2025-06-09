package com.example.chronosnap.ui.view.dialogs;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.chronosnap.ui.view.adapters.CategoryAdapter;
import com.example.chronosnap.ui.viewmodel.StopwatchVM;
import com.example.chronosnap.databinding.DialogStopwatchSavingBinding;
import com.example.chronosnap.ui.viewmodelfactories.StopwatchVMFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SaveRecordDialogFragment extends DialogFragment {

    public interface SaveListener{
        void onSave(String selectedCategory, int priorety);
        void onDelete();
    }
    DialogStopwatchSavingBinding binding;
    StopwatchVM vm;
    private SaveListener listener;

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DialogStopwatchSavingBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(requireActivity(), new StopwatchVMFactory(requireContext())).get(StopwatchVM.class);

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        vm.getAllCategories().observe(getViewLifecycleOwner(), categoriesMap -> {
            if (categoriesMap != null && !categoriesMap.isEmpty()) {
                List<Map.Entry<String, Integer>> categoryList = new ArrayList<>(categoriesMap.entrySet());
                CategoryAdapter adapter = new CategoryAdapter(requireContext(), categoryList, true);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.categorySpinner.setAdapter(adapter);
            }
        });

        CategoryAdapter adapter = new CategoryAdapter(requireContext(), vm.getAllCategoriesList(), true);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.categorySpinner.setAdapter(adapter);
        vm.getAllCategories().observe(getViewLifecycleOwner(), observe -> {
            CategoryAdapter adapter2 = new CategoryAdapter(requireContext(), vm.getAllCategoriesList(), true);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            binding.categorySpinner.setAdapter(adapter2);
        });

        binding.saveActivityEntry.setOnClickListener(v -> {
            String category = binding.categorySpinner.getSelectedItem().toString();
            byte priority = binding.showScreentimeCheckbox.isChecked() ? (byte) 1 : (byte) 3;
            if (listener != null)
                listener.onSave(category, priority);
            dismiss();
        });
        binding.deleteActivityEntry.setOnClickListener(v -> {
            if (listener != null)
                listener.onDelete();
            dismiss();
        });

        return binding.getRoot();
    }

    public void setSaveListener(SaveListener listener){
        this.listener = listener;
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }

}
