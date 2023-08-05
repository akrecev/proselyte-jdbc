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
public class DeveloperEntity {
    private Long id;
    private String firstName;
    private String lastName;
    private List<Integer> skillsIds;
    private Integer specialtyId;
    private Status status;
}
