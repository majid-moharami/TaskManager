package com.example.simplelist.repository;

import com.example.simplelist.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    public Task get(UUID i) {
        for (int j = 0; j <mTaskList.size() ; j++) {
            if (mTaskList.get(j).getTaskID() == i){
                return mTaskList.get(j);
            }
        }
        return null;
    }

    public void delete(Task task){
        for (int i = 0; i <mTaskList.size() ; i++) {
            if (mTaskList.get(i).getTaskID() == task.getTaskID()) {
               mTaskList.remove(i);
            }
        }
    }

    public void update(Task task) {
        Task updateTask = get(task.getTaskID());
        updateTask.setName(task.getName());
        updateTask.setDate(task.getDate());
        updateTask.setStats(task.getStats());
        updateTask.setDescription(task.getDescription());
    }
    public void updateTask(Task task){
        for (int i = 0; i <mTaskList.size() ; i++) {
            if (mTaskList.get(i).getTaskID() == task.getTaskID()) {
                mTaskList.get(i).setDescription(task.getDescription());
                mTaskList.get(i).setStats(task.getStats());
                mTaskList.get(i).setName(task.getName());
                mTaskList.get(i).setDate(task.getDate());
            }
        }
    }
}
