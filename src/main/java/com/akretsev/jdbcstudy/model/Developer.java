package com.akretsev.jdbcstudy.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "developers")
public class Developer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;
    @ManyToMany
    @JoinTable(
            name = "developer_skills",
            joinColumns = {@JoinColumn(name = "developer_id")},
            inverseJoinColumns = {@JoinColumn(name = "skill_id")}
    )
    private List<Skill> skills;
    @ManyToOne
    private Specialty specialty;
    private Status status;

    @Override
    public String toString() {
        return '\n' + "Developer" + '\n' +
                "id = " + id + ", " + '\n' +
                "firstName - " + firstName + ", " + '\n' +
                "lastName - " + lastName + ", " + '\n' +
                "skills: " + skills + ", " + '\n' +
                "specialty - " + specialty + ", " + '\n' +
                "status - " + status;
    }
}
