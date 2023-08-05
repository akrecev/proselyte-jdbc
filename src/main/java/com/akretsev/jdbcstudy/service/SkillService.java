package com.akretsev.jdbcstudy.service;

import com.akretsev.jdbcstudy.model.Skill;

import java.util.List;

public interface SkillService {
    Skill create(Skill skill);

    Skill getById(Integer id);

    List<Skill> getAll();

    Skill update(Skill skill);

    void deleteById(Integer id);
}
