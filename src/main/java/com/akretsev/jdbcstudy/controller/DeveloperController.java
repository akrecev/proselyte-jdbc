package com.akretsev.jdbcstudy.controller;

import com.akretsev.jdbcstudy.model.Developer;
import com.akretsev.jdbcstudy.model.Skill;
import com.akretsev.jdbcstudy.model.Specialty;

import java.util.List;

public interface DeveloperController{
    Developer create(String firstName, String lastName, List<Skill> skills, Specialty specialty);

    Developer getById(Long id);

    List<Developer> getAll();

    Developer update(Long id, String firstName, String lastName, List<Skill> skills, Specialty specialty);

    void deleteById(Long id);
}
