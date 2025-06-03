package com.example.chronosnap.UI.View.Fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chronosnap.Domain.Entities.Category;
import com.example.chronosnap.R;
import com.example.chronosnap.Utils.CalendarUtils;
import com.example.chronosnap.Utils.ChartUtils;
import com.example.chronosnap.databinding.FragmentStatisticsBinding;
import com.github.mikephil.charting.data.PieEntry;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class StatisticsFragment extends Fragment {
    FragmentStatisticsBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStatisticsBinding.inflate(inflater, container, false);

        LocalDate today = LocalDate.now();
        ChartUtils.setupPieChart(binding.dayPieChart,
                getPieEntries(getCategoriesDurations(today.toString(), today.toString())),
                getPieEntriesColors(getCategoriesDurations(today.toString(), today.toString())));
        ChartUtils.setupPieChart(binding.weekPieChart,
                getPieEntries(getCategoriesDurations(today.with(DayOfWeek.MONDAY).toString(), today.toString())),
                getPieEntriesColors(getCategoriesDurations(today.with(DayOfWeek.MONDAY).toString(), today.toString())));
        ChartUtils.setupPieChart(binding.weekPieChart,
                getPieEntries(getCategoriesDurations(today.withDayOfMonth(1).toString(), today.toString())),
                getPieEntriesColors(getCategoriesDurations(today.withDayOfMonth(1).toString(), today.toString())));
        return binding.getRoot();

    }

    private Map<Category, Integer> getCategoriesDurations(String startDate, String finishDate) {
        // TODO получение данных из репозитория / use case
        return new HashMap<>();
    }

    private ArrayList<PieEntry> getPieEntries(Map<Category, Integer> categoryDurations) {
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        for (Map.Entry<Category, Integer> entry : categoryDurations.entrySet()) {
            pieEntries.add(new PieEntry(entry.getValue(), entry.getKey().getName()));
        }
        return pieEntries;
    }

    private ArrayList<Integer> getPieEntriesColors(Map<Category, Integer> categoryDurations) {
        ArrayList<Integer> colors = new ArrayList<>();
        for (Map.Entry<Category, Integer> entry : categoryDurations.entrySet()) {
            colors.add(entry.getKey().getColorIndex());
        }
        return colors;
    }
}