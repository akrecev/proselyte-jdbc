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

import static com.akretsev.jdbcstudy.utility.TestUtils.getTestSpecialty;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialtyServiceImplTest {
    @Mock
    SpecialtyRepository specialtyRepository;

    @InjectMocks
    SpecialtyServiceImpl specialtyService;

    @Test
    void testCreate() {
        when(specialtyRepository.save(any(Specialty.class)))
                .thenReturn(getTestSpecialty());

        Specialty actualTestSpecialty = specialtyService.create(getTestSpecialty());

        assertNotNull(actualTestSpecialty);
        assertEquals(getTestSpecialty().getName(), actualTestSpecialty.getName());
        verify(specialtyRepository, times(1)).save(any());
    }

    @Test
    void testGetByIdOk() {
        when(specialtyRepository.findById(getTestSpecialty().getId()))
                .thenReturn(Optional.of(getTestSpecialty()));

        Specialty actualTestSpecialty = specialtyService.getById(getTestSpecialty().getId());

        assertNotNull(actualTestSpecialty);
        assertEquals(getTestSpecialty().getName(), actualTestSpecialty.getName());
        verify(specialtyRepository, times(1)).findById(anyInt());
    }

    @Test
    void testGetByIdNotFound() {
        when(specialtyRepository.findById(getTestSpecialty().getId()))
                .thenThrow(new DataNotFoundException("Specialty id=" + getTestSpecialty().getId() + " not found."));

        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
                () -> specialtyService.getById(getTestSpecialty().getId()));

        assertEquals("Specialty id=10 not found.", exception.getMessage());
        verify(specialtyRepository, times(1)).findById(anyInt());
    }

    @Test
    void testGetAllOk() {
        when(specialtyRepository.findAll())
                .thenReturn(Collections.singletonList(getTestSpecialty()));

        List<Specialty> SpecialtyList = Collections.singletonList(getTestSpecialty());
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
                .thenReturn(getTestSpecialty());

        Specialty actualTestSpecialty = specialtyService.update(getTestSpecialty());

        assertNotNull(actualTestSpecialty);
        assertEquals(getTestSpecialty().getId(), actualTestSpecialty.getId());
        assertEquals(getTestSpecialty().getName(), actualTestSpecialty.getName());
        verify(specialtyRepository, times(1)).update(any());
    }
}