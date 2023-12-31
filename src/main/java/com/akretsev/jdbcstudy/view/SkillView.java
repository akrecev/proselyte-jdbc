package com.akretsev.jdbcstudy.view;

import com.akretsev.jdbcstudy.controller.SkillController;
import com.akretsev.jdbcstudy.model.Skill;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static com.akretsev.jdbcstudy.model.Skill.printSkills;

@RequiredArgsConstructor
public class SkillView {
    private final SkillController skillController;

    public List<Skill> select(Scanner scanner) {
        List<Skill> skills = new ArrayList<>();
        System.out.println("Select skills by id (write the list in one line separated by commas\",\" without spaces):");
        printSkills();
        scanner.nextLine();
        String skillListString = scanner.nextLine();
        List<String> skillList = Arrays.asList(skillListString.split(",")) ;

        List<Integer> skillIds = skillList.stream().map(Integer::parseInt).collect(Collectors.toList());

        for (Integer skillId : skillIds) {
            skills.add(skillController.getById(skillId));
        }
        System.out.println("You entered - " + skills);

        return skills;
    }

    public void create(Scanner scanner) {
        System.out.println("Input skill name");
        String name = scanner.next().trim().toLowerCase();
        if (name.length() > 1) {
            String nameCap = name.substring(0, 1).toUpperCase() + name.substring(1);
            Skill newSkill = skillController.create(nameCap);
            System.out.println("You have created a skill:");
            System.out.println(newSkill);
        } else {
            System.out.println("Too short name.");
        }
    }

    public void delete(Scanner scanner) {
        System.out.println("Skills list:");
        printSkills();
        System.out.println("Input id deleted skill.");
        int deletedSkillId = scanner.nextInt();
        Skill deletedSkill = skillController.getById(deletedSkillId);
        System.out.println("Skill id=" + deletedSkillId + " (" + deletedSkill + ")" + " will deleted.");
        System.out.println(deletedSkill);
        System.out.println("To confirm deletion enter id of skill for his deleted again");
        int deletedSkillIdAgain = scanner.nextInt();
        if (deletedSkillIdAgain == deletedSkillId) {
            skillController.deleteById(deletedSkillId);
            System.out.println("Skill id=" + deletedSkillId + " was deleted.");
        } else {
            System.out.println("Deletion not confirmed");
        }
    }

}
