package com.example.chronosnap.domain.entities;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.util.UUID;

@Entity(tableName = "tasks")
public class MyTask {
    @NonNull
    @PrimaryKey
    private String id;

    @ColumnInfo(name = "user_id")
    private String userId;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "date")
    private String date;
    @ColumnInfo(name = "category_color")
    private int categoryColor;
    @ColumnInfo(name = "section_index")
    private int sectionIndex;
    @ColumnInfo(name = "is_done")
    private boolean isDone;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public MyTask(String id, String uid, String name, int categoryColor, LocalDate date, int sectionIndex){
        this.id = id != null ? id : UUID.randomUUID().toString();
        userId = uid;
        this.name = name;
        this.categoryColor = categoryColor;
        this.sectionIndex = sectionIndex;
        this.date = date.toString();
        isDone = false;
    }

    public MyTask(String id, String uid, String name, int categoryColor, String date, int sectionIndex){
        this.id = id != null ? id : UUID.randomUUID().toString();
        userId = uid;
        this.name = name;
        this.categoryColor = categoryColor;
        this.sectionIndex = sectionIndex;
        this.date = date;
        isDone = false;
    }

    public MyTask(){}

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
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

    public int getSectionIndex() {
        return sectionIndex;
    }

    public void setSectionIndex(int sectionIndex) {
        this.sectionIndex = sectionIndex;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCategoryColor() {
        return categoryColor;
    }

    public void setCategoryColor(int categoryColor) {
        this.categoryColor = categoryColor;
    }
}
