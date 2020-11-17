package com.foxminded.universitytimetable.api.restcontrollers.system;

import com.foxminded.universitytimetable.api.constants.Urls;
import com.foxminded.universitytimetable.database.ProfessorDAO;
import com.foxminded.universitytimetable.models.Professor;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "/test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class ProfessorRestControllerSystemTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProfessorDAO professorDAO;

    private final String NAME = "TestName";
    private final String SURNAME = "TestSurname";
    private final String PATRONYMIC = "TestPatronymic";
    private final String SUBJECT = "TestSubject";
    private Gson gson = new Gson();

    @Test
    void add_shouldReturnCorrectResponse_whenRequestIsCorrect() throws Exception {
        Professor professor = new Professor(NAME, SURNAME, PATRONYMIC, SUBJECT);
        professor.setId(1);
        String professorJson = gson.toJson(professor);

        mockMvc.perform(post(Urls.API_REST_POST_PROFESSOR_JSON)
                .param("name", NAME)
                .param("surname", SURNAME)
                .param("patronymic", PATRONYMIC)
                .param("subject", SUBJECT))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(professorJson));
    }

    @Test
    void add_shouldReturnCorrectResponse_whenRequestIsNotCorrect() throws Exception {
        mockMvc.perform(post(Urls.API_REST_POST_PROFESSOR_JSON)
                .param("wrong", ""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAll_shouldReturnCorrectResponse_whenRequestIsCorrect() throws Exception {
        String expected = "";
        mockMvc.perform(get(Urls.API_REST_GET_PROFESSORS_JSON))
                .andExpect(content().string(expected));

        mockMvc.perform(post(Urls.API_REST_POST_PROFESSOR_JSON)
                .param("name", NAME)
                .param("surname", SURNAME)
                .param("patronymic", PATRONYMIC)
                .param("subject", SUBJECT));
        mockMvc.perform(post(Urls.API_REST_POST_PROFESSOR_JSON)
                .param("name", NAME)
                .param("surname", SURNAME)
                .param("patronymic", PATRONYMIC)
                .param("subject", SUBJECT));

        expected = "[{\"id\":1,\"name\":\"TestName\",\"surname\":\"TestSurname\",\"patronymic\":\"TestPatronymic\"," +
                "\"subject\":\"TestSubject\"},{\"id\":2,\"name\":\"TestName\",\"surname\":\"TestSurname\"," +
                "\"patronymic\":\"TestPatronymic\",\"subject\":\"TestSubject\"}]";
        mockMvc.perform(get(Urls.API_REST_GET_PROFESSORS_JSON))
                .andExpect(content().string(expected));
    }

    @Test
    void getById_shouldReturnCorrectResponse_whenRequestIsCorrect() throws Exception {
        Professor professor = new Professor(NAME, SURNAME, PATRONYMIC, SUBJECT);
        professorDAO.add(professor);

        professor.setId(1);
        String professorJson = gson.toJson(professor);

        mockMvc.perform(get(Urls.API_REST_GET_PROFESSOR_JSON_BY_ID, 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(professorJson));
    }

    @Test
    void getById_shouldReturnCorrectResponse_whenRequestIsNotCorrect() throws Exception {
        mockMvc.perform(get(Urls.API_REST_GET_PROFESSOR_JSON_BY_ID, "wrong"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getBySurname_shouldReturnCorrectResponse_whenRequestIsCorrect() throws Exception {
        Professor professor = new Professor(NAME, SURNAME, PATRONYMIC, SUBJECT);
        professorDAO.add(professor);
        String expected = "[{\"id\":1,\"name\":\"TestName\",\"surname\":\"TestSurname\"," +
                "\"patronymic\":\"TestPatronymic\",\"subject\":\"TestSubject\"}]";

        mockMvc.perform(get(Urls.API_REST_GET_PROFESSOR_JSON_BY_SURNAME, professor.getSurname())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(expected));
    }

    @Test
    void update_shouldReturnCorrectResponse_whenRequestIsCorrect() throws Exception {
        Professor professor = new Professor(NAME, SURNAME, PATRONYMIC, SUBJECT);
        professorDAO.add(professor);

        professor.setId(1);
        professor.setName("updated");
        String professorJson = gson.toJson(professor);

        mockMvc.perform(put(Urls.API_REST_PUT_PROFESSOR_JSON)
                .param("id", "1")
                .param("name", professor.getName())
                .param("surname", SURNAME)
                .param("patronymic", PATRONYMIC)
                .param("subject", SUBJECT))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(professorJson));
    }

    @Test
    void update_shouldReturnCorrectResponse_whenRequestIsNotCorrect() throws Exception {
        mockMvc.perform(put(Urls.API_REST_PUT_PROFESSOR_JSON)
                .param("id", "1")
                .param("wrong", ""))
                .andExpect(status().isBadRequest());

        mockMvc.perform(put(Urls.API_REST_PUT_PROFESSOR_JSON)
                .param("id", "wrong")
                .param("name", NAME)
                .param("surname", SURNAME)
                .param("patronymic", PATRONYMIC)
                .param("subject", SUBJECT))
                .andExpect(status().isBadRequest());
    }

    @Test
    void remove_shouldReturnCorrectResponse_whenRequestIsCorrect() throws Exception {
        Professor professor = new Professor(NAME, SURNAME, PATRONYMIC, SUBJECT);
        professorDAO.add(professor);

        mockMvc.perform(delete(Urls.API_REST_DELETE_PROFESSOR_JSON)
                .param("professorId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("1"));
    }

    @Test
    void remove_shouldReturnCorrectResponse_whenRequestIsNotCorrect() throws Exception {
        mockMvc.perform(delete(Urls.API_REST_DELETE_PROFESSOR_JSON)
                .param("wrong", "1"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(delete(Urls.API_REST_DELETE_PROFESSOR_JSON)
                .param("professorId", "wrong"))
                .andExpect(status().isBadRequest());
    }
}
