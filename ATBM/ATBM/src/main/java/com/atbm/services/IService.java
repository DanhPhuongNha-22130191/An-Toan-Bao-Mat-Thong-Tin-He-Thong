package com.atbm.services;


import java.util.List;

public interface IService<T, ID> {
    boolean insert(T entity);

    T getById(ID id);

    List<T> getAll();

    boolean delete(ID id);

    boolean update(T entity); // Thêm cập nhật


}

