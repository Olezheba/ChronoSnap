package com.example.chronosnap.domain.usecases;

import com.example.chronosnap.data.repository.UserRepository;
import com.google.android.gms.tasks.OnCompleteListener;

import java.util.TreeMap;

public class UpdateAllCategoriesUseCase {
    private final UserRepository repo;

    public UpdateAllCategoriesUseCase(UserRepository repo) {
        this.repo = repo;
    }

    public void execute(TreeMap<String, Integer> categories, OnCompleteListener<Void> listener) {
        repo.updateAllCategories(categories, listener);
    }
}
