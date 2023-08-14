package com.akretsev.jdbcstudy.repository.hibernate;

import com.akretsev.jdbcstudy.exception.DataNotFoundException;
import com.akretsev.jdbcstudy.model.Specialty;
import com.akretsev.jdbcstudy.repository.SpecialtyRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

import static com.akretsev.jdbcstudy.utility.HibernateUtil.getSessionFactory;

public class HibernateSpecialtyRepositoryImpl implements SpecialtyRepository {
    @Override
    public Specialty save(Specialty specialty) {
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(specialty);
        transaction.commit();
        session.close();

        return specialty;
    }

    @Override
    public Optional<Specialty> findById(Integer id) {
        Specialty specialty = getSessionFactory().openSession().get(Specialty.class, id);
        return Optional.ofNullable(specialty);
    }

    @Override
    public List<Specialty> findAll() {
        return (List<Specialty>) getSessionFactory()
                .openSession()
                .createQuery("FROM Specialty ")
                .list();
    }

    @Override
    public Specialty update(Specialty specialty) {
        Session session = getSessionFactory().openSession();
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
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.remove(deletedSpecialty);
        transaction.commit();
        session.close();
    }
}
