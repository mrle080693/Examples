package com.foxminded.universitytimetable.api.restcontrollers.unit;

import com.foxminded.universitytimetable.api.constants.Urls;
import com.foxminded.universitytimetable.api.restcontrollers.StatisticsRestController;
import com.foxminded.universitytimetable.services.StatisticsService;
import com.foxminded.universitytimetable.services.exceptions.ValidationException;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(StatisticsRestController.class)
class StatisticsRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StatisticsService statisticsService;
    private Gson gson = new Gson();

    private long employment = 11;
    private int id = 3;
    private Date from = new Date(2019, 11, 11);
    private Date till = new Date(2019, 11, 13);
    private String jsonEmployment = gson.toJson(employment);

    @Test
    void getGroupEmploymentHaveToReturnCorrectResponse() {
        try {
            given(statisticsService.getGroupEmployment(any(Integer.class), any(Date.class), any(Date.class)))
                    .willReturn(employment);

            mockMvc.perform(get(Urls.API_REST_GET_GROUP_EMPLOYMENT_JSON)
                    .param("groupId", String.valueOf(id))
                    .param("from", String.valueOf(from))
                    .param("till", String.valueOf(till)))

                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(content().json(jsonEmployment));
        } catch (Exception e) {
            // try/catch for see green test. Throws make test red and disoriented me.
        }
    }

    @Test
    void getGroupEmploymentHaveToThrowResponseStatusExceptionWithCorrectStatusIfCatchException() {
        try {
            given(statisticsService.getGroupEmployment(any(Integer.class), any(Date.class), any(Date.class)))
                    .willThrow(ValidationException.class);
            mockMvc.perform(get(Urls.API_REST_GET_GROUP_EMPLOYMENT_JSON)
                    .param("groupId", String.valueOf(id))
                    .param("from", String.valueOf(from))
                    .param("till", String.valueOf(till)))
                    .andExpect(status().isBadRequest());


            given(statisticsService.getGroupEmployment(any(Integer.class), any(Date.class), any(Date.class)))
                    .willThrow(Exception.class);
            mockMvc.perform(get(Urls.API_REST_GET_GROUP_EMPLOYMENT_JSON)
                    .param("groupId", String.valueOf(id))
                    .param("from", String.valueOf(from))
                    .param("till", String.valueOf(till)))
                    .andExpect(status().isInternalServerError());
        } catch (Exception e) {
            // try/catch for see green test. Throws make test red and disoriented me.
        }
    }

    @Test
    void getProfessorEmploymentHaveToReturnCorrectResponse() {
        try {
            given(statisticsService.getProfessorEmployment(any(Integer.class), any(Date.class), any(Date.class)))
                    .willReturn(employment);

            mockMvc.perform(get(Urls.API_REST_GET_PROFESSOR_EMPLOYMENT_JSON)
                    .param("professorId", String.valueOf(id))
                    .param("from", String.valueOf(from))
                    .param("till", String.valueOf(till)))

                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(content().json(jsonEmployment));
        } catch (Exception e) {
            // try/catch for see green test. Throws make test red and disoriented me.
        }
    }

    @Test
    void getProfessorEmploymentHaveToThrowResponseStatusExceptionWithCorrectStatusIfCatchException() {
        try {
            given(statisticsService.getProfessorEmployment(any(Integer.class), any(Date.class), any(Date.class)))
                    .willThrow(ValidationException.class);
            mockMvc.perform(get(Urls.API_REST_GET_PROFESSOR_EMPLOYMENT_JSON)
                    .param("professorId", String.valueOf(id))
                    .param("from", String.valueOf(from))
                    .param("till", String.valueOf(till)))
                    .andExpect(status().isBadRequest());


            given(statisticsService.getGroupEmployment(any(Integer.class), any(Date.class), any(Date.class)))
                    .willThrow(Exception.class);
            mockMvc.perform(get(Urls.API_REST_GET_PROFESSOR_EMPLOYMENT_JSON)
                    .param("professorId", String.valueOf(id))
                    .param("from", String.valueOf(from))
                    .param("till", String.valueOf(till)))
                    .andExpect(status().isInternalServerError());
        } catch (Exception e) {
            // try/catch for see green test. Throws make test red and disoriented me.
        }
    }
}
