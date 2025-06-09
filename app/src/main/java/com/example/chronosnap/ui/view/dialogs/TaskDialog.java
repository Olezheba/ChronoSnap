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

import com.example.chronosnap.databinding.DialogTaskBinding;
import com.example.chronosnap.ui.view.adapters.CategoryAdapter;
import com.example.chronosnap.ui.viewmodel.TaskVM;
import com.example.chronosnap.ui.viewmodelfactories.TaskVMFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TaskDialog extends DialogFragment {
    private SaveListener listener;
    private DialogTaskBinding binding;
    private TaskVM vm;
    private CategoryAdapter adapter;

    public interface SaveListener {
        void onSave(String name, int color);
        void Delete();
    }

    public void setSaveListener(SaveListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DialogTaskBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(requireActivity(), new TaskVMFactory(requireContext())).get(TaskVM.class);

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        adapter = new CategoryAdapter(requireContext(), new ArrayList<>(), true);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.categorySpinner.setAdapter(adapter);

        vm.getCategoriesLiveData().observe(getViewLifecycleOwner(), categoriesMap -> {
            if (categoriesMap != null) {
                List<Map.Entry<String, Integer>> categoryList = new ArrayList<>(categoriesMap.entrySet());
                adapter.clear();
                adapter.addAll(categoryList);
                adapter.notifyDataSetChanged();
            }
        });

        binding.saveBtn.setOnClickListener(v -> {
            String name = binding.taskNameEt.getText().toString();
            Map.Entry<String, Integer> selectedItem = (Map.Entry<String, Integer>) binding.categorySpinner.getSelectedItem();
            int color = selectedItem != null ? selectedItem.getValue() : Color.GRAY;
            if (listener != null) {
                listener.onSave(name, color);
                dismiss();
            }
        });

        binding.deleteBtn.setOnClickListener(v -> dismiss());

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
