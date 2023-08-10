package com.akretsev.jdbcstudy.model;

import com.akretsev.jdbcstudy.repository.jdbc.JdbcSpecialtyRepositoryImpl;
import com.akretsev.jdbcstudy.service.SpecialtyService;
import com.akretsev.jdbcstudy.service.impl.SpecialtyServiceImpl;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "specialties")
public class Specialty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, length = 50)
    private String name;
    @OneToMany(mappedBy = "specialty")
    private List<Developer> developers;

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
