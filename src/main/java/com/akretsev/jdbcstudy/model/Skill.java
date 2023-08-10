package com.akretsev.jdbcstudy.model;

import com.akretsev.jdbcstudy.repository.jdbc.JdbcSkillRepositoryImpl;
import com.akretsev.jdbcstudy.service.SkillService;
import com.akretsev.jdbcstudy.service.impl.SkillServiceImpl;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "skills")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, length = 50)
    private String name;
    @ManyToMany(mappedBy = "skills")
    private List<Developer> developers;

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
