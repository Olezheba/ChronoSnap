package com.example.chronosnap.domain.usecases;

import com.example.chronosnap.data.repository.TaskRepository;
import com.example.chronosnap.domain.entities.MyTask;

public class DeleteTaskUseCase {
    TaskRepository repo;
    public DeleteTaskUseCase(TaskRepository repo) {
        this.repo = repo;
    }

    public void execute(MyTask myTask){
        repo.deleteTask(myTask);
    }
}
