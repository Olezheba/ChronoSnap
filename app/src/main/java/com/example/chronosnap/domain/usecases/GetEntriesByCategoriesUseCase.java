package com.example.chronosnap.domain.usecases;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.chronosnap.data.repository.ActivityEntriesRepository;
import com.example.chronosnap.data.repository.TaskRepository;
import com.example.chronosnap.domain.entities.ActivityEntry;

import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class GetEntriesByCategoriesUseCase {
    GetDayActivityEntriesUseCase getDayEntries;
    public GetEntriesByCategoriesUseCase(GetDayActivityEntriesUseCase getDayEntries) {
        this.getDayEntries = getDayEntries;
    }
    public LiveData<Map<Map.Entry<String, Integer>, Long>> execute(String startDate, String endDate) {
        MutableLiveData<Map<Map.Entry<String, Integer>, Long>> resultLiveData = new MutableLiveData<>();
        if (startDate.equals(endDate)) {
            getDayEntries.execute(startDate).observeForever(entries -> {
                Map<Map.Entry<String, Integer>, Long> result = new TreeMap<>();
                for (ActivityEntry entry : entries) {
                    Map.Entry<String, Integer> key = new AbstractMap.SimpleEntry<>(entry.getCategoryName(), entry.getCategoryColor());
                    result.put(key, result.getOrDefault(key, 0L) + entry.getDuration());
                }
                resultLiveData.setValue(result);
            });
        }
        return resultLiveData;
    }

}