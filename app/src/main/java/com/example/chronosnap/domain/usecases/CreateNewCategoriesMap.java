package com.example.chronosnap.domain.usecases;

import android.content.Context;

import com.example.chronosnap.utils.CategoryUtils;

import java.util.Map;
import java.util.TreeMap;

public class CreateNewCategoriesMap {
    static CategoryUtils utils;
    public static final Map<String,Integer> execute(Context context){
        Map<String,Integer> newMap = new TreeMap<>();
        utils = new CategoryUtils(context);
        for (int i = 0; i<9; i++){
            newMap.put(utils.getApplicationCategories()[i], CategoryUtils.APP_CATEGORY_COLORS[i]);
        }
        return newMap;
    }
}
