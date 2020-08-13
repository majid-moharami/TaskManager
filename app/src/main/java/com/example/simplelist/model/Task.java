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

    public Task(){
        mTaskID = UUID.randomUUID();
    }

    public Task(String name) {
        this.name = name;
        mDate=new Date();
        mStats= Stats.randomState();
        mTaskID = UUID.randomUUID();
    }


}
