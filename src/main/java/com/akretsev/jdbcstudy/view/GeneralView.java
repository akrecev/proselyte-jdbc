package com.akretsev.jdbcstudy.view;

import com.akretsev.jdbcstudy.model.Developer;
import com.akretsev.jdbcstudy.model.Skill;
import com.akretsev.jdbcstudy.model.Specialty;

import java.util.List;

import static com.akretsev.jdbcstudy.context.ApplicationContext.*;

public class GeneralView {

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
        int userInput = getScanner().nextInt();
        while (userInput != 0) {
            switch (userInput) {
                case 1:
                    System.out.println("Input first name (without spaces):");
                    String firstName = getScanner().next();
                    System.out.println("You entered - " + firstName);
                    System.out.println("Input last name (without spaces):");
                    String lastName = getScanner().next();
                    System.out.println("You entered - " + lastName);

                    List<Skill> skills = getSkillView().select(getScanner());
                    Specialty specialty = getSpecialtyView().select(getScanner());

                    Developer developer = getDeveloperController().create(firstName, lastName, skills, specialty);
                    System.out.println("Saved developer: \n" + developer);
                    break;

                case 2:
                    System.out.println("List of all developers:");
                    System.out.println(getDeveloperController().getAll());
                    break;

                case 3:
                    System.out.println("Input id of developer:");
                    long developerId = getScanner().nextLong();
                    System.out.println(getDeveloperController().getById(developerId));
                    break;

                case 4:
                    System.out.println("Input id developer for updated");
                    long updatedId = getScanner().nextLong();
                    getDeveloperController().getById(updatedId);
                    System.out.println("Input first name:");
                    String updatedFirstName = getScanner().next();
                    System.out.println("You entered - " + updatedFirstName);
                    System.out.println("Input last name:");
                    String updatedLastName = getScanner().next();
                    System.out.println("You entered - " + updatedLastName);
                    List<Skill> updatedSkills = getSkillView().select(getScanner());
                    Specialty updatedSpecialty = getSpecialtyView().select(getScanner());
                    Developer updatedDeveloper = getDeveloperController().update(
                            updatedId, updatedFirstName, updatedLastName, updatedSkills, updatedSpecialty
                    );
                    System.out.println("Updated developer: \n" + updatedDeveloper);
                    break;

                case 5:
                    System.out.println("Input id of developer for his deleted:");
                    long deletedDeveloperId = getScanner().nextLong();
                    Developer deletedDeveloper = getDeveloperController().getById(deletedDeveloperId);
                    System.out.println("Developer id=" + deletedDeveloperId + " will deleted.");
                    System.out.println(deletedDeveloper);
                    System.out.println("To confirm deletion enter id of developer for his deleted again");
                    long deletedDeveloperIdAgain = getScanner().nextLong();
                    if (deletedDeveloperIdAgain == deletedDeveloperId) {
                        getDeveloperController().deleteById(deletedDeveloperId);
                        System.out.println("Developer id=" + deletedDeveloperId + " was deleted.");
                    } else {
                        System.out.println("Deletion not confirmed");
                    }

                    break;

                case 6:
                    getSkillView().create(getScanner());

                    break;

                case 7:
                    getSkillView().delete(getScanner());

                    break;

                case 8:
                    getSpecialtyView().create(getScanner());

                    break;

                case 9:
                    getSpecialtyView().delete(getScanner());

                    break;

                default:
                    System.out.println("You entered an invalid command");
            }
            printMenu();
            if (getScanner().hasNextInt()) {
                userInput = getScanner().nextInt();
            }
        }
    }

}
