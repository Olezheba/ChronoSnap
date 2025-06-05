package com.example.chronosnap.ui.view.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.chronosnap.ui.view.adapters.CategoryAdapter;
import com.example.chronosnap.ui.viewmodel.StopwatchVM;
import com.example.chronosnap.databinding.DialogStopwatchSavingBinding;

public class SaveRecordDialogFragment extends DialogFragment {

    public interface SaveListener{
        void onSave(String selectedCategory, byte priorety);
        void onDelete();
    }
    DialogStopwatchSavingBinding binding;
    StopwatchVM vm;
    private SaveListener listener;

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DialogStopwatchSavingBinding.inflate(inflater, container, false);
        vm = new StopwatchVM();

        CategoryAdapter adapter = new CategoryAdapter(requireContext(), vm.getAllCategoriesList(), true);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.categorySpinner.setAdapter(adapter);

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
