package com.example.chronosnap.domain.usecases;

import com.example.chronosnap.data.repository.TaskRepository;
import com.example.chronosnap.domain.entities.MyTask;

public class AddNewTaskUseCase {
    private final TaskRepository repo;
    public AddNewTaskUseCase(TaskRepository repo){
        this.repo = repo;
    }

    public void execute(MyTask myTask){
        repo.insertTask(myTask);
    }
}
