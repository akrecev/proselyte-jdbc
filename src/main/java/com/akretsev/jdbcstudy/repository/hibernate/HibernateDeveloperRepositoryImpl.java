package com.akretsev.jdbcstudy.repository.hibernate;

import com.akretsev.jdbcstudy.exception.DataNotFoundException;
import com.akretsev.jdbcstudy.model.Developer;
import com.akretsev.jdbcstudy.repository.DeveloperRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

import static com.akretsev.jdbcstudy.utility.HibernateUtil.getSessionFactory;

public class HibernateDeveloperRepositoryImpl implements DeveloperRepository {

    @Override
    public Developer save(Developer developer) {
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(developer);
        transaction.commit();
        session.close();

        return developer;
    }

    @Override
    public Optional<Developer> findById(Long id) {
        Developer developer = getSessionFactory().openSession().get(Developer.class, id);
        return Optional.ofNullable(developer);
    }

    @Override
    public List<Developer> findAll() {
        return (List<Developer>) getSessionFactory()
                .openSession()
                .createQuery("FROM Developer")
                .list();
    }

    @Override
    public Developer update(Developer developer) {
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(developer);
        transaction.commit();
        session.close();

        return developer;
    }

    @Override
    public void deleteById(Long id) {
        Developer deletedDeveloper =
                findById(id).orElseThrow(() -> new DataNotFoundException("Developer id=" + id + " not found."));
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.remove(deletedDeveloper);
        transaction.commit();
        session.close();
    }
}
