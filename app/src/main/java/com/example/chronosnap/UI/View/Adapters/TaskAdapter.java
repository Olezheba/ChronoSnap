package com.example.chronosnap.UI.View.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.chronosnap.Domain.Entities.Task;
import com.example.chronosnap.R;

import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task> {

    public TaskAdapter(Context context, List<Task> array){
        super(context, R.layout.item_task, array);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Task task = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_task, null);
        }

        TextView tv =  convertView.findViewById(R.id.task_text);
        tv.setText(task.getName());
        ((View) convertView.findViewById(R.id.category_color_view)).setBackgroundColor(task.getCategory().getColorIndex());

        ImageButton shift = convertView.findViewById(R.id.task_shift_btn);
        shift.setOnClickListener(v -> {
            //TODO сдвиг задачи на след. день
        });

        ImageButton edit = convertView.findViewById(R.id.task_edit_btn);
        edit.setOnClickListener(v -> {
            //TODO изменение/удаление задачи
        });

        CheckBox ch = convertView.findViewById(R.id.task_checkbox);
        if (task.isDone()) MarkTaskAsDone(tv, shift);
        ch.setChecked(task.isDone());
        ch.setOnClickListener(v -> {
            task.setDone(ch.isChecked());
            if (task.isDone()) MarkTaskAsDone(tv, shift);
            else MarkTaskAsUndone(tv, shift);
        });

        return convertView;
    }

    @SuppressLint("ResourceAsColor")
    private void MarkTaskAsDone(TextView tv, ImageButton ib){
        tv.setTextColor(R.color.dark_gray);
        tv.setPaintFlags(tv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        ib.setVisibility(View.INVISIBLE);
    }

    @SuppressLint("ResourceAsColor")
    private void MarkTaskAsUndone(TextView tv, ImageButton ib){
        tv.setTextColor(R.color.black);
        tv.setPaintFlags(tv.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        ib.setVisibility(View.VISIBLE);
    }
}
