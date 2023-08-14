package com.akretsev.jdbcstudy.view;

import com.akretsev.jdbcstudy.controller.DeveloperController;
import com.akretsev.jdbcstudy.controller.console.ConsoleDeveloperControllerImpl;
import com.akretsev.jdbcstudy.model.Developer;
import com.akretsev.jdbcstudy.model.Skill;
import com.akretsev.jdbcstudy.model.Specialty;
import com.akretsev.jdbcstudy.repository.hibernate.HibernateDeveloperRepositoryImpl;
import com.akretsev.jdbcstudy.service.impl.DeveloperServiceImpl;

import java.util.List;
import java.util.Scanner;

public class GeneralView {
    private final Scanner scanner = new Scanner(System.in);
    private final DeveloperController developerController =
            new ConsoleDeveloperControllerImpl(new DeveloperServiceImpl(new HibernateDeveloperRepositoryImpl()));
    private final SkillView skillView = new SkillView();
    private final SpecialtyView specialtyView = new SpecialtyView();

    public static void printMenu() {
        System.out.println("\nChoose an action:\n" +
                "1 - add developer to list\n" +
                "2 - get all developers\n" +
                "3 - get developer by his ID\n" +
                "4 - update developer (by his ID)\n" +
                "5 - delete developer by his ID\n" +
                "6 - add developer's skill\n" +
                "7 - delete developer's skill\n" +
                "8 - add developer's specialty\n" +
                "9 - delete developer's specialty\n" +
                "0 - exit");
    }


    public void start() {

        System.out.println("Launch the application \"List of developers\"");
        printMenu();
        int userInput = scanner.nextInt();
        while (userInput != 0) {
            switch (userInput) {
                case 1:
                    System.out.println("Input first name (without spaces):");
                    String firstName = scanner.next();
                    System.out.println("You entered - " + firstName);
                    System.out.println("Input last name (without spaces):");
                    String lastName = scanner.next();
                    System.out.println("You entered - " + lastName);

                    List<Skill> skills = skillView.select(scanner);
                    Specialty specialty = specialtyView.select(scanner);

                    Developer developer = developerController.create(firstName, lastName, skills, specialty);
                    System.out.println("Saved developer: \n" + developer);
                    break;

                case 2:
                    System.out.println("List of all developers:");
                    System.out.println(developerController.getAll());
                    break;

                case 3:
                    System.out.println("Input id of developer:");
                    long developerId = scanner.nextLong();
                    System.out.println(developerController.getById(developerId));
                    break;

                case 4:
                    System.out.println("Input id developer for updated");
                    long updatedId = scanner.nextLong();
                    developerController.getById(updatedId);
                    System.out.println("Input first name:");
                    String updatedFirstName = scanner.next();
                    System.out.println("You entered - " + updatedFirstName);
                    System.out.println("Input last name:");
                    String updatedLastName = scanner.next();
                    System.out.println("You entered - " + updatedLastName);
                    List<Skill> updatedSkills = skillView.select(scanner);
                    Specialty updatedSpecialty = specialtyView.select(scanner);
                    Developer updatedDeveloper = developerController.update(
                            updatedId, updatedFirstName, updatedLastName, updatedSkills, updatedSpecialty
                    );
                    System.out.println("Updated developer: \n" + updatedDeveloper);
                    break;

                case 5:
                    System.out.println("Input id of developer for his deleted:");
                    long deletedDeveloperId = scanner.nextLong();
                    Developer deletedDeveloper = developerController.getById(deletedDeveloperId);
                    System.out.println("Developer id=" + deletedDeveloperId + " will deleted.");
                    System.out.println(deletedDeveloper);
                    System.out.println("To confirm deletion enter id of developer for his deleted again");
                    long deletedDeveloperIdAgain = scanner.nextLong();
                    if (deletedDeveloperIdAgain == deletedDeveloperId) {
                        developerController.deleteById(deletedDeveloperId);
                        System.out.println("Developer id=" + deletedDeveloperId + " was deleted.");
                    } else {
                        System.out.println("Deletion not confirmed");
                    }

                    break;

                case 6:
                    skillView.create(scanner);

                    break;

                case 7:
                    skillView.delete(scanner);

                    break;

                case 8:
                    specialtyView.create(scanner);

                    break;

                case 9:
                    specialtyView.delete(scanner);

                    break;

                default:
                    System.out.println("You entered an invalid command");
            }
            printMenu();
            if (scanner.hasNextInt()){
                userInput = scanner.nextInt();
            }
        }
    }

}
