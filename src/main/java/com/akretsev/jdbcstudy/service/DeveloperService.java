package com.akretsev.jdbcstudy.service;

import com.akretsev.jdbcstudy.model.Developer;

import java.util.List;

public interface DeveloperService {
    Developer create(Developer developer);

    Developer getById(Long id);

    List<Developer> getAll();

    Developer update(Developer developer);

    void deleteById(Long id);
}
