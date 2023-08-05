package com.akretsev.jdbcstudy.controller;

import com.akretsev.jdbcstudy.model.Skill;

import java.util.List;

public interface SkillController {
    Skill create(String name);

    Skill getById(Integer id);

    List<Skill> getAll();

    Skill update(Integer id, String name);

    void deleteById(Integer id);
}
