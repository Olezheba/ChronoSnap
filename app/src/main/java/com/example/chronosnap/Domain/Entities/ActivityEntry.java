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
    @PrimaryKey(autoGenerate = true)
    private String id;

    @ColumnInfo(name = "user_id")
    private String userId;
    private String name;
    private byte categoryIndex;
    private LocalDate date;
    private long startTime;
    private long finishTime;
    private long longitude;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ActivityEntry (String userId, String name, byte categoryIndex, long startTime){
        this.userId = userId;
        this.name = name;
        this.categoryIndex = categoryIndex;
        date = LocalDate.now();
        this.startTime = startTime;
        finishTime = System.currentTimeMillis();
        longitude = finishTime-startTime;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ActivityEntry (String userId, String name, byte categoryIndex, long startTime, long finishTime){
        this.userId = userId;
        this.name = name;
        this.categoryIndex = categoryIndex;
        date = LocalDate.now();
        this.startTime = startTime;
        this.finishTime = finishTime;
        longitude = finishTime-startTime;
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

    public byte getCategoryIndex() {
        return categoryIndex;
    }

    public void setCategoryIndex(byte categoryIndex) {
        this.categoryIndex = categoryIndex;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(long finishTime) {
        this.finishTime = finishTime;
    }

    public long getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public LocalDate getDate() { return date; }

    public void setDate(LocalDate date) { this.date = date; }
}
