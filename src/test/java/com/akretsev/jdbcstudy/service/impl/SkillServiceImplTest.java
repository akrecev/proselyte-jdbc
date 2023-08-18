package com.akretsev.jdbcstudy.service.impl;

import com.akretsev.jdbcstudy.exception.DataNotFoundException;
import com.akretsev.jdbcstudy.model.Skill;
import com.akretsev.jdbcstudy.repository.SkillRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.akretsev.jdbcstudy.utility.TestUtils.getTestSkill;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SkillServiceImplTest {

    @Mock
    SkillRepository skillRepository;

    @InjectMocks
    SkillServiceImpl skillService;

    @Test
    void testCreate() {
        when(skillRepository.save(any(Skill.class)))
                .thenReturn(getTestSkill());

        Skill actualTestSkill = skillService.create(getTestSkill());

        assertNotNull(actualTestSkill);
        assertEquals(getTestSkill().getName(), actualTestSkill.getName());
        verify(skillRepository, times(1)).save(any());
    }

    @Test
    void testGetByIdOk() {
        when(skillRepository.findById(getTestSkill().getId()))
                .thenReturn(Optional.of(getTestSkill()));

        Skill actualTestSkill = skillService.getById(getTestSkill().getId());

        assertNotNull(actualTestSkill);
        assertEquals(getTestSkill().getName(), actualTestSkill.getName());
        verify(skillRepository, times(1)).findById(anyInt());
    }

    @Test
    void testGetByIdNotFound() {
        when(skillRepository.findById(getTestSkill().getId()))
                .thenThrow(new DataNotFoundException("Skill id=" + getTestSkill().getId() + " not found."));

        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
                () -> skillService.getById(getTestSkill().getId()));

        assertEquals("Skill id=10 not found.", exception.getMessage());
        verify(skillRepository, times(1)).findById(anyInt());
    }

    @Test
    void testGetAllOk() {
        when(skillRepository.findAll())
                .thenReturn(Collections.singletonList(getTestSkill()));

        List<Skill> skillList = Collections.singletonList(getTestSkill());
        List<Skill> actualSkillList = skillService.getAll();

        assertNotNull(actualSkillList);
        assertEquals(skillList.size(), actualSkillList.size());
        assertEquals(skillList.get(0), actualSkillList.get(0));
        verify(skillRepository, times(1)).findAll();
    }

    @Test
    void testGetAllEmpty() {
        when(skillRepository.findAll())
                .thenReturn(Collections.emptyList());

        List<Skill> actualSkillList = skillRepository.findAll();

        assertNotNull(actualSkillList);
        assertEquals(0, actualSkillList.size());
        verify(skillRepository, times(1)).findAll();
    }

    @Test
    void testUpdate() {
        when(skillRepository.update(any(Skill.class)))
                .thenReturn(getTestSkill());

        Skill actualTestSkill = skillService.update(getTestSkill());

        assertNotNull(actualTestSkill);
        assertEquals(getTestSkill().getId(), actualTestSkill.getId());
        assertEquals(getTestSkill().getName(), actualTestSkill.getName());
        verify(skillRepository, times(1)).update(any());
    }

}