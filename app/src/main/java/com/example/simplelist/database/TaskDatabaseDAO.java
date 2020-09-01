package com.example.simplelist.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.simplelist.model.Task;

import java.util.List;
@Dao
public interface TaskDatabaseDAO {

    @Query("SELECT * FROM Task")
    List<Task> getList();

    @Query("SELECT * FROM TASK WHERE uuid=:uuid")
    Task get(String uuid);

    @Insert
    void insert(Task task);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);
}
