package com.example.chronosnap.data.persistentstorage;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.chronosnap.domain.entities.MyTask;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTask(MyTask myTask);

    @Delete
    void deleteTask(MyTask myTask);

    @Query("SELECT * FROM MyTask WHERE date = :date AND user_id = :uid AND section_index = :sectionIndex ORDER BY id ASC")
    LiveData<List<MyTask>> getTasks(String date, String uid, int sectionIndex);
}
