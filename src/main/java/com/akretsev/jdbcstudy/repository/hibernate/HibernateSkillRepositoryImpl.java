package com.akretsev.jdbcstudy.repository.hibernate;

import com.akretsev.jdbcstudy.exception.DataNotFoundException;
import com.akretsev.jdbcstudy.model.Skill;
import com.akretsev.jdbcstudy.repository.SkillRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

import static com.akretsev.jdbcstudy.utility.HibernateUtil.getSession;

public class HibernateSkillRepositoryImpl implements SkillRepository {
    @Override
    public Skill save(Skill skill) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.persist(skill);
        transaction.commit();
        session.close();

        return skill;
    }

    @Override
    public Optional<Skill> findById(Integer id) {
        Session session = getSession();
        Skill skill = session.get(Skill.class, id);
        session.close();

        return Optional.ofNullable(skill);
    }

    @Override
    public List<Skill> findAll() {
        Session session = getSession();
        List<Skill> skills = session.createQuery("FROM Skill", Skill.class).list();
        session.close();

        return skills;
    }

    @Override
    public Skill update(Skill skill) {
        Session session = getSession();
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
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.remove(deletedSkill);
        transaction.commit();
        session.close();
    }
}
