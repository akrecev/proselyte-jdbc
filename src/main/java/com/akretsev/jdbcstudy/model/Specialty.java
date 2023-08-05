package com.akretsev.jdbcstudy.model;

import com.akretsev.jdbcstudy.repository.jdbc.JdbcSpecialtyRepositoryImpl;
import com.akretsev.jdbcstudy.service.SpecialtyService;
import com.akretsev.jdbcstudy.service.impl.SpecialtyServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Specialty {
    private Integer id;
    private String name;

    @Override
    public String toString() {
        return name;
    }

    public static void printSpecialties() {
        SpecialtyService specialtyService = new SpecialtyServiceImpl(new JdbcSpecialtyRepositoryImpl());
        List<Specialty> specialties = specialtyService.getAll();
        for (Specialty specialty : specialties) {
            System.out.println(specialty.getId() + " - " + specialty.getName());
        }
    }
}
