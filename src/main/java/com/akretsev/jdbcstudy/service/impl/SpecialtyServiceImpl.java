package com.akretsev.jdbcstudy.service.impl;

import com.akretsev.jdbcstudy.model.Specialty;
import com.akretsev.jdbcstudy.repository.SpecialtyRepository;
import com.akretsev.jdbcstudy.service.SpecialtyService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class SpecialtyServiceImpl implements SpecialtyService {
    private final SpecialtyRepository specialtyRepository;

    @Override
    public Specialty create(Specialty specialty) {
        return specialtyRepository.save(specialty);
    }

    @Override
    public Specialty getById(Integer id) {
        return specialtyRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Specialty id=" + id + " not found.")
        );
    }

    @Override
    public List<Specialty> getAll() {
        return specialtyRepository.findAll();
    }

    @Override
    public Specialty update(Specialty specialty) {
        return specialtyRepository.update(specialty);
    }

    @Override
    public void deleteById(Integer id) {
        specialtyRepository.deleteById(id);
    }
}
