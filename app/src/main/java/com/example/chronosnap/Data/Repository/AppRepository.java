package com.example.chronosnap.Data.Repository;

import androidx.lifecycle.LiveData;

import com.example.chronosnap.Domain.Entities.ActivityEntry;
import com.example.chronosnap.Domain.Entities.Category;
import com.example.chronosnap.Domain.Entities.Task;

import java.util.List;
import java.util.Map;

public interface AppRepository {

    //Categories
    public LiveData<List<Category>> getAllCategories(String userId);
    public LiveData<Category> getCategoryById(String id);
    public void addCategory(Category category);
    public void deleteCategory(Category category);
    public void editCategory(Category category);

    //Tasks
    public LiveData<List<Task>> getAllTasks(String userId);
    public LiveData<Task> getTaskById(String id);
    public void addTask(Task task);
    public void deleteTask(Task task);
    public void editTask(Task task, String id);

    //ActivityEntry
    public LiveData<List<ActivityEntry>> getEntriesForPeriod(String period);
    public LiveData<Task> getEntryById(String id);
    public int getCategoryTimeForPeriod(int categoryIndex, String startDate, String finishDate);
    public Map<String, Integer> getAllCategoriesTimeForPeriod(String startDate, String finishDate);
    public void insertActivityEntry(ActivityEntry activityEntry);
    public void deleteActivityEntry(String id);
}
