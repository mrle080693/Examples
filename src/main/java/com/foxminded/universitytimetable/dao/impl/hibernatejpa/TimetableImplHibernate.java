package com.foxminded.universitytimetable.dao.impl.hibernatejpa;

import com.foxminded.universitytimetable.dao.TimetableDAO;
import com.foxminded.universitytimetable.dao.queries.JPQLQueries;
import com.foxminded.universitytimetable.models.Lesson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Repository("timetableImplHibernateBean")
public class TimetableImplHibernate implements TimetableDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(TimetableImplHibernate.class);
    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public TimetableImplHibernate(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }


    @Override
    public List<Lesson> getGroupTimetable(int groupId, Date from, Date till) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to get group timetable. Group id: " + groupId + "from: " + from + "till: " + till);
        }

        List lessons = null;

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createQuery(JPQLQueries.GET_GROUP_TIMETABLE);
        query.setParameter("groupId", groupId);
        query.setParameter("from", from);
        query.setParameter("till", till);
        lessons = query.getResultList();
        entityManager.close();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully get group timetable. Group id = " + groupId + "from: " + from + "till: " + till);
        }

        return lessons;
    }

    @Override
    public List<Lesson> getProfessorTimetable(int professorId, Date from, Date till) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to get professor timetable. Professor id: " + professorId + " from: " + from + "till: "
                    + till);
        }

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createQuery(JPQLQueries.GET_PROFESSOR_TIMETABLE);
        query.setParameter("professorId", professorId);
        query.setParameter("from", from);
        query.setParameter("till", till);
        List<Lesson> lessons = query.getResultList();
        entityManager.close();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully get professor timetable. Professor id = " + professorId + "from: " + from +
                    "till: " + till);
        }

        return lessons;
    }
}
