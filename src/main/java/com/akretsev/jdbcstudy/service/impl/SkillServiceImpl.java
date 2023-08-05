package com.akretsev.jdbcstudy.service.impl;

import com.akretsev.jdbcstudy.exception.DataNotFoundException;
import com.akretsev.jdbcstudy.model.Skill;
import com.akretsev.jdbcstudy.repository.SkillRepository;
import com.akretsev.jdbcstudy.service.SkillService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {
    private final SkillRepository skillRepository;

    @Override
    public Skill create(Skill skill) {
        return skillRepository.save(skill);
    }

    @Override
    public Skill getById(Integer id) {
        return skillRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Skill id=" + id + " not found.")
        );
    }

    @Override
    public List<Skill> getAll() {
        return skillRepository.findAll();
    }

    @Override
    public Skill update(Skill skill) {
        return skillRepository.update(skill);
    }

    @Override
    public void deleteById(Integer id) {
        skillRepository.deleteById(id);
    }
}
