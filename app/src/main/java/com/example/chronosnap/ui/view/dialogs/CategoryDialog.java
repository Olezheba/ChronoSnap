package com.example.chronosnap.ui.view.dialogs;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
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

    private DialogCategoryBinding binding;
    private SettingsVM vm;
    private int checkedColorIndex = 0;
    private boolean newCategory;
    private String oldName;

    private List<Integer> availableColors;

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

        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        if (getArguments() != null) {
            newCategory = getArguments().getBoolean("newCategory", true);
            oldName = getArguments().getString("oldName");
        }

        ArrayList<Integer> allColors = new ArrayList<>();
        for (int c : CategoryUtils.CATEGORY_COLORS) {
            allColors.add(c);
        }

        vm.getCategories().observe(getViewLifecycleOwner(), existingCategories -> {
            if (existingCategories == null) return;

            binding.pallete.removeAllViews();

            // Отфильтруем занятые цвета
            availableColors = new ArrayList<>();
            for (Integer color : allColors) {
                if (!existingCategories.containsValue(color)) {
                    availableColors.add(color);
                }
            }

            // Если редактируем категорию, добавим её цвет в доступные, чтобы не пропал выбор
            if (!newCategory && oldName != null && existingCategories.containsKey(oldName)) {
                Integer oldColor = existingCategories.get(oldName);
                if (oldColor != null && !availableColors.contains(oldColor)) {
                    availableColors.add(0, oldColor); // Добавим в начало списка, чтобы выбрать по умолчанию
                    checkedColorIndex = 0;
                }
            }

            // Отрисовка цветов
            for (int i = 0; i < availableColors.size(); i++) {
                int color = availableColors.get(i);

                MaterialCardView colorView = new MaterialCardView(getContext());
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35,
                        getResources().getDisplayMetrics());
                params.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35,
                        getResources().getDisplayMetrics());
                params.setMargins(8, 8, 8, 8);
                colorView.setLayoutParams(params);
                colorView.setCardBackgroundColor(color);
                colorView.setClickable(true);
                colorView.setFocusable(true);
                colorView.setStrokeWidth(i == checkedColorIndex ? 6 : 0);
                colorView.setStrokeColor(Color.BLACK);

                int index = i;
                colorView.setOnClickListener(v -> {
                    checkedColorIndex = index;
                    // Обновим выделение
                    for (int j = 0; j < binding.pallete.getChildCount(); j++) {
                        View child = binding.pallete.getChildAt(j);
                        if (child instanceof MaterialCardView) {
                            MaterialCardView card = (MaterialCardView) child;
                            card.setStrokeWidth(j == checkedColorIndex ? 6 : 0);
                        }
                    }
                });

                binding.pallete.addView(colorView);
            }
            // Если редактируем, подставим имя в поле ввода
            if (!newCategory && oldName != null) {
                binding.categoryEt.setText(oldName);
            }

            binding.saveBtn.setOnClickListener(v -> {
                String categoryName = binding.categoryEt.getText().toString().trim();
                if (categoryName.isEmpty()) {
                    binding.categoryEt.setError("Пустое поле");
                    return;
                }

                TreeMap<String, Integer> updatedCategories = new TreeMap<>(existingCategories);
                if (newCategory) {
                    updatedCategories.put(categoryName, availableColors.get(checkedColorIndex));
                } else {
                    // Удаляем старую категорию если имя изменилось
                    if (oldName != null && !oldName.equals(categoryName)) {
                        updatedCategories.remove(oldName);
                    }
                    updatedCategories.put(categoryName, availableColors.get(checkedColorIndex));
                }

                vm.onSave(updatedCategories);
                dismiss();
            });
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
