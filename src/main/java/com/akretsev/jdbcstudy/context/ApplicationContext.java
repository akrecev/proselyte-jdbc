package com.akretsev.jdbcstudy.context;

import com.akretsev.jdbcstudy.controller.DeveloperController;
import com.akretsev.jdbcstudy.controller.console.ConsoleDeveloperControllerImpl;
import com.akretsev.jdbcstudy.controller.console.ConsoleSkillControllerImpl;
import com.akretsev.jdbcstudy.controller.console.ConsoleSpecialtyControllerImpl;
import com.akretsev.jdbcstudy.repository.hibernate.HibernateDeveloperRepositoryImpl;
import com.akretsev.jdbcstudy.repository.hibernate.HibernateSkillRepositoryImpl;
import com.akretsev.jdbcstudy.repository.hibernate.HibernateSpecialtyRepositoryImpl;
import com.akretsev.jdbcstudy.service.impl.DeveloperServiceImpl;
import com.akretsev.jdbcstudy.service.impl.SkillServiceImpl;
import com.akretsev.jdbcstudy.service.impl.SpecialtyServiceImpl;
import com.akretsev.jdbcstudy.view.SkillView;
import com.akretsev.jdbcstudy.view.SpecialtyView;

import java.util.Scanner;

public class ApplicationContext {
    private static Scanner scanner;
    private static DeveloperController developerController;
    private static SkillView skillView;
    private static SpecialtyView specialtyView;

    public static synchronized Scanner getScanner() {
        if (scanner == null) {
            scanner = new Scanner(System.in);
        }

        return scanner;
    }

    public static synchronized DeveloperController getDeveloperController() {
        if (developerController == null) {
            developerController = new ConsoleDeveloperControllerImpl(
                    new DeveloperServiceImpl(
                            new HibernateDeveloperRepositoryImpl()
                    )
            );
        }

        return developerController;
    }

    public static synchronized SkillView getSkillView() {
        if (skillView == null) {
            skillView = new SkillView(
                    new ConsoleSkillControllerImpl(
                            new SkillServiceImpl(
                                    new HibernateSkillRepositoryImpl()
                            )
                    )
            );
        }

        return skillView;
    }

    public static synchronized SpecialtyView getSpecialtyView() {
        if (specialtyView == null) {
            specialtyView = new SpecialtyView(
                    new ConsoleSpecialtyControllerImpl(
                            new SpecialtyServiceImpl(
                                    new HibernateSpecialtyRepositoryImpl()
                            )
                    )
            );
        }

        return specialtyView;
    }
}
