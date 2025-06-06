package com.example.chronosnap.ui.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chronosnap.R;

import java.util.List;
import java.util.Map;

public class CategoryAdapter extends ArrayAdapter<Map.Entry<String, Integer>> {
    private boolean isSpinner;
    public CategoryAdapter(@NonNull Context context, List<Map.Entry<String, Integer>> array, boolean isSpinner) {
        super(context, R.layout.item_spinner_category, array);
        this.isSpinner = true;
    }

    public CategoryAdapter(@NonNull Context context, List<Map.Entry<String, Integer>> array) {
        super(context, R.layout.item_category, array);
        isSpinner = false;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Map.Entry<String, Integer> category = getItem(position);

        if (isSpinner) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_spinner_category, null);
            }
        } else {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_category, null);
            }
        }

        TextView tv = convertView.findViewById(R.id.category_name);
        assert category != null;
        tv.setText(category.getKey());
        (convertView.findViewById(R.id.category_color)).setBackgroundColor(category.getValue());

        if (!isSpinner) {
            ImageButton edit = convertView.findViewById(R.id.edit_category_btn);
            if (position<10){
                edit.setVisibility(View.INVISIBLE);
            }
            edit.setOnClickListener(v -> {
                //TODO: изменение/удаление категории / use case
            });
        }

        return convertView;
    }

}
