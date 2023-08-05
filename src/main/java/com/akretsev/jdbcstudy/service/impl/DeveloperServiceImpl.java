package com.akretsev.jdbcstudy.service.impl;

import com.akretsev.jdbcstudy.exception.DataNotFoundException;
import com.akretsev.jdbcstudy.model.Developer;
import com.akretsev.jdbcstudy.model.Status;
import com.akretsev.jdbcstudy.repository.DeveloperRepository;
import com.akretsev.jdbcstudy.service.DeveloperService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class DeveloperServiceImpl implements DeveloperService {
    private final DeveloperRepository developerRepository;

    @Override
    public Developer create(Developer developer) {
        return developerRepository.save(developer);
    }

    @Override
    public Developer getById(Long id) {
        Developer developer = developerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Developer id=" + id + " not found"));

        if (developer.getStatus().equals(Status.DELETED)) {
            throw new DataNotFoundException("Developer is deleted. " + developer);
        }

        return developer;
    }

    @Override
    public List<Developer> getAll() {
        return developerRepository.findAll();
    }

    @Override
    public Developer update(Developer developer) {
        developer = developerRepository.update(developer);

        if (developer.getStatus().equals(Status.DELETED)) {
            throw new DataNotFoundException("Developer is deleted.");
        }

        return developer;
    }

    @Override
    public void deleteById(Long id) {
        Developer deletedDeveloper = getById(id);
        deletedDeveloper.setStatus(Status.DELETED);
        developerRepository.update(deletedDeveloper);
    }

}
