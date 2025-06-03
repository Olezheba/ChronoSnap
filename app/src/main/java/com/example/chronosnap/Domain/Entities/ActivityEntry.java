package com.example.chronosnap.Domain.Entities;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Duration;

@Entity(tableName = "activity_entries")
public class ActivityEntry {
    @PrimaryKey
    @NonNull
    private String id;

    @ColumnInfo(name = "user_id")
    private String userId;
    private String name;
    private int categoryIndex;
    private String date;
    private long startTime;
    private long duration;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ActivityEntry (String userId, String name, byte categoryIndex, long startTime){
        this.userId = userId;
        this.name = name;
        this.categoryIndex = categoryIndex;
        date = LocalDate.now().toString();
        this.startTime = startTime;
        duration = System.currentTimeMillis()-startTime;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ActivityEntry (String userId, String name, byte categoryIndex, long startTime, long duration){
        this.userId = userId;
        this.name = name;
        this.categoryIndex = categoryIndex;
        date = LocalDate.now().toString();
        this.startTime = startTime;
        this.duration = duration;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCategoryIndex() {
        return categoryIndex;
    }

    public void setCategoryIndex(int categoryIndex) {
        this.categoryIndex = categoryIndex;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getDuration() { return duration; }

    public void setDuration(long duration) { this.duration = duration; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }
}
