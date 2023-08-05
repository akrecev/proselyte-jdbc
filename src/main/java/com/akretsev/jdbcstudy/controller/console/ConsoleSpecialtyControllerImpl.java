package com.akretsev.jdbcstudy.controller.console;

import com.akretsev.jdbcstudy.controller.SpecialtyController;
import com.akretsev.jdbcstudy.model.Specialty;
import com.akretsev.jdbcstudy.service.SpecialtyService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ConsoleSpecialtyControllerImpl implements SpecialtyController {
    private final SpecialtyService specialtyService;

    @Override
    public Specialty create(String name) {
        Specialty specialty = Specialty.builder()
                .name(name)
                .build();

        return specialtyService.create(specialty);
    }

    @Override
    public Specialty getById(Integer id) {
        return specialtyService.getById(id);
    }

    @Override
    public List<Specialty> getAll() {
        return specialtyService.getAll();
    }

    @Override
    public Specialty update(Integer id, String name) {
        Specialty updatedSpecialty = specialtyService.getById(id);
        updatedSpecialty.setName(name);

        return specialtyService.update(updatedSpecialty);
    }

    @Override
    public void deleteById(Integer id) {
        specialtyService.deleteById(id);
    }
}
