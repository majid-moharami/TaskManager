package com.example.simplelist.repository;

import com.example.simplelist.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskRepository implements IRepository<Task> {

    private String mName;
    private int mNumber;
    private List<Task> mTaskList=new ArrayList<>();
    private static TaskRepository sTaskRepository;

    public static TaskRepository getInstance() {
        return sTaskRepository;
    }

    public static TaskRepository getInstance(String name , int number) {
        if (sTaskRepository == null)
           return sTaskRepository = new TaskRepository(name,number);

        return sTaskRepository;
    }

    private TaskRepository() {
    }

    private TaskRepository(String name, int number) {
        mName = name;
        mNumber = number;
        for (int i = 0; i < mNumber ; i++) {
            Task task = new Task(name);
            mTaskList.add(task);
        }
    }


    @Override
    public List<Task> getList() {
        return mTaskList;
    }

    @Override
    public void setList(List<Task> list) {
        mTaskList = list;
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public void insert(Task task) {
        mTaskList.add(task);
    }

    @Override
    public Task get(int i) {
        return mTaskList.get(i);
    }
}
