package com.example.simplelist.model;

public class User {
    private String mUserName;
    private String mUser_ID;

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

    public User(String userName, String user_ID) {
        mUserName = userName;
        mUser_ID = user_ID;
    }
}
