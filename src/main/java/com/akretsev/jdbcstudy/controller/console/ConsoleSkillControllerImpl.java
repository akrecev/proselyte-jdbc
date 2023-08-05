package com.akretsev.jdbcstudy.controller.console;

import com.akretsev.jdbcstudy.controller.SkillController;
import com.akretsev.jdbcstudy.model.Skill;
import com.akretsev.jdbcstudy.service.SkillService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ConsoleSkillControllerImpl implements SkillController {
    private final SkillService skillService;

    @Override
    public Skill create(String name) {
        Skill skill = Skill.builder()
                .name(name)
                .build();

        return skillService.create(skill);
    }

    @Override
    public Skill getById(Integer id) {
        return skillService.getById(id);
    }

    @Override
    public List<Skill> getAll() {
        return skillService.getAll();
    }

    @Override
    public Skill update(Integer id, String name) {
        Skill updatedSkill = skillService.getById(id);
        updatedSkill.setName(name);

        return skillService.update(updatedSkill);
    }

    @Override
    public void deleteById(Integer id) {
        skillService.deleteById(id);
    }
}
