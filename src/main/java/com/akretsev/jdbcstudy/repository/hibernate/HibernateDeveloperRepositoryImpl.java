package com.akretsev.jdbcstudy.repository.hibernate;

import com.akretsev.jdbcstudy.model.Developer;
import com.akretsev.jdbcstudy.model.Status;
import com.akretsev.jdbcstudy.repository.DeveloperRepository;
import jakarta.persistence.EntityGraph;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.akretsev.jdbcstudy.utility.HibernateUtil.*;

public class HibernateDeveloperRepositoryImpl implements DeveloperRepository {

    @Override
    public Developer save(Developer developer) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.persist(developer);
        transaction.commit();
        session.close();

        return developer;
    }

    @Override
    public Optional<Developer> findById(Long id) {
        Session session = getSession();
        EntityGraph<?> entityGraph = getSession().getEntityGraph(DEVELOPER_GRAPH);
        Map<String, Object> properties = new HashMap<>();
        properties.put(FETCH_GRAPH_PATH, entityGraph);
        Developer developer = session.find(Developer.class, id, properties);
        session.close();

        return Optional.ofNullable(developer);
    }

    @Override
    public List<Developer> findAll() {
        Session session = getSession();
        EntityGraph<?> entityGraph = getSession().getEntityGraph(DEVELOPER_GRAPH);
        List<Developer> developers = session.createQuery("FROM Developer", Developer.class)
                .setHint(FETCH_GRAPH_PATH, entityGraph)
                .list();
        session.close();

        return developers;
    }

    @Override
    public Developer update(Developer developer) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.merge(developer);
        transaction.commit();
        session.close();

        return developer;
    }

    @Override
    public void deleteById(Long id) {
        Developer deletedDeveloper = Developer.builder().id(id).status(Status.DELETED).build();
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.merge(deletedDeveloper);
        transaction.commit();
        session.close();
    }


}
