package com.example.simplelist.model;

import com.example.simplelist.Stats;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class Task implements Serializable {
    private String name;
    private Stats mStats;
    private Date mDate;
    private String mDescription;
    private UUID mTaskID;
    private String mUserID;

    public String getUserID() {
        return mUserID;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Stats getStats() {
        return mStats;
    }

    public void setStats(Stats stats) {
        mStats = stats;
    }

    public UUID getTaskID() {
        return mTaskID;
    }

    public Task() {
    }

    public Task(String name, Stats stats, Date date, String description, UUID taskID, String userID) {
        this.name = name;
        mStats = stats;
        mDate = date;
        mDescription = description;
        mTaskID = taskID;
        mUserID = userID;
    }

    public Task(String userID){
        mUserID = userID;
        mTaskID = UUID.randomUUID();
    }

    public Task(String name , String userID) {
        this.name = name;
        mDate=new Date();
        mStats= Stats.randomState();
        mTaskID = UUID.randomUUID();
        mUserID = userID;
    }


}
