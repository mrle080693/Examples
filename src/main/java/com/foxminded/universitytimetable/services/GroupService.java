package com.foxminded.universitytimetable.services;

import com.foxminded.universitytimetable.configurations.SpringJDBCConfig;
import com.foxminded.universitytimetable.dao.impl.GroupImpl;
import com.foxminded.universitytimetable.exceptions.DAOException;
import com.foxminded.universitytimetable.exceptions.NotFoundEntityException;
import com.foxminded.universitytimetable.models.Group;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

//@Service (Происходит зацикливание при вызове контекста из контекста)
public class GroupService {
    private AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringJDBCConfig.class);
    private GroupImpl groupImpl = context.getBean("groupImplBean", GroupImpl.class);

    public int add(Group group) throws DAOException {
        int groupIdInTable;

        int id = group.getId();
        String name = group.getName();

        if (group == null) {
            throw new IllegalArgumentException("Group cant be null");
        }

        if (id != 0) {
            throw new IllegalArgumentException("New group id must be 0. \n" +
                    "If you want update group you have to use update method");
        }

        if (name == null) {
            throw new IllegalArgumentException("Group must have name");
        }

        if (name.trim().equals("")) {
            throw new IllegalArgumentException("Group name must not be empty");
        }

        groupIdInTable = groupImpl.add(group);

        return groupIdInTable;
    }

    public List<Group> getAll() throws DAOException {
        List<Group> groups;

        groups = groupImpl.getAll();

        if (groups.isEmpty()) {
            throw new NotFoundEntityException("Table groups is empty");
        }

        return groups;
    }

    public Group getById(int id) throws DAOException, NotFoundEntityException {
        Group group;

        if (id == 0) {
            throw new IllegalArgumentException("Group id cant be 0");
        }

        group = groupImpl.getById(id);

        return group;
    }

    public List<Group> getByName(String name) throws DAOException, NotFoundEntityException {
        List<Group> groups;

        if (name == null) {
            throw new IllegalArgumentException("Group must have name");
        }

        if (name.trim().equals("")) {
            throw new IllegalArgumentException("Group name must not be empty");
        }

        groups = groupImpl.getByName(name);

        return groups;
    }

    public int update(Group group) throws DAOException, NotFoundEntityException {
        int status;

        int id = group.getId();
        String name = group.getName();

        if (group == null) {
            throw new IllegalArgumentException("Group for update cant be null");
        }

        if (id == 0) {
            throw new IllegalArgumentException("New group id must not be 0. \n" +
                    "If you want add new group you have to use add method");
        }

        if (name == null) {
            throw new IllegalArgumentException("Group must have name");
        }

        if (name.trim().equals("")) {
            throw new IllegalArgumentException("Group name must not be empty");
        }

        status = groupImpl.update(group);

        if (status != 1) {
            throw new IllegalArgumentException("Group with input id doesnt exist");
        }

        return status;
    }

    public int remove(int groupId) throws DAOException, NotFoundEntityException {
        int status;

        status = groupImpl.remove(groupId);

        if (status != 1) {
            throw new IllegalArgumentException("Group with input id doesnt exist");
        }

        return status;
    }
}
