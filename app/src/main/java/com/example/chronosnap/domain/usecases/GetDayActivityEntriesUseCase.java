package com.example.chronosnap.domain.usecases;

import androidx.lifecycle.LiveData;

import com.example.chronosnap.data.repository.ActivityEntriesRepository;
import com.example.chronosnap.domain.entities.ActivityEntry;

import java.util.List;

public class GetDayActivityEntriesUseCase {
    ActivityEntriesRepository repo;
    public GetDayActivityEntriesUseCase(ActivityEntriesRepository repo){
        this.repo = repo;
    }

    public LiveData<List<ActivityEntry>> execute(String selectedDate) {
        return repo.getEntries(selectedDate);
    }
}
