package com.akretsev.jdbcstudy.service;

import com.akretsev.jdbcstudy.model.Specialty;

import java.util.List;

public interface SpecialtyService {
    Specialty create(Specialty specialty);

    Specialty getById(Integer id);

    List<Specialty> getAll();

    Specialty update(Specialty specialty);

    void deleteById(Integer id);
}
