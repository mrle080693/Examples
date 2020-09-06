package com.foxminded.universitytimetable.dao.impl.hibernate;

import com.foxminded.universitytimetable.dao.GroupDAO;
import com.foxminded.universitytimetable.dao.impl.jdbctemplate.GroupImpl;
import com.foxminded.universitytimetable.dao.queries.JPQLQueries;
import com.foxminded.universitytimetable.dao.queries.SQLQueries;
import com.foxminded.universitytimetable.models.Group;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

@Repository("groupImplHibernateBean")
public class GroupImplHibernate implements GroupDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupImplHibernate.class);
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("mr");

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int add(Group group) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to add group: " + group);
        }

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        int id = entityManager.merge(group).getId();
        entityManager.getTransaction().commit();
        entityManager.close();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully add group with id = " + id);
        }

        return id;
    }

    @Override
    public List<Group> getAll() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to get all from table groups");
        }

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createQuery(JPQLQueries.GET_ALL_GROUPS);
        List groups = query.getResultList();
        entityManager.close();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Result is: " + groups);
        }

        return groups;
    }

    @Override
    public Group getById(int id) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to get group by id = " + id);
        }

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createQuery(JPQLQueries.GET_GROUP_BY_ID);
        query.setParameter("id", id);
        Group group = (Group) query.getSingleResult();
        entityManager.close();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Result is: " + group);
        }

        return group;
    }

    @Override
    public List<Group> getByName(String name) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to get group by name = " + name);
        }

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createQuery(JPQLQueries.GET_GROUPS_BY_NAME);
        query.setParameter("name", name);
        List groups = query.getResultList();
        entityManager.close();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Result is: " + groups);
        }

        return groups;
    }

    @Override
    public int update(Group group) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to update group: " + group);
        }

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        int id = entityManager.merge(group).getId();
        entityManager.getTransaction().commit();
        entityManager.close();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully update group with id = " + id);
        }

        return id;
    }

    @Override
    public int remove(int groupId) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Try to remove group with id = " + groupId);
        }

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery(JPQLQueries.DELETE_GROUP);
        query.setParameter("id", groupId);
        int status = query.executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Successfully remove group with id: " + groupId);
        }

        return status;
    }
}
