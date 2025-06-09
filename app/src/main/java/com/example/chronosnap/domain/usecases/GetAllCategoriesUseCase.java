package com.example.chronosnap.domain.usecases;

import androidx.lifecycle.MutableLiveData;

import com.example.chronosnap.data.repository.UserRepository;

import java.util.TreeMap;

public class GetAllCategoriesUseCase {
    public static MutableLiveData<TreeMap<String, Integer>> execute() {
        UserRepository repo = new UserRepository();
        return repo.getCategoriesLiveData();
    }
}
