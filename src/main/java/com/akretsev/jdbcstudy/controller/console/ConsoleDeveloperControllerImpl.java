package com.akretsev.jdbcstudy.controller.console;

import com.akretsev.jdbcstudy.controller.DeveloperController;
import com.akretsev.jdbcstudy.model.Developer;
import com.akretsev.jdbcstudy.model.Skill;
import com.akretsev.jdbcstudy.model.Specialty;
import com.akretsev.jdbcstudy.model.Status;
import com.akretsev.jdbcstudy.service.DeveloperService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ConsoleDeveloperControllerImpl implements DeveloperController {
    private final DeveloperService developerService;

    @Override
    public Developer create(String firstName, String lastName, List<Skill> skills, Specialty specialty) {
        Developer developer = Developer.builder()
                .firstName(firstName)
                .lastName(lastName)
                .skills(skills)
                .specialty(specialty)
                .status(Status.ACTIVE)
                .build();

        return developerService.create(developer);
    }

    @Override
    public Developer getById(Long id) {
        return developerService.getById(id);
    }

    @Override
    public List<Developer> getAll() {
        return developerService.getAll();
    }

    @Override
    public Developer update(Long id, String firstName, String lastName, List<Skill> skills, Specialty specialty) {
        Developer updatedDeveloper = developerService.getById(id);

        updatedDeveloper.setFirstName(firstName);
        updatedDeveloper.setLastName(lastName);
        updatedDeveloper.setSkills(skills);
        updatedDeveloper.setSpecialty(specialty);
        updatedDeveloper.setStatus(Status.ACTIVE);

        return developerService.update(updatedDeveloper);
    }

    @Override
    public void deleteById(Long id) {
        developerService.deleteById(id);
    }
}
