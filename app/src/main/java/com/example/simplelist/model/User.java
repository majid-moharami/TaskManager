package com.example.simplelist.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Long mId;
    @ColumnInfo(name = "Username")
    private String mUserName;
    @ColumnInfo(name = "userId")
    private String mUser_ID;
    @ColumnInfo(name = "signUP_date")
    private Date mDate;

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getUser_ID() {
        return mUser_ID;
    }

    public void setUser_ID(String user_ID) {
        mUser_ID = user_ID;
    }

    public User() {
    }

    public User(String userName, String user_ID ) {
        mUserName = userName;
        mUser_ID = user_ID;
        mDate = new Date();
    }
}
