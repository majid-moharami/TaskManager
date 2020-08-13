package com.example.simplelist.repository;

import java.util.List;
import java.util.UUID;

public interface IRepository<E> {
    List<E> getList();
    void setList(List<E> list);
    String getName();
    void insert(E e);
    E get(UUID i);

}
