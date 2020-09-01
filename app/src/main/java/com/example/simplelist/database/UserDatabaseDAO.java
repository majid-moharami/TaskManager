package com.example.simplelist.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.simplelist.model.Task;
import com.example.simplelist.model.User;

import java.util.List;

@Dao
public interface UserDatabaseDAO {

    @Query("SELECT * FROM User")
    List<User> getList();

    @Query("SELECT * FROM User WHERE id=:id")
    User get(Long id);

    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);
}
