package com.example.chronosnap.ui.view.dialogs;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.chronosnap.databinding.DialogCategoryBinding;
import com.example.chronosnap.ui.viewmodel.SettingsVM;
import com.example.chronosnap.utils.CategoryUtils;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class CategoryDialog extends DialogFragment {
    public void setSaveListener(SaveListener listener) {
        this.listener = listener;
    }

    public interface SaveListener{
        void onSave(Map<String, Integer> categories);
        void onDelete();
    }

    DialogCategoryBinding binding;
    SettingsVM vm;
    private SaveListener listener;
    private int checkedColorIndex;
    Boolean newCategory;
    String oldName;

    public static CategoryDialog newInstance(boolean isNewCategory, String oldName) {
        CategoryDialog fragment = new CategoryDialog();
        Bundle args = new Bundle();
        args.putBoolean("newCategory", isNewCategory);
        args.putString("oldName", oldName);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DialogCategoryBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(requireActivity()).get(SettingsVM.class);

        newCategory = null;
        oldName = null;

        Bundle args = getArguments();
        newCategory = args.getBoolean("newCategory");
        oldName = args.getString("oldName");

        List<Map.Entry<String, Integer>> categoryList =  vm.getAllCategoriesList();
        ArrayList<Integer> colors = Arrays.stream(CategoryUtils.CATEGORY_COLORS)
                .boxed()
                .collect(Collectors.toCollection(ArrayList<Integer>::new));

        checkedColorIndex = 0;
        int i = 0;
        for (int color : colors) {
            boolean isAdded = false;
            for (Map.Entry<String, Integer> x : categoryList) {
                if (x.getValue() == color){
                    isAdded = true;
                }
            }
            if (isAdded) continue;
            MaterialCardView view = new MaterialCardView(getContext());
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,35,
                    getResources().getDisplayMetrics());
            params.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,35,
                    getResources().getDisplayMetrics());

            view.setLayoutParams(params);
            view.setCardBackgroundColor(color);
            binding.pallete.addView(view);
            int index = i;
            view.setOnClickListener(v -> {
                checkedColorIndex = index;
                GridLayout.LayoutParams newParams = new GridLayout.LayoutParams();
                newParams.rowSpec = GridLayout.spec(index<9 ? 1 : 2);
                newParams.columnSpec = GridLayout.spec(index<9 ? index : index-8);
            });
            i++;
        }

        binding.saveBtn.setOnClickListener(v -> {
            if (binding.categoryEt.getText().toString().equals("")) {
                binding.categoryEt.setError("Пустое поле");
                return;
            }
            Map<String, Integer> categories = new TreeMap<>();
            for (Map.Entry<String, Integer> category : categoryList){
                categories.put(category.getKey(), category.getValue());
            }
            if (newCategory) {
                categories.put(binding.categoryEt.getText().toString(), colors.get(checkedColorIndex));
            } else {
                categories.remove(oldName);
                categories.put(binding.categoryEt.getText().toString(), colors.get(checkedColorIndex));
            }
            if (listener != null){
                listener.onSave(categories);
            }
        });

        return binding.getRoot();
    }


}
