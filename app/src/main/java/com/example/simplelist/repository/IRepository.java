package com.example.simplelist.repository;

import java.util.List;

public interface IRepository<E> {
    List<E> getList();
    void setList(List<E> list);
    String getName();
    void insert(E e);
    E get(int i);
}
