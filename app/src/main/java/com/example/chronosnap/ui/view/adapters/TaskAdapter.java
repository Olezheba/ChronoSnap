package com.example.chronosnap.ui.view.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chronosnap.domain.entities.MyTask;
import com.example.chronosnap.R;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private Context context;
    private List<MyTask> myTaskList;

    public TaskAdapter(Context context, List<MyTask> myTaskList) {
        this.context = context;
        this.myTaskList = myTaskList != null ? myTaskList : new ArrayList<>();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        MyTask myTask = myTaskList.get(position);
        holder.tv.setText(myTask.getName());
        holder.colorView.setBackgroundColor(myTask.getCategoryColor());
        holder.checkbox.setChecked(myTask.isDone());

        if (myTask.isDone()) markAsDone(holder.tv);
        else markAsUndone(holder.tv);

        holder.checkbox.setOnClickListener(v -> {
            myTask.setDone(holder.checkbox.isChecked());
            if (myTask.isDone()) markAsDone(holder.tv);
            else markAsUndone(holder.tv);
        });

        holder.deleteBtn.setOnClickListener(v -> {
            // TODO: Удаление задачи
        });
    }

    @Override
    public int getItemCount() {
        return myTaskList.size();
    }

    public void updateData(List<MyTask> newMyTasks) {
        this.myTaskList = newMyTasks != null ? newMyTasks : new ArrayList<>();
        notifyDataSetChanged();
    }

    private void markAsDone(TextView tv) {
        tv.setTextColor(ContextCompat.getColor(context, R.color.dark_gray));
        tv.setPaintFlags(tv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    private void markAsUndone(TextView tv) {
        tv.setTextColor(ContextCompat.getColor(context, R.color.black));
        tv.setPaintFlags(tv.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        View colorView;
        ImageButton deleteBtn;
        CheckBox checkbox;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.task_text);
            colorView = itemView.findViewById(R.id.category_color_view);
            deleteBtn = itemView.findViewById(R.id.task_delete_btn);
            checkbox = itemView.findViewById(R.id.task_checkbox);
        }
    }
}
