package com.example.chronosnap.data.persistentstorage;

import static android.icu.text.MessagePattern.ArgType.SELECT;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.chronosnap.domain.entities.ActivityEntry;

import java.util.List;

@Dao
public interface ActivityEntryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addEntry(ActivityEntry entry);

    @Query("SELECT * FROM activity_entries WHERE date = :date AND user_id = :uid")
    LiveData<List<ActivityEntry>> getEntries(String date, String uid);

    @Query("SELECT * FROM activity_entries WHERE date > :startDate AND date < :finishDate AND user_id = :uid")
    LiveData<List<ActivityEntry>> getEntries(String startDate, String finishDate, String uid);
}
