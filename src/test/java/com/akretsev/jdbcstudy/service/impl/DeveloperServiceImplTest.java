package com.akretsev.jdbcstudy.service.impl;

import com.akretsev.jdbcstudy.exception.DataNotFoundException;
import com.akretsev.jdbcstudy.model.Developer;
import com.akretsev.jdbcstudy.model.Skill;
import com.akretsev.jdbcstudy.model.Specialty;
import com.akretsev.jdbcstudy.model.Status;
import com.akretsev.jdbcstudy.repository.DeveloperRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeveloperServiceImplTest {
    @Mock
    DeveloperRepository developerRepository;

    @InjectMocks
    DeveloperServiceImpl developerService;

    Skill testSkill = new Skill(10, "Test skill");
    Specialty testSpecialty = new Specialty(10, "Test specialty");

    Developer testDeveloper = new Developer(
            10L,
            "testDeveloperFirstName",
            "testDeveloperLastName",
            Collections.singletonList(testSkill),
            testSpecialty,
            Status.ACTIVE
    );

    @Test
    void testCreate() {
        when(developerRepository.save(any(Developer.class)))
                .thenReturn(testDeveloper);

        Developer actualTestDeveloper = developerService.create(testDeveloper);

        assertNotNull(actualTestDeveloper);
        assertEquals(testDeveloper.getFirstName(), actualTestDeveloper.getFirstName());
        assertEquals(testDeveloper.getLastName(), actualTestDeveloper.getLastName());
        assertEquals(testDeveloper.getSkills(), actualTestDeveloper.getSkills());
        assertEquals(testDeveloper.getSpecialty(), actualTestDeveloper.getSpecialty());
        assertEquals(testDeveloper.getStatus(), actualTestDeveloper.getStatus());
        verify(developerRepository, times(1)).save(any());
    }

    @Test
    void testGetByIdOk() {
        when(developerRepository.findById(testDeveloper.getId()))
                .thenReturn(Optional.ofNullable(testDeveloper));

        Developer actualTestDeveloper = developerService.getById(testDeveloper.getId());

        assertNotNull(actualTestDeveloper);
        assertEquals(testDeveloper.getFirstName(), actualTestDeveloper.getFirstName());
        assertEquals(testDeveloper.getLastName(), actualTestDeveloper.getLastName());
        assertEquals(testDeveloper.getSkills(), actualTestDeveloper.getSkills());
        assertEquals(testDeveloper.getSpecialty(), actualTestDeveloper.getSpecialty());
        assertEquals(testDeveloper.getStatus(), actualTestDeveloper.getStatus());
        verify(developerRepository, times(1)).findById(anyLong());
    }

    @Test
    void testGetByIdNotFound() {
        when(developerRepository.findById(testDeveloper.getId()))
                .thenThrow(new DataNotFoundException("Developer id=" + testDeveloper.getId() + " not found."));

        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
                () -> developerService.getById(testDeveloper.getId()));

        assertEquals("Developer id=10 not found.", exception.getMessage());
        verify(developerRepository, times(1)).findById(anyLong());
    }

    @Test
    void testGetAllOk() {
        when(developerRepository.findAll())
                .thenReturn(Collections.singletonList(testDeveloper));

        List<Developer> developerList = Collections.singletonList(testDeveloper);
        List<Developer> actualDeveloperList = developerService.getAll();

        assertNotNull(actualDeveloperList);
        assertEquals(developerList.size(), actualDeveloperList.size());
        assertEquals(developerList.get(0), actualDeveloperList.get(0));
        verify(developerRepository, times(1)).findAll();
    }

    @Test
    void testGetAllEmpty() {
        when(developerRepository.findAll())
                .thenReturn(Collections.emptyList());

        List<Developer> actualDeveloperList = developerRepository.findAll();

        assertNotNull(actualDeveloperList);
        assertEquals(0, actualDeveloperList.size());
        verify(developerRepository, times(1)).findAll();
    }

    @Test
    void testUpdate() {
        when(developerRepository.update(any(Developer.class)))
                .thenReturn(testDeveloper);

        Developer actualTestDeveloper = developerService.update(testDeveloper);

        assertNotNull(actualTestDeveloper);
        assertEquals(testDeveloper.getId(), actualTestDeveloper.getId());
        assertEquals(testDeveloper.getFirstName(), actualTestDeveloper.getFirstName());
        assertEquals(testDeveloper.getLastName(), actualTestDeveloper.getLastName());
        assertEquals(testDeveloper.getSkills(), actualTestDeveloper.getSkills());
        assertEquals(testDeveloper.getSpecialty(), actualTestDeveloper.getSpecialty());
        assertEquals(testDeveloper.getStatus(), actualTestDeveloper.getStatus());
        verify(developerRepository, times(1)).update(any());
    }

}