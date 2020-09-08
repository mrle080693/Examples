package com.foxminded.universitytimetable.dao.impl.hibernate;

import com.foxminded.universitytimetable.dao.StatisticsDAO;
import com.foxminded.universitytimetable.dao.queries.JPQLQueries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.Date;

@Repository("statisticsImplHibernateBean")
public class StatisticsImplHibernate implements StatisticsDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsImplHibernate.class);
    @Autowired
    private EntityManagerFactory entityManagerFactory;


    @Override
    public long getProfessorEmployment(int professorId, Date from, Date till) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to get professor employment. Professor id: " + professorId + " from: " + from + "till: "
                    + till);
        }

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createQuery(JPQLQueries.GET_PROFESSOR_EMPLOYMENT);
        query.setParameter("professorId", professorId);
        query.setParameter("from", from);
        query.setParameter("till", till);
        Long lessonsQuantity = (Long) query.getSingleResult();
        entityManager.close();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully get professor employment. Professor id = " + professorId + "from: " + from +
                    "till: " + till);
        }

        return lessonsQuantity;
    }

    @Override
    public long getGroupEmployment(int groupId, Date from, Date till) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to get group employment. Group id: " + groupId + "from: " + from + "till: " + till);
        }

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createQuery(JPQLQueries.GET_GROUP_EMPLOYMENT);
        query.setParameter("groupId", groupId);
        query.setParameter("from", from);
        query.setParameter("till", till);
        Long lessonsQuantity = (Long) query.getSingleResult();
        entityManager.close();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully get group employment. Group id = " + groupId + "from: " + from + "till: " + till);
        }

        return lessonsQuantity;
    }
}
