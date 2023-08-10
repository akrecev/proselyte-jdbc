package com.akretsev.jdbcstudy.service.impl;

import com.akretsev.jdbcstudy.exception.DataNotFoundException;
import com.akretsev.jdbcstudy.model.Specialty;
import com.akretsev.jdbcstudy.repository.SpecialtyRepository;
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
class SpecialtyServiceImplTest {
    @Mock
    SpecialtyRepository specialtyRepository;

    @InjectMocks
    SpecialtyServiceImpl specialtyService;

    Specialty testSpecialty = Specialty.builder().id(10).name("Test specialty").build();

    @Test
    void testCreate() {
        when(specialtyRepository.save(any(Specialty.class)))
                .thenReturn(testSpecialty);

        Specialty actualTestSpecialty = specialtyService.create(testSpecialty);

        assertNotNull(actualTestSpecialty);
        assertEquals(testSpecialty.getName(), actualTestSpecialty.getName());
        verify(specialtyRepository, times(1)).save(any());
    }

    @Test
    void testGetByIdOk() {
        when(specialtyRepository.findById(testSpecialty.getId()))
                .thenReturn(Optional.ofNullable(testSpecialty));

        Specialty actualTestSpecialty = specialtyService.getById(testSpecialty.getId());

        assertNotNull(actualTestSpecialty);
        assertEquals(testSpecialty.getName(), actualTestSpecialty.getName());
        verify(specialtyRepository, times(1)).findById(anyInt());
    }

    @Test
    void testGetByIdNotFound() {
        when(specialtyRepository.findById(testSpecialty.getId()))
                .thenThrow(new DataNotFoundException("Specialty id=" + testSpecialty.getId() + " not found."));

        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
                () -> specialtyService.getById(testSpecialty.getId()));

        assertEquals("Specialty id=10 not found.", exception.getMessage());
        verify(specialtyRepository, times(1)).findById(anyInt());
    }

    @Test
    void testGetAllOk() {
        when(specialtyRepository.findAll())
                .thenReturn(Collections.singletonList(testSpecialty));

        List<Specialty> SpecialtyList = Collections.singletonList(testSpecialty);
        List<Specialty> actualSpecialtyList = specialtyService.getAll();

        assertNotNull(actualSpecialtyList);
        assertEquals(SpecialtyList.size(), actualSpecialtyList.size());
        assertEquals(SpecialtyList.get(0), actualSpecialtyList.get(0));
        verify(specialtyRepository, times(1)).findAll();
    }

    @Test
    void testGetAllEmpty() {
        when(specialtyRepository.findAll())
                .thenReturn(Collections.emptyList());

        List<Specialty> actualSpecialtyList = specialtyRepository.findAll();

        assertNotNull(actualSpecialtyList);
        assertEquals(0, actualSpecialtyList.size());
        verify(specialtyRepository, times(1)).findAll();
    }

    @Test
    void testUpdate() {
        when(specialtyRepository.update(any(Specialty.class)))
                .thenReturn(testSpecialty);

        Specialty actualTestSpecialty = specialtyService.update(testSpecialty);

        assertNotNull(actualTestSpecialty);
        assertEquals(testSpecialty.getId(), actualTestSpecialty.getId());
        assertEquals(testSpecialty.getName(), actualTestSpecialty.getName());
        verify(specialtyRepository, times(1)).update(any());
    }
}