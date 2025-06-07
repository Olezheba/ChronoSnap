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

public class TaskDialog extends DialogFragment {
    private SaveListener listener;
    private DialogTaskBinding binding;
    private TaskVM vm;


    public interface SaveListener{
        void onSave(String name, int color);
        void Delete();
    }
    public void setSaveListener(SaveListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DialogTaskBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(requireActivity()).get(TaskVM.class);

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        CategoryAdapter adapter = new CategoryAdapter(requireContext(), vm.getAllCategoriesList(), true);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.categorySpinner.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }

}
