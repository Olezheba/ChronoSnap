package com.example.chronosnap.ui.view.fragments;

import static com.example.chronosnap.utils.CalendarUtils.daysInWeekArray;

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

import com.example.chronosnap.R;
import com.example.chronosnap.ui.view.adapters.CalendarAdapter;
import com.example.chronosnap.utils.CalendarUtils;
import com.example.chronosnap.databinding.FragmentListBinding;

import java.time.LocalDate;
import java.util.ArrayList;


public class ListFragment extends Fragment implements CalendarAdapter.OnItemListener {
    private FragmentListBinding binding;

    @SuppressLint("MissingInflatedId")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentListBinding.inflate(inflater, container, false);;

        binding.back.setOnClickListener(v -> {
            CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
            setWeekView();
        });

        binding.forward.setOnClickListener(v -> {
            CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
            setWeekView();
        });

        if (CalendarUtils.selectedDate == null) {
            CalendarUtils.selectedDate = LocalDate.now();
        }
        setWeekView();

        return binding.getRoot();
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setWeekView() {
        if (binding.monthYearText == null || binding.calendarView == null || CalendarUtils.selectedDate == null) {
            return;
        }

        binding.monthYearText.setText(CalendarUtils.monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(requireContext(), 7);
        binding.calendarView.setLayoutManager(layoutManager);
        binding.calendarView.setAdapter(calendarAdapter);

        Section sec1 = new Section((byte) 1);
        Section sec2 = new Section((byte) 2);
        Section sec3 = new Section((byte) 3);
        Section sec4 = new Section((byte) 4);
        binding.sectionBtn1.setOnClickListener(v -> { onSectionClick(binding.sectionBtn1, sec1); });
        binding.sectionBtn2.setOnClickListener(v -> { onSectionClick(binding.sectionBtn2, sec2); });
        binding.sectionBtn3.setOnClickListener(v -> { onSectionClick(binding.sectionBtn3, sec3); });
        binding.sectionBtn4.setOnClickListener(v -> { onSectionClick(binding.sectionBtn4, sec4); });
        binding.addView1.setOnClickListener(v -> {addTask((byte)1);});
        binding.addView2.setOnClickListener(v -> {addTask((byte)2);});
        binding.addView3.setOnClickListener(v -> {addTask((byte)3);});
        binding.addView4.setOnClickListener(v -> {addTask((byte)4);});
    }

    @Override
    public void onItemClick(int position, LocalDate date) {
        if (date != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CalendarUtils.selectedDate = date;
            setWeekView();

        }
    }

    private void onSectionClick(View v, Section s) {
        ImageButton btn;
        RecyclerView taskList;
        View addView;

        if (v.getId() == R.id.section_btn_1) {
            btn = binding.sectionBtn1;
            taskList = binding.taskList1;
            addView = binding.addView1;
        } else if (v.getId() == R.id.section_btn_2) {
            btn = binding.sectionBtn2;
            taskList = binding.taskList2;
            addView = binding.addView2;
        } else if (v.getId() == R.id.section_btn_3) {
            btn = binding.sectionBtn3;
            taskList = binding.taskList3;
            addView = binding.addView3;
        } else if (v.getId() == R.id.section_btn_4) {
            btn = binding.sectionBtn4;
            taskList = binding.taskList4;
            addView = binding.addView4;
        } else {
            return;
        }

        if (s.isExpanded) {
            taskList.setVisibility(View.GONE);
            addView.setVisibility(View.GONE);
            btn.setEnabled(false);
            btn.animate().rotationBy(-90).setDuration(200).start();
            btn.setEnabled(true);
        } else {
            taskList.setVisibility(View.VISIBLE);
            addView.setVisibility(View.VISIBLE);
            btn.setEnabled(false);
            btn.animate().rotationBy(90).setDuration(200).start();
            btn.setEnabled(true);
        }

        s.setExpansion(!s.isExpanded);
    }

    private void addTask(byte sectionIndex){
        //TODO всплывающее окно добавления задачи
    }

    private static class Section{
        final byte index;
        boolean isExpanded;
        public Section(byte index){
            this.index = index;
            isExpanded = false;
        }

        public void setExpansion(boolean expanded) {
            isExpanded = expanded;
        }
    }

}