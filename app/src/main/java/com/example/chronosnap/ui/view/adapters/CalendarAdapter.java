package com.example.chronosnap.ui.view.adapters;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chronosnap.R;
import com.example.chronosnap.ui.view.viewholders.CalendarViewHolder;
import com.example.chronosnap.utils.CalendarUtils;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder>
{
    ArrayList<LocalDate> days;
    OnItemListener onItemListener;

    public CalendarAdapter(ArrayList<LocalDate> days, OnItemListener onItemListener)
    {
        this.days = days;
        this.onItemListener = onItemListener;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_calendar_day, parent, false);
//        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
//        layoutParams.height = parent.getHeight();

        return new CalendarViewHolder(view, onItemListener, days);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position)
    {
        final LocalDate date = days.get(position);
        if(date == null)
            holder.dayOfMonth.setText("");
        else {
            holder.dayOfMonth.setText(String.valueOf(date.getDayOfMonth()));
            if(date.equals(CalendarUtils.selectedDate)) {
                holder.parentView.setBackgroundColor(ContextCompat.getColor
                        (holder.itemView.getContext(), R.color.blue));
                holder.parentView.getBackground().setAlpha(100);
                holder.dayOfMonth.setTextColor(ContextCompat.getColor
                        (holder.itemView.getContext(), R.color.blue));
            }
        }
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public interface OnItemListener {
        void onItemClick(int position, LocalDate date);
    }
}