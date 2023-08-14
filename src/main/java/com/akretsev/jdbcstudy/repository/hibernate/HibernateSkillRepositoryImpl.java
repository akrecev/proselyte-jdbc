package com.akretsev.jdbcstudy.repository.hibernate;

import com.akretsev.jdbcstudy.exception.DataNotFoundException;
import com.akretsev.jdbcstudy.model.Skill;
import com.akretsev.jdbcstudy.repository.SkillRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

import static com.akretsev.jdbcstudy.utility.HibernateUtil.getSessionFactory;

public class HibernateSkillRepositoryImpl implements SkillRepository {
    @Override
    public Skill save(Skill skill) {
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(skill);
        transaction.commit();
        session.close();

        return skill;
    }

    @Override
    public Optional<Skill> findById(Integer id) {
        Skill skill = getSessionFactory().openSession().get(Skill.class, id);
        return Optional.ofNullable(skill);
    }

    @Override
    public List<Skill> findAll() {
        return (List<Skill>) getSessionFactory()
                .openSession()
                .createQuery("FROM Skill")
                .list();
    }

    @Override
    public Skill update(Skill skill) {
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(skill);
        transaction.commit();
        session.close();

        return skill;
    }

    @Override
    public void deleteById(Integer id) {
        Skill deletedSkill =
                findById(id).orElseThrow(() -> new DataNotFoundException("Skill id=" + id + " not found."));
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.remove(deletedSkill);
        transaction.commit();
        session.close();
    }
}
