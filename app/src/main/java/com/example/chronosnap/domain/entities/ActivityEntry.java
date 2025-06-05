package com.example.chronosnap.domain.entities;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.util.Objects;

@Entity(tableName = "activity_entries")
public class ActivityEntry {
    @PrimaryKey
    @NonNull
    private String id;

    @ColumnInfo(name = "user_id")
    private String userId;
    private int categoryIndex;
    private String date;
    private long startTime;
    private long duration;
    private int priority;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ActivityEntry (String userId, Integer categoryIndex, long startTime, long duration, int priority){
        this.userId = userId;
        this.categoryIndex = categoryIndex;
        date = LocalDate.now().toString();
        this.startTime = startTime;
        this.duration = duration;
        this.priority = priority;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActivityEntry that = (ActivityEntry) o;
        return categoryIndex == that.categoryIndex && startTime == that.startTime && duration == that.duration && Objects.equals(id, that.id) && Objects.equals(userId, that.userId) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, categoryIndex, date, startTime, duration);
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
