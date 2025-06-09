package com.example.chronosnap.domain.usecases;

import androidx.lifecycle.LiveData;

import com.example.chronosnap.data.repository.TaskRepository;
import com.example.chronosnap.domain.entities.MyTask;

import java.util.List;

public class GetTasksForDayUseCase {
    TaskRepository repo;
    public GetTasksForDayUseCase(TaskRepository repo) {
        this.repo = repo;
    }

    public LiveData<List<MyTask>> execute(String date, int sectionIndex){
        return repo.getTasks(date, sectionIndex);
    }
}
