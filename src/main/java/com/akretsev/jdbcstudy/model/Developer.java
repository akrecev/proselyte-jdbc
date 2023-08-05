package com.akretsev.jdbcstudy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Developer {
    private Long id;
    private String firstName;
    private String lastName;
    private List<Skill> skills;
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
