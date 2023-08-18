package com.akretsev.jdbcstudy.view;

import com.akretsev.jdbcstudy.controller.SpecialtyController;
import com.akretsev.jdbcstudy.model.Specialty;
import lombok.RequiredArgsConstructor;

import java.util.Scanner;

import static com.akretsev.jdbcstudy.model.Specialty.printSpecialties;

@RequiredArgsConstructor
public class SpecialtyView {
    private final SpecialtyController specialtyService;

    public Specialty select(Scanner scanner) {
        System.out.println("Select specialty by id (input only number):");
        printSpecialties();
        int specialtyId = scanner.nextInt();
        Specialty specialty = specialtyService.getById(specialtyId);
        System.out.println("You entered - " + specialty);

        return specialty;
    }

    public void create(Scanner scanner) {
        System.out.println("Input specialty name");
        scanner.nextLine();
        String name = scanner.nextLine().trim().toLowerCase();
        if (name.length() > 1) {
            String nameCap = name.substring(0, 1).toUpperCase() + name.substring(1);
            Specialty newSpecialty = specialtyService.create(nameCap);
            System.out.println("You have created a specialty:");
            System.out.println(newSpecialty);
        } else {
            System.out.println("Too short name.");
        }
    }

    public void delete(Scanner scanner) {
        System.out.println("Specialty list:");
        printSpecialties();
        System.out.println("Input id deleted specialty.");
        int deletedSpecialtyId = scanner.nextInt();
        Specialty deletedSpecialty = specialtyService.getById(deletedSpecialtyId);
        System.out.println("Specialty id=" + deletedSpecialtyId + " (" + deletedSpecialty + ")" + " will deleted.");
        System.out.println(deletedSpecialty);
        System.out.println("To confirm deletion enter id of specialty for his deleted again");
        int deletedSpecialtyIdAgain = scanner.nextInt();
        if (deletedSpecialtyIdAgain == deletedSpecialtyId) {
            specialtyService.deleteById(deletedSpecialtyId);
            System.out.println("Specialty id=" + deletedSpecialtyId + " was deleted.");
        } else {
            System.out.println("Deletion not confirmed");
        }
    }

}
