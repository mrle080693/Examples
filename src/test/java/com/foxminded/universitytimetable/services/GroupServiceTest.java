package com.foxminded.universitytimetable.services;

import com.foxminded.universitytimetable.configurations.SpringJDBCConfig;
import com.foxminded.universitytimetable.exceptions.NotFoundEntityException;
import com.foxminded.universitytimetable.models.Group;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Component
class GroupServiceTest {
    @Autowired
    private static AnnotationConfigApplicationContext context;
    private static GroupService groupService;

    @BeforeAll
    static void dataSet() {
        context = new AnnotationConfigApplicationContext(SpringJDBCConfig.class);
        groupService = context.getBean("groupServiceBean", GroupService.class);
    }

    @Test
    void addMustAddNewGroup() {
        Group group = new Group("Test");

        int expected = 1;
        int actual = groupService.add(group);

        assertEquals(expected, actual);
    }




    @Test
    void getByIdMustThrowNotFoundEntityExceptionIfTableIsNotContainsSuchId() {
        Assertions.assertThrows(NotFoundEntityException.class, () -> groupService.getById(6));
    }
}
