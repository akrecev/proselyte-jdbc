package com.akretsev.jdbcstudy.model;

import com.akretsev.jdbcstudy.repository.jdbc.JdbcSkillRepositoryImpl;
import com.akretsev.jdbcstudy.service.SkillService;
import com.akretsev.jdbcstudy.service.impl.SkillServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Skill {
    private Integer id;
    private String name;

    @Override
    public String toString() {
        return name ;
    }

    public static void printSkills() {
        SkillService skillService = new SkillServiceImpl(new JdbcSkillRepositoryImpl());
        List<Skill> skills = skillService.getAll();
        for (Skill skill : skills) {
            System.out.println(skill.getId() + " - " + skill.getName());
        }
    }
}
