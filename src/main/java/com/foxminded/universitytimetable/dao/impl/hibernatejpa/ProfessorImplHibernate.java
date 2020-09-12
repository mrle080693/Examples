package com.foxminded.universitytimetable.dao.impl.hibernatejpa;

import com.foxminded.universitytimetable.dao.ProfessorDAO;
import com.foxminded.universitytimetable.dao.queries.JPQLQueries;
import com.foxminded.universitytimetable.models.Professor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

@Repository("professorImplHibernateBean")
public class ProfessorImplHibernate implements ProfessorDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProfessorImplHibernate.class);
    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public ProfessorImplHibernate(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }


    @Override
    public int add(Professor professor) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to add professor: " + professor);
        }

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        int id = entityManager.merge(professor).getId();
        entityManager.getTransaction().commit();
        entityManager.close();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully add professor with id = " + id);
        }

        return id;
    }

    @Override
    public List<Professor> getAll() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to get all from table professors");
        }

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createQuery(JPQLQueries.GET_ALL_PROFESSORS);
        List professors = query.getResultList();
        entityManager.close();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Result is: " + professors);
        }

        return professors;
    }

    @Override
    public Professor getById(int id) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to get professor by id = " + id);
        }

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createQuery(JPQLQueries.GET_PROFESSOR_BY_ID);
        query.setParameter("id", id);
        Professor professor = (Professor) query.getSingleResult();
        entityManager.close();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Result is: " + professor);
        }

        return professor;
    }

    @Override
    public List<Professor> getBySurname(String surname) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to get professor by surname = " + surname);
        }

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createQuery(JPQLQueries.GET_PROFESSORS_BY_SURNAME);
        query.setParameter("surname", surname);
        List professors = query.getResultList();
        entityManager.close();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Result is: " + professors);
        }

        return professors;
    }

    @Override
    public int update(Professor professor) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to update professor: " + professor);
        }

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        int id = entityManager.merge(professor).getId();
        entityManager.getTransaction().commit();
        entityManager.close();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully update professor with id = " + id);
        }

        return id;
    }

    @Override
    public int remove(int professorId) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to remove professor with id = " + professorId);
        }

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery(JPQLQueries.DELETE_PROFESSOR);
        query.setParameter("id", professorId);
        int status = query.executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully remove professor with id: " + professorId);
        }

        return status;
    }
}
