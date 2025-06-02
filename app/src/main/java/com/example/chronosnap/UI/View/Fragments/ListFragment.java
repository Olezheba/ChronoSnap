package com.example.chronosnap.UI.View.Fragments;

import static com.example.chronosnap.Utils.CalendarUtils.daysInWeekArray;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.chronosnap.R;
import com.example.chronosnap.UI.View.Adapters.CalendarAdapter;
import com.example.chronosnap.Utils.CalendarUtils;

import java.time.LocalDate;
import java.util.ArrayList;


public class ListFragment extends Fragment implements CalendarAdapter.OnItemListener {


    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ImageButton back;
    private ImageButton forward;

    @SuppressLint("MissingInflatedId")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        monthYearText = view.findViewById(R.id.month_year_text_list);
        calendarRecyclerView = view.findViewById(R.id.calendar_view_list);
        back = view.findViewById(R.id.calendar_back_arrow_list);
        forward = view.findViewById(R.id.calendar_forward_arrow_list);

        back.setOnClickListener(v -> {
            CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
            setWeekView();
        });

        forward.setOnClickListener(v -> {
            CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
            setWeekView();
        });

        if (CalendarUtils.selectedDate == null) {
            CalendarUtils.selectedDate = LocalDate.now();
        }
        setWeekView();

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setWeekView() {
        if (monthYearText == null || calendarRecyclerView == null || CalendarUtils.selectedDate == null) {
            return;
        }

        monthYearText.setText(CalendarUtils.monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(requireContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    @Override
    public void onItemClick(int position, LocalDate date) {
        if (date != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CalendarUtils.selectedDate = date;
            setWeekView();
        }
    }

}