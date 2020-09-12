package com.foxminded.universitytimetable.dao.hibernatejpa;

import com.foxminded.universitytimetable.dao.impl.hibernatejpa.GroupImplHibernate;
import com.foxminded.universitytimetable.models.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GroupImplHibernateTest {
    private GroupImplHibernate groupImplHibernate;
    private Group group;

    @BeforeEach
    void dataSet() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("test");
        groupImplHibernate = new GroupImplHibernate(entityManagerFactory);

        group = new Group("test");
        groupImplHibernate.add(group);
    }

    @Test
    void addMustCreateIdNotEqualsZero() {
        int id = groupImplHibernate.add(group);

        assertTrue(id > 0);
    }

    @Test
    void addMustAddGroupWithCorrectName() {
        String expected = groupImplHibernate.getByName(group.getName()).get(0).getName();
        String actual = group.getName();

        assertEquals(expected, actual);
    }

    @Test
    void addMustAddGroupWithLongAndDifficultName() {
        group.setName("I am very loooooooong and very difficult */?>< name of the group!!!");
        groupImplHibernate.add(group);

        String expected = groupImplHibernate.getByName(group.getName()).get(0).getName();
        String actual = group.getName();

        assertEquals(expected, actual);
    }

    @Test
    void getAllMustReturnEmptyListIfTableIsEmpty() {
        groupImplHibernate.remove(1);

        int expected = 0;
        int actual = groupImplHibernate.getAll().size();

        assertEquals(expected, actual);
    }

    @Test
    void getAllMustReturnAllFromTable() {
        for (int index = 1; index < 100; index++) {
            groupImplHibernate.add(group);
        }

        int expected = 100;
        int actual = groupImplHibernate.getAll().size();

        assertEquals(expected, actual);
    }

    @Test
    void getByIdMustReturnGroupWithCorrectName() {
        String expected = "test";
        String actual = groupImplHibernate.getById(1).getName();

        assertEquals(expected, actual);
    }

    @Test
    void getByNameMustReturnEmptyListIfTableIsNotContainsGroupsWithSuchName() {
        groupImplHibernate.remove(1);

        int groupsQuantity = groupImplHibernate.getByName("Test").size();
        assertTrue(groupsQuantity == 0);
    }

    @Test
    void getByNameMustReturnGroupWithInputNameIfTableContainsSuchGroupName() {
        List<Group> groups = groupImplHibernate.getByName("test");

        String expected = "test";
        String actual = groups.get(0).getName();

        assertEquals(expected, actual);
    }

    @Test
    void getByNameMustReturnAllGroupWithInputNameIfTableContainsSuchGroupName() {
        for (int index = 1; index < 1000; index++) {
            groupImplHibernate.add(group);
        }

        int expected = 1000;
        int actual = groupImplHibernate.getByName("test").size();

        assertEquals(expected, actual);
    }

    @Test
    void updateMustUpdateRowInTableWithIdEqualsInputGroupId() {
        group.setId(1);
        group.setName("Updated");
        groupImplHibernate.update(group);

        String expected = "Updated";
        String actual = groupImplHibernate.getByName("Updated").get(0).getName();

        assertEquals(expected, actual);
    }

    @Test
    void removeMustRemoveRowInTableWithIdEqualsInputGroupId() {
        int rowsQuantityBeforeRemove = groupImplHibernate.getAll().size();

        groupImplHibernate.remove(1);
        int rowsQuantityAfterRemove = groupImplHibernate.getAll().size();

        assertEquals(rowsQuantityBeforeRemove, rowsQuantityAfterRemove + 1);
    }
}
