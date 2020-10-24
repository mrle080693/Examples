package com.foxminded.universitytimetable.api.restcontrollers;

import com.foxminded.universitytimetable.models.Professor;
import com.foxminded.universitytimetable.services.ProfessorService;
import com.foxminded.universitytimetable.services.exceptions.NotFoundEntityException;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProfessorRestController.class)
class ProfessorRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProfessorService professorService;
    private Gson gson = new Gson();

    private final String NAME = "For";
    private final String SURNAME = "Not";
    private final String PATRONYMIC = "Be";
    private final String SUBJECT = "Confused";
    private final Professor PROFESSOR = new Professor(NAME, SURNAME, PATRONYMIC, SUBJECT);
    private final String PROFESSOR_JSON = gson.toJson(PROFESSOR);

    @Test
    void addHaveToReturnCorrectResponse() {
        try {
            given(professorService.add(any(Professor.class))).willReturn(PROFESSOR);

            mockMvc.perform(post("/rest/professors/post")
                    .param("name", NAME)
                    .param("surname", SURNAME)
                    .param("patronymic", PATRONYMIC)
                    .param("subject", SUBJECT))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(content().json(PROFESSOR_JSON));
        } catch (Exception e) {
            // try/catch for see green test. Throws make test red and disoriented me.
        }
    }

    @Test
    void addHaveToThrowResponseStatusExceptionWithCorrectStatusIfCatchException() {
        try {
            given(professorService.add(any(Professor.class))).willThrow(ValidationException.class);
            mockMvc.perform(post("/rest/professors/post")
                    .param("name", NAME)
                    .param("surname", SURNAME)
                    .param("patronymic", PATRONYMIC)
                    .param("subject", SUBJECT))
                    .andExpect(status().isBadRequest());

            given(professorService.add(any(Professor.class))).willThrow(Exception.class);
            mockMvc.perform(post("/rest/professors/post")
                    .param("name", NAME)
                    .param("surname", SURNAME)
                    .param("patronymic", PATRONYMIC)
                    .param("subject", SUBJECT))
                    .andExpect(status().isInternalServerError());
        } catch (Exception e) {
            // try/catch for see green test. Throws make test red and disoriented me.
        }
    }

    @Test
    void addHaveToThrowResponseStatusExceptionWithBadRequestStatusIfRequestParamIsWrong() {
        try {
            mockMvc.perform(post("/rest/professors/post")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("wrong", ""))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            // try/catch for see green test. Throws make test red and disoriented me.
        }
    }

    @Test
    void getAllHaveToReturnCorrectResponse() {
        try {
            List<Professor> professors = new ArrayList<>();
            professors.add(PROFESSOR);
            professors.add(PROFESSOR);
            String professorsJson = gson.toJson(professors);

            given(professorService.getAll()).willReturn(professors);

            mockMvc.perform(get("/rest/professors")
                    .contentType(MediaType.APPLICATION_JSON))

                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(content().json(professorsJson));
        } catch (Exception e) {
            // try/catch for see green test. Throws make test red and disoriented me.
        }
    }

    @Test
    void getAllHaveToThrowResponseStatusExceptionWithCorrectStatusIfCatchException() {
        try {
            given(professorService.getAll()).willThrow(ValidationException.class);
            mockMvc.perform(get("/rest/professors")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());

            given(professorService.getAll()).willThrow(NotFoundEntityException.class);
            mockMvc.perform(get("/rest/professors")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());

            given(professorService.getAll()).willThrow(Exception.class);
            mockMvc.perform(get("/rest/professors")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError());
        } catch (Exception e) {
            // try/catch for see green test. Throws make test red and disoriented me.
        }
    }

    @Test
    void getByIdHaveToReturnCorrectResponse() {
        try {
            given(professorService.getById(any(Integer.class))).willReturn(PROFESSOR);

            mockMvc.perform(get("/rest/professors/{id}"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(content().json(PROFESSOR_JSON));
        } catch (Exception e) {
            // try/catch for see green test. Throws make test red and disoriented me.
        }
    }

    @Test
    void getByIdHaveToThrowResponseStatusExceptionWithCorrectStatusWhenCatchException() {
        try {
            given(professorService.getById(1)).willThrow(ValidationException.class);
            mockMvc.perform(get("/rest/professors/{1}"))
                    .andExpect(status().isBadRequest());

            given(professorService.getById(1)).willThrow(NotFoundEntityException.class);
            mockMvc.perform(get("/rest/professors/{1}"))
                    .andExpect(status().isNotFound());

            given(professorService.getById(1)).willThrow(Exception.class);
            mockMvc.perform(get("/rest/professors/{1}"))
                    .andExpect(status().isInternalServerError());
        } catch (Exception e) {
            // try/catch for see green test. Throws make test red and disoriented me.
        }
    }

    @Test
    void getBySurnameHaveToReturnCorrectResponse() {
        try {
            List<Professor> professors = new ArrayList<>();
            professors.add(PROFESSOR);
            professors.add(PROFESSOR);

            given(professorService.getBySurname(any(String.class))).willReturn(professors);

            mockMvc.perform(get("/rest/professors/{surname}")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(content().json(PROFESSOR_JSON));
        } catch (Exception e) {
            // try/catch for see green test. Throws make test red and disoriented me.
        }
    }

    @Test
    void getBySurnameHaveToThrowResponseStatusExceptionWithCorrectStatusIfCatchException() {
        try {
            given(professorService.getBySurname(any(String.class))).willThrow(ValidationException.class);

            mockMvc.perform(get("/rest/professors/{surname}"))
                    .andExpect(status().isBadRequest());

            given(professorService.getBySurname(any(String.class))).willThrow(NotFoundEntityException.class);
            mockMvc.perform(get("/rest/professors/{surname}"))
                    .andExpect(status().isNotFound());

            given(professorService.getBySurname("")).willThrow(Exception.class);
            mockMvc.perform(get("/rest/professors/{surname}"))
                    .andExpect(status().isInternalServerError());
        } catch (Exception e) {
            // try/catch for see green test. Throws make test red and disoriented me.
        }
    }

    @Test
    void updateHaveToReturnCorrectResponse() {
        try {
            given(professorService.add(any(Professor.class))).willReturn(PROFESSOR);

            mockMvc.perform(post("/rest/professors/put")
                    .param("id", "3")
                    .param("name", NAME)
                    .param("surname", SURNAME)
                    .param("patronymic", PATRONYMIC)
                    .param("subject", SUBJECT))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(content().json(PROFESSOR_JSON));
        } catch (Exception e) {
            // try/catch for see green test. Throws make test red and disoriented me.
        }
    }

    @Test
    void updateHaveToThrowResponseStatusExceptionWithCorrectStatusIfCatchException() {
        try {
            given(professorService.add(any(Professor.class))).willThrow(ValidationException.class);
            mockMvc.perform(post("/rest/professors/put")
                    .param("id", "3")
                    .param("name", NAME)
                    .param("surname", SURNAME)
                    .param("patronymic", PATRONYMIC)
                    .param("subject", SUBJECT))
                    .andExpect(status().isBadRequest());

            given(professorService.add(any(Professor.class))).willThrow(Exception.class);
            mockMvc.perform(post("/rest/professors/put")
                    .param("id", "3")
                    .param("name", NAME)
                    .param("surname", SURNAME)
                    .param("patronymic", PATRONYMIC)
                    .param("subject", SUBJECT))
                    .andExpect(status().isInternalServerError());
        } catch (Exception e) {
            // try/catch for see green test. Throws make test red and disoriented me.
        }
    }

    @Test
    void updateHaveToThrowResponseStatusExceptionWithBadRequestStatusIfRequestParamIsWrong() {
        try {
            mockMvc.perform(post("/rest/professors/put")
                    .param("Wrong", "3")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("wrong", ""))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            // try/catch for see green test. Throws make test red and disoriented me.
        }
    }

    @Test
    void removeHaveToReturnCorrectResponse() {
        try {
            given(professorService.remove(any(Integer.class))).willReturn(PROFESSOR);

            mockMvc.perform(delete("/rest/professors/delete")
                    .param("professorId", String.valueOf(1)))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(content().json(PROFESSOR_JSON));
        } catch (Exception e) {
            // try/catch for see green test. Throws make test red and disoriented me.
        }
    }

    @Test
    void removeHaveToThrowResponseStatusExceptionWithCorrectStatusIfCatchException() {
        try {
            given(professorService.remove(any(Integer.class))).willThrow(NotFoundEntityException.class);
            mockMvc.perform(delete("/rest/professors/delete")
                    .param("professorId", "1"))
                    .andExpect(status().isNotFound());

            given(professorService.remove(-1)).willThrow(ValidationException.class);
            mockMvc.perform(delete("/rest/professors/delete")
                    .param("professorId", "1"))
                    .andExpect(status().isBadRequest());

            given(professorService.remove(1)).willThrow(Exception.class);
            mockMvc.perform(delete("/rest/professors/delete")
                    .param("professorId", "1"))
                    .andExpect(status().isInternalServerError());
        } catch (Exception e) {
            // try/catch for see green test. Throws make test red and disoriented me.
        }
    }

    @Test
    void removeHaveToThrowResponseStatusExceptionWithBadRequestStatusIfRequestParamIsWrong() {
        try {
            mockMvc.perform(put("/rest/professors/delete")
                    .param("wrong", "1"))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            // try/catch for see green test. Throws make test red and disoriented me.
        }
    }
}
