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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SkillServiceImplTest {

    @Mock
    SkillRepository skillRepository;

    @InjectMocks
    SkillServiceImpl skillService;

    Skill testSkill = new Skill(10, "Test skill");

    @Test
    void testCreate() {
        when(skillRepository.save(any(Skill.class)))
                .thenReturn(testSkill);

        Skill actualTestSkill = skillService.create(testSkill);

        assertNotNull(actualTestSkill);
        assertEquals(testSkill.getName(), actualTestSkill.getName());
        verify(skillRepository, times(1)).save(any());
    }

    @Test
    void testGetByIdOk() {
        when(skillRepository.findById(testSkill.getId()))
                .thenReturn(Optional.ofNullable(testSkill));

        Skill actualTestSkill = skillService.getById(testSkill.getId());

        assertNotNull(actualTestSkill);
        assertEquals(testSkill.getName(), actualTestSkill.getName());
        verify(skillRepository, times(1)).findById(anyInt());
    }

    @Test
    void testGetByIdNotFound() {
        when(skillRepository.findById(testSkill.getId()))
                .thenThrow(new DataNotFoundException("Skill id=" + testSkill.getId() + " not found."));

        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
                () -> skillService.getById(testSkill.getId()));

        assertEquals("Skill id=10 not found.", exception.getMessage());
        verify(skillRepository, times(1)).findById(anyInt());
    }

    @Test
    void testGetAllOk() {
        when(skillRepository.findAll())
                .thenReturn(Collections.singletonList(testSkill));

        List<Skill> skillList = Collections.singletonList(testSkill);
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
                .thenReturn(testSkill);

        Skill actualTestSkill = skillService.update(testSkill);

        assertNotNull(actualTestSkill);
        assertEquals(testSkill.getId(), actualTestSkill.getId());
        assertEquals(testSkill.getName(), actualTestSkill.getName());
        verify(skillRepository, times(1)).update(any());
    }

}