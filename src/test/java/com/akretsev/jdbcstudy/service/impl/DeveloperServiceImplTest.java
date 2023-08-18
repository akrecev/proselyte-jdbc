package com.akretsev.jdbcstudy.service.impl;

import com.akretsev.jdbcstudy.exception.DataNotFoundException;
import com.akretsev.jdbcstudy.model.Developer;
import com.akretsev.jdbcstudy.repository.DeveloperRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.akretsev.jdbcstudy.utility.TestUtils.getTestDeveloper;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeveloperServiceImplTest {
    @Mock
    DeveloperRepository developerRepository;

    @InjectMocks
    DeveloperServiceImpl developerService;

    @Test
    void testCreate() {
        when(developerRepository.save(any(Developer.class)))
                .thenReturn(getTestDeveloper());

        Developer actualTestDeveloper = developerService.create(getTestDeveloper());

        assertNotNull(actualTestDeveloper);
        assertEquals(getTestDeveloper().getFirstName(), actualTestDeveloper.getFirstName());
        assertEquals(getTestDeveloper().getLastName(), actualTestDeveloper.getLastName());
        assertEquals(getTestDeveloper().getSkills(), actualTestDeveloper.getSkills());
        assertEquals(getTestDeveloper().getSpecialty(), actualTestDeveloper.getSpecialty());
        assertEquals(getTestDeveloper().getStatus(), actualTestDeveloper.getStatus());
        verify(developerRepository, times(1)).save(any());
    }

    @Test
    void testGetByIdOk() {
        when(developerRepository.findById(getTestDeveloper().getId()))
                .thenReturn(Optional.of(getTestDeveloper()));

        Developer actualTestDeveloper = developerService.getById(getTestDeveloper().getId());

        assertNotNull(actualTestDeveloper);
        assertEquals(getTestDeveloper().getFirstName(), actualTestDeveloper.getFirstName());
        assertEquals(getTestDeveloper().getLastName(), actualTestDeveloper.getLastName());
        assertEquals(getTestDeveloper().getSkills(), actualTestDeveloper.getSkills());
        assertEquals(getTestDeveloper().getSpecialty(), actualTestDeveloper.getSpecialty());
        assertEquals(getTestDeveloper().getStatus(), actualTestDeveloper.getStatus());
        verify(developerRepository, times(1)).findById(anyLong());
    }

    @Test
    void testGetByIdNotFound() {
        when(developerRepository.findById(getTestDeveloper().getId()))
                .thenThrow(new DataNotFoundException("Developer id=" + getTestDeveloper().getId() + " not found."));

        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
                () -> developerService.getById(getTestDeveloper().getId()));

        assertEquals("Developer id=10 not found.", exception.getMessage());
        verify(developerRepository, times(1)).findById(anyLong());
    }

    @Test
    void testGetAllOk() {
        when(developerRepository.findAll())
                .thenReturn(Collections.singletonList(getTestDeveloper()));

        List<Developer> developerList = Collections.singletonList(getTestDeveloper());
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
                .thenReturn(getTestDeveloper());

        Developer actualTestDeveloper = developerService.update(getTestDeveloper());

        assertNotNull(actualTestDeveloper);
        assertEquals(getTestDeveloper().getId(), actualTestDeveloper.getId());
        assertEquals(getTestDeveloper().getFirstName(), actualTestDeveloper.getFirstName());
        assertEquals(getTestDeveloper().getLastName(), actualTestDeveloper.getLastName());
        assertEquals(getTestDeveloper().getSkills(), actualTestDeveloper.getSkills());
        assertEquals(getTestDeveloper().getSpecialty(), actualTestDeveloper.getSpecialty());
        assertEquals(getTestDeveloper().getStatus(), actualTestDeveloper.getStatus());
        verify(developerRepository, times(1)).update(any());
    }

}