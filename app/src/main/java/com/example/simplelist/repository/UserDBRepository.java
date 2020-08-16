package com.example.simplelist.repository;

import com.example.simplelist.model.User;

import java.util.List;
import java.util.UUID;

public class UserDBRepository implements IRepository<User> {
    @Override
    public List<User> getList() {
        return null;
    }

    @Override
    public void setList(List<User> list) {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void insert(User user) {

    }

    @Override
    public User get(UUID i) {
        return null;
    }
}
