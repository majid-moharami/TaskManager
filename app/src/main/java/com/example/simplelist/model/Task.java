package com.example.simplelist.model;

import com.example.simplelist.Stats;

import java.util.Random;

public class Task {
    private String name;
    private Stats mStats;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Stats getStats() {
        return mStats;
    }

    public Task(String name) {
        this.name = name;
        mStats= Stats.randomState();
    }

}
