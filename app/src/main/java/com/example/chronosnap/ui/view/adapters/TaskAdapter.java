package com.example.chronosnap.ui.view.adapters;

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

import com.example.chronosnap.domain.entities.Task;
import com.example.chronosnap.R;

import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task> {

    public TaskAdapter(Context context, List<Task> array){
        super(context, R.layout.item_task, array);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Task task = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_task, null);
        }

        TextView tv =  convertView.findViewById(R.id.task_text);
        assert task != null;
        tv.setText(task.getName());
        (convertView.findViewById(R.id.category_color_view)).setBackgroundColor(task.getCategoryColor());

//        ImageButton shift = convertView.findViewById(R.id.task_shift_btn);
//        shift.setOnClickListener(v -> {
//        });

        ImageButton delete = convertView.findViewById(R.id.task_delete_btn);
        delete.setOnClickListener(v -> {
            //TODO удаление задачи
        });

        CheckBox ch = convertView.findViewById(R.id.task_checkbox);
        if (task.isDone()) MarkTaskAsDone(tv);
        ch.setChecked(task.isDone());
        ch.setOnClickListener(v -> {
            task.setDone(ch.isChecked());
            if (task.isDone()) MarkTaskAsDone(tv);
            else MarkTaskAsUndone(tv);
        });
        return convertView;
    }

    @SuppressLint("ResourceAsColor")
    private void MarkTaskAsDone(TextView tv){
        tv.setTextColor(R.color.dark_gray);
        tv.setPaintFlags(tv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    @SuppressLint("ResourceAsColor")
    private void MarkTaskAsUndone(TextView tv){
        tv.setTextColor(R.color.black);
        tv.setPaintFlags(tv.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
    }
}
