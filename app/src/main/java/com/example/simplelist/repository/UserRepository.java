package com.example.simplelist.repository;

import com.example.simplelist.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserRepository implements IRepository<User> {

    private static UserRepository mUserRepository;
    private List<User> mUserList = new ArrayList<>();
    private UserRepository() {
    }

    public static UserRepository getInstance(){
        if (mUserRepository==null)
            mUserRepository = new UserRepository();
        return mUserRepository;
    }

    @Override
    public List<User> getList() {
        return mUserList;
    }

    @Override
    public void setList(List<User> list) {
        mUserList = list;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void insert(User user) {
        mUserList.add(user);
    }

    @Override
    public User get(UUID id) {
       return  null;
    }

    public User get(String id){
        for (int i = 0; i < mUserList.size()  ; i++) {
            if (mUserList.get(i).getUser_ID().equals(id)){
                return mUserList.get(i);
            }
        }
        return null;
    }
}
