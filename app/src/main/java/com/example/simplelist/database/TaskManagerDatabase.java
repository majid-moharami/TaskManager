package com.example.simplelist.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.simplelist.model.Task;
import com.example.simplelist.model.User;

@Database(entities = {Task.class, User.class} , version = 1 , exportSchema = false)
@TypeConverters({Task.UUidConverter.class , Task.DateConverters.class , Task.StatesConverter.class})
public abstract class TaskManagerDatabase extends RoomDatabase {
    public abstract TaskDatabaseDAO taskDao();
    public abstract UserDatabaseDAO userDao();
}
