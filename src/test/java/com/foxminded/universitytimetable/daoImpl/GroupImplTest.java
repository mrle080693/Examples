package com.foxminded.universitytimetable.daoImpl;

import com.foxminded.universitytimetable.configurations.SpringContextConfig;
import com.foxminded.universitytimetable.dao.GroupDAO;
import com.foxminded.universitytimetable.dao.impl.GroupImpl;
import com.foxminded.universitytimetable.exceptions.DAOException;
import com.foxminded.universitytimetable.models.Group;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// https://www.logicbig.com/tutorials/spring-framework/spring-boot/jdbc-and-in-memory-h2.html
@RunWith(MockitoJUnitRunner.class)
public class GroupImplTest {
    private AnnotationConfigApplicationContext context = SpringContextConfig.context;

    @Mock
    GroupDAO groupDAO;

    @InjectMocks
    GroupImpl groupImpl = context.getBean("groupImplBean", GroupImpl.class);

    @Test
    public void addTest() {
        try {
            Group group = new Group("g");

            groupImpl.add(group);

            assertEquals(groupImpl.getByName(group.getName()).get(0).getName().trim(), group.getName().trim());
        } catch (DAOException dao){
            System.out.println(dao.getDAOExceptionMessage());
            dao.getDataAccessException().printStackTrace();
        }
    }
}
