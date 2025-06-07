package com.example.chronosnap.domain.usecases;

import androidx.lifecycle.MutableLiveData;

import com.example.chronosnap.domain.entities.Task;

import java.time.LocalDate;
import java.util.ArrayList;

public class GetTasksForDayUseCase {
    public static ArrayList<Task> execute(LocalDate date, int sectionIndex){
        ArrayList<Task> list = new ArrayList<>();
        //TODO получение задач / use case
        return list;
    }
}
