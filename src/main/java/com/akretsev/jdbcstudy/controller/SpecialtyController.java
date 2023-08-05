package com.akretsev.jdbcstudy.controller;

import com.akretsev.jdbcstudy.model.Specialty;

import java.util.List;

public interface SpecialtyController {
    Specialty create(String name);

    Specialty getById(Integer id);

    List<Specialty> getAll();

    Specialty update(Integer id, String name);

    void deleteById(Integer id);
}
