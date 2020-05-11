package com.foxminded.universitytimetable.dao;

import com.foxminded.universitytimetable.configurations.SpringJDBCConfig;
import com.foxminded.universitytimetable.dao.impl.GroupImpl;
import com.foxminded.universitytimetable.dao.impl.LessonImpl;
import com.foxminded.universitytimetable.dao.impl.ProfessorImpl;
import com.foxminded.universitytimetable.exceptions.NotFoundEntityException;
import com.foxminded.universitytimetable.models.Group;
import com.foxminded.universitytimetable.models.Lesson;
import com.foxminded.universitytimetable.models.Professor;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GroupImplTest {
    private AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringJDBCConfig.class);

    private GroupImpl groupImpl = context.getBean("groupImplBean", GroupImpl.class);
    private Group group = new Group("test");

    @BeforeEach
    void dataSet() {
        groupImpl.add(group);

    }

    // add method
    @Test
    void addMustAddGroupToDB() {
        int groupsQuantity = groupImpl.getAll().size();
        assertTrue(groupsQuantity > 0);
    }

    @Test
    void addMustCreateIdNotEqualsZero() {
        int id = groupImpl.add(group);

        assertTrue(id > 0);
    }

    @Test
    void addMustAddGroupWithCorrectName() {
        String expected = groupImpl.getByName(group.getName()).get(0).getName();
        String actual = group.getName();

        assertEquals(expected, actual);
    }

    @Test
    void addMustAddGroupWithLongAndDifficultName() {
        group.setName("I am very loooooooong and very difficult */?>< name of the group!!!");
        groupImpl.add(group);

        String expected = groupImpl.getByName(group.getName()).get(0).getName();
        String actual = group.getName();

        assertEquals(expected, actual);
    }

    // getAll method
    @Test
    void getAllMustReturnEmptyListIfTableIsEmpty() {
        groupImpl.remove(1);

        int expected = 0;
        int actual = groupImpl.getAll().size();

        assertEquals(expected, actual);
    }

    @Test
    void getAllMustReturnAllFromTable() {
        for (int index = 1; index < 100; index++) {
            groupImpl.add(group);
        }

        int expected = 100;
        int actual = groupImpl.getAll().size();

        assertEquals(expected, actual);
    }

    // getById method
    @Test
    void getByIdMustReturnCorrectResult() {
        String expected = "test";
        String actual = groupImpl.getById(1).getName();

        assertEquals(expected, actual);
    }

    @Test
    void getByIdMustThrowNotFoundEntityExceptionIfTableIsNotContainsSuchId() {
        Assertions.assertThrows(NotFoundEntityException.class, () -> groupImpl.getById(6));
    }

    // getByName method
    @Test
    void getByNameMustReturnEmptyListIfTableIsNotContainsGroupsWithSuchName() {
        groupImpl.remove(1);

        int groupsQuantity = groupImpl.getByName("Test").size();
        assertTrue(groupsQuantity == 0);
    }

    @Test
    void getByNameMustReturnGroupWithInputNameIfTableContainsSuchGroupName() {
        List<Group> groups = groupImpl.getByName("test");

        String expected = "test";
        String actual = groups.get(0).getName();

        assertEquals(expected, actual);
    }

    @Test
    void getByNameMustReturnAllGroupWithInputNameIfTableContainsSuchGroupName() {
        for (int index = 1; index < 1000; index++) {
            groupImpl.add(group);
        }

        int expected = 1000;
        int actual = groupImpl.getByName("test").size();

        assertEquals(expected, actual);
    }

    // update method
    @Test
    void updateMustThrowIllegalArgumentExceptionIfTableNotContainsRowWithInputGroupId() {
        group.setId(33);
        Assertions.assertThrows(IllegalArgumentException.class, () -> groupImpl.update(group));
    }

    @Test
    void updateMustUpdateRowInTableWithIdEqualsInputGroupId() {
        group.setId(1);
        group.setName("Updated");
        groupImpl.update(group);

        String expected = "Updated";
        String actual = groupImpl.getByName("Updated").get(0).getName();

        assertEquals(expected, actual);
    }

    // remove method
    @Test
    void removeMustThrowIllegalArgumentExceptionIfTableNotContainsRowWithInputGroupId() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> groupImpl.remove(7));
    }

    @Test
    void removeMustRemoveRowInTableWithIdEqualsInputGroupId() {
        int rowsQuantityBeforeRemove = groupImpl.getAll().size();

        groupImpl.remove(1);
        int rowsQuantityAfterRemove = groupImpl.getAll().size();

        assertEquals(rowsQuantityBeforeRemove, rowsQuantityAfterRemove + 1);
    }

    @Test
    void removeMustCascadeRemoveLessonsWithItsGroup() {
        Lesson lesson = new Lesson(new Date(1212, 12, 12), 1, 1,
                1, "Building", "Classroom");
        Professor professor = new Professor("Name", "Surname", "Patronymic", "Math");
        LessonImpl lessonImpl = context.getBean("lessonImplBean", LessonImpl.class);
        ProfessorImpl professorImpl = context.getBean("professorImplBean", ProfessorImpl.class);

        professorImpl.add(professor);
        lessonImpl.add(lesson);

        int lessonsQuantityBeforeRemove = lessonImpl.getAll().size();

        groupImpl.remove(1);

        int lessonsQuantityAfterRemove = lessonImpl.getAll().size();

        assertTrue(lessonsQuantityBeforeRemove > lessonsQuantityAfterRemove);
    }
}
