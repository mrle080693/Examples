package com.foxminded.universitytimetable.dao.impl.hibernatejpa;

import com.foxminded.universitytimetable.dao.LessonDAO;
import com.foxminded.universitytimetable.dao.queries.JPQLQueries;
import com.foxminded.universitytimetable.models.Lesson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

@Repository("lessonImplHibernateBean")
public class LessonImplHibernate implements LessonDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(LessonImplHibernate.class);
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public int add(Lesson lesson) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to add lesson " + lesson);
        }

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        int id = entityManager.merge(lesson).getId();
        entityManager.getTransaction().commit();
        entityManager.close();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully add lesson with id = " + id);
        }

        return id;
    }

    @Override
    public List<Lesson> getAll() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to get all from table lessons");
        }

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createQuery(JPQLQueries.GET_ALL_LESSONS);
        List lessons = query.getResultList();
        entityManager.close();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Result is: " + lessons);
        }

        return lessons;
    }

    @Override
    public Lesson getById(int id) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to get lesson by id = " + id);
        }

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createQuery(JPQLQueries.GET_LESSON_BY_ID);
        query.setParameter("id", id);
        Lesson lesson = (Lesson) query.getSingleResult();
        entityManager.close();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Result is: " + lesson);
        }

        return lesson;
    }

    @Override
    public int update(Lesson lesson) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to update lesson: " + lesson);
        }

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        int id = entityManager.merge(lesson).getId();
        entityManager.getTransaction().commit();
        entityManager.close();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully update lesson with status = " + id);
        }

        return id;

    }

    @Override
    public int remove(int lessonId) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to remove lesson with id = " + lessonId);
        }

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery(JPQLQueries.DELETE_LESSON);
        query.setParameter("id", lessonId);
        int status = query.executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully remove lesson with id: " + lessonId);
        }

        return status;
    }
}
