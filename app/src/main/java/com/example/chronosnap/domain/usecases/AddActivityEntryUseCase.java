package com.example.chronosnap.domain.usecases;

import com.example.chronosnap.data.repository.ActivityEntriesRepository;
import com.example.chronosnap.domain.entities.ActivityEntry;

public class AddActivityEntryUseCase {
    private final ActivityEntriesRepository repo;
    public AddActivityEntryUseCase(ActivityEntriesRepository repo) {
        this.repo = repo;
    }
    public void execute(ActivityEntry entry){
        repo.addActivityEntry(entry);
    }
}
