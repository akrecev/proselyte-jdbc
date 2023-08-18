package com.akretsev.jdbcstudy.utility;

import com.akretsev.jdbcstudy.model.Developer;
import com.akretsev.jdbcstudy.model.Skill;
import com.akretsev.jdbcstudy.model.Specialty;
import com.akretsev.jdbcstudy.model.Status;

import java.util.Collections;

public class TestUtils {
    private final static Skill testSkill = Skill.builder().id(10).name("Test skill").build();
    private final static Specialty testSpecialty = Specialty.builder().id(10).name("Test specialty").build();

    private final static Developer testDeveloper = new Developer(
            10L,
            "testDeveloperFirstName",
            "testDeveloperLastName",
            Collections.singletonList(testSkill),
            testSpecialty,
            Status.ACTIVE
    );

    public static Developer getTestDeveloper() {
        return testDeveloper;
    }

    public static Skill getTestSkill() {
        return testSkill;
    }

    public static Specialty getTestSpecialty() {
        return testSpecialty;
    }
}
