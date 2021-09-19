package com.codegym.repository;

import java.util.List;

public interface IRepository<E> {
    List<E> findAll();
    E findById(Long id);
    void save(E e);
    void remove(Long id);
}
