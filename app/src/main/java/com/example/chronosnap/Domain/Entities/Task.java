package com.example.chronosnap.Domain.Entities;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity(tableName = "tasks")
public class Task {
    @PrimaryKey(autoGenerate = true)
    private String id;

    @ColumnInfo(name = "user_id")
    private String userId;
    private String name;
    private LocalDate date;
    private Category category;
    private byte sectionIndex;
    @ColumnInfo(name = "is_done")
    private boolean isDone;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Task (String uid, String name, Category category, byte sectionIndex){
        userId = uid;
        this.name = name;
        this.category = category;
        this.sectionIndex = sectionIndex;
        date = LocalDate.now();
        isDone = false;
    }

    public Task (String uid, String name, Category category, LocalDate date, byte sectionIndex){
        userId = uid;
        this.name = name;
        this.category = category;
        this.sectionIndex = sectionIndex;
        this.date = date;
        isDone = false;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public byte getSectionIndex() {
        return sectionIndex;
    }

    public void setSectionIndex(byte sectionIndex) {
        this.sectionIndex = sectionIndex;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
