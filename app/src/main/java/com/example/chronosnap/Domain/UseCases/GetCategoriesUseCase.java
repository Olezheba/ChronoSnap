package com.example.chronosnap.Domain.UseCases;

import androidx.lifecycle.LiveData;

import com.example.chronosnap.Data.Repository.AppRepository;
import com.example.chronosnap.Domain.Entities.Category;

import java.util.List;

public class GetCategoriesUseCase {
    private final AppRepository repository;

    public GetCategoriesUseCase(AppRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<Category>> execute(String userId) {
        return repository.getAllCategories(userId);
    }
}
