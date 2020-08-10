package com.example.simplelist.model;

import com.example.simplelist.Stats;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;

public class Task implements Serializable {
    private String name;
    private Stats mStats;
    private Date mDate;

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

    public Task(){

    }

    public Task(String name) {
        this.name = name;
        mDate=new Date();
        mStats= Stats.randomState();
    }


}
