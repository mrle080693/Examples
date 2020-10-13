package com.foxminded.universitytimetable.database;

import com.foxminded.universitytimetable.database.impl.repositories.GroupRepository;
import com.foxminded.universitytimetable.models.Group;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GroupRepositoryTest {
    @Autowired
    private GroupRepository groupRepository;
    private Group group = new Group("Test");

    @Test
    void addMustCreateIdNotEqualsZero() {
        Group group = groupRepository.save(new Group());
        int id = group.getId();

        assertTrue(id > 0);
    }

    @Test
    void addMustAddGroupWithCorrectName() {
        groupRepository.save(group);
        String expected = groupRepository.getByName(group.getName()).get(0).getName();
        String actual = group.getName();

        assertEquals(expected, actual);
    }

    @Test
    void addMustAddGroupWithLongAndDifficultName() {
        group.setName("I am very loooooooong and very difficult */?>< name of the group!!!");
        groupRepository.save(group);

        String expected = groupRepository.getByName(group.getName()).get(0).getName();
        String actual = group.getName();

        assertEquals(expected, actual);
    }

    @Test
    void getAllMustReturnEmptyListIfTableIsEmpty() {
        int expected = 0;
        int actual = groupRepository.findAll().size();

        assertEquals(expected, actual);
    }

    @Test
    void getAllMustReturnAllFromTable() {
        ArrayList<Group> groups = new ArrayList<>();

        groups.add(group);
        group.setId(2);
        groups.add(group);
        group.setId(3);
        groups.add(group);

        groupRepository.saveAll(groups);

        int expected = 3;
        int actual = groupRepository.findAll().size();

        assertEquals(expected, actual);
    }

    @Test
    void getByIdMustReturnGroupWithCorrectName() {
        groupRepository.save(group);

        String expected = "Test";
        String actual = groupRepository.getOne(1).getName();

        assertEquals(expected, actual);
    }

    @Test
    void getByNameMustReturnEmptyListIfTableIsNotContainsGroupsWithSuchName() {
        int groupsQuantity = groupRepository.getByName("Test").size();
        assertTrue(groupsQuantity == 0);
    }

    @Test
    void getByNameMustReturnGroupWithInputNameIfTableContainsSuchGroupName() {
        groupRepository.save(group);
        List<Group> groups = groupRepository.getByName("Test");

        String expected = "Test";
        String actual = groups.get(0).getName();

        assertEquals(expected, actual);
    }

    // Single running test
    @Test
    void getByNameMustReturnAllGroupWithInputNameIfTableContainsSuchGroupName() {
        for (int index = 0; index < 100; index++) {
            group.setId(index + 1);
            groupRepository.save(group);
        }

        int expected = 100;
        int actual = groupRepository.getByName("Test").size();

        assertEquals(expected, actual);
    }

    // Single running test
    @Test
    void updateMustUpdateRowInTableWithIdEqualsInputGroupId() {
        group.setId(1);
        group.setName("Updated");
        groupRepository.save(group);

        String expected = "Updated";
        String actual = groupRepository.getByName("Updated").get(0).getName();

        assertEquals(expected, actual);
    }

    @Test
    void removeMustRemoveRowInTableWithIdEqualsInputGroupId() {
        groupRepository.save(group);
        int rowsQuantityBeforeRemove = groupRepository.findAll().size();

        groupRepository.remove(1);
        int rowsQuantityAfterRemove = groupRepository.findAll().size();

        assertEquals(rowsQuantityBeforeRemove, rowsQuantityAfterRemove + 1);
    }
}
