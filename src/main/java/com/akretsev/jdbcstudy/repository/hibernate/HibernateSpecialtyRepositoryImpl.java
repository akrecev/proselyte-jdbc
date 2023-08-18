package com.akretsev.jdbcstudy.repository.hibernate;

import com.akretsev.jdbcstudy.exception.DataNotFoundException;
import com.akretsev.jdbcstudy.model.Specialty;
import com.akretsev.jdbcstudy.repository.SpecialtyRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

import static com.akretsev.jdbcstudy.utility.HibernateUtil.getSession;

public class HibernateSpecialtyRepositoryImpl implements SpecialtyRepository {
    @Override
    public Specialty save(Specialty specialty) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.persist(specialty);
        transaction.commit();
        session.close();

        return specialty;
    }

    @Override
    public Optional<Specialty> findById(Integer id) {
        Session session = getSession();
        Specialty specialty = session.get(Specialty.class, id);
        session.close();

        return Optional.ofNullable(specialty);
    }

    @Override
    public List<Specialty> findAll() {
        Session session = getSession();
        List<Specialty> specialties = session.createQuery("FROM Specialty", Specialty.class).list();
        session.close();

        return specialties;
    }

    @Override
    public Specialty update(Specialty specialty) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.merge(specialty);
        transaction.commit();
        session.close();

        return specialty;
    }

    @Override
    public void deleteById(Integer id) {
        Specialty deletedSpecialty =
                findById(id).orElseThrow(() -> new DataNotFoundException("Specialty id=" + id + " not found."));
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.remove(deletedSpecialty);
        transaction.commit();
        session.close();
    }
}
