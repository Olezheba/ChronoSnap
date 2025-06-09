package com.example.chronosnap.ui.view.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chronosnap.data.persistentstorage.AppDatabase;
import com.example.chronosnap.data.remotedata.ActivityEntryRemoteDataSource;
import com.example.chronosnap.data.repository.ActivityEntriesRepository;
import com.example.chronosnap.domain.usecases.GetDayActivityEntriesUseCase;
import com.example.chronosnap.domain.usecases.GetEntriesByCategoriesUseCase;
import com.example.chronosnap.utils.ChartUtils;
import com.example.chronosnap.databinding.FragmentStatisticsBinding;
import com.github.mikephil.charting.data.PieEntry;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    private Map<Map.Entry<String, Integer>, Long> getCategoriesDurations(String startDate, String finishDate) {
        AppDatabase db = Room.databaseBuilder(requireContext(), AppDatabase.class, "db").build();
        GetEntriesByCategoriesUseCase getEntriesByCategoriesUseCase = new GetEntriesByCategoriesUseCase
                (new GetDayActivityEntriesUseCase(new ActivityEntriesRepository
                        (db,new ActivityEntryRemoteDataSource())));
        return getEntriesByCategoriesUseCase.execute(startDate, finishDate).getValue();
    }

    private ArrayList<PieEntry> getPieEntries(Map<Map.Entry<String, Integer>, Long> categoryDurations) {
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        for (Map.Entry<Map.Entry<String, Integer>, Long> entry : categoryDurations.entrySet()) {
            pieEntries.add(new PieEntry(entry.getValue(), entry.getKey().getValue()));
        }
        return pieEntries;
    }

    private ArrayList<Integer> getPieEntriesColors(Map<Map.Entry<String, Integer>, Long> categoryDurations) {
        ArrayList<Integer> colors = new ArrayList<>();
        for (Map.Entry<Map.Entry<String, Integer>, Long> entry : categoryDurations.entrySet()) {
            colors.add(entry.getKey().getValue());
        }
        return colors;
    }
}