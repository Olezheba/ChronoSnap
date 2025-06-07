package com.example.chronosnap.domain.usecases;

import com.example.chronosnap.domain.entities.ActivityEntry;

import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class GetEntriesByCategoriesUseCase {
    public static Map<Map.Entry<String, Integer>, Long> execute(String startDate, String endDate) {
        List<ActivityEntry> allEntries = new ArrayList<>();
        //TODO получение всех записей за день / repo
        Map<Map.Entry<String, Integer>, Long> result = new TreeMap<>();
        for (ActivityEntry entry : allEntries) {
            if (result.containsKey(entry.getCategoryName()))
                result.put(new AbstractMap.SimpleEntry<>(entry.getCategoryName(), entry.getCategoryColor()),
                        result.get(new AbstractMap.SimpleEntry<>
                                (entry.getCategoryName(), entry.getCategoryColor())) + entry.getDuration());
            else
                result.put(new AbstractMap.SimpleEntry<>(entry.getCategoryName(), entry.getCategoryColor()),
                        entry.getDuration());
        }
        return result;
    }
}