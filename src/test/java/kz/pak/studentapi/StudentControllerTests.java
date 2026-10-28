package kz.pak.studentapi;

import kz.pak.studentapi.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private StudentRepository repository;

    @BeforeEach
    void clearDatabase() {
        repository.deleteAll();
    }

    @Test
    void createListReadUpdateDeleteStudent() throws Exception {
        String location = mvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Stanislav Pak\",\"email\":\"stanislav@example.com\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.name").value("Stanislav Pak"))
                .andReturn().getResponse().getHeader("Location");

        mvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Stanislav Pak"));

        mvc.perform(get(location))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("stanislav@example.com"));

        mvc.perform(put(location)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Stanislav Pak\",\"email\":\"pak@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("pak@example.com"));

        mvc.perform(delete(location)).andExpect(status().isNoContent());
        mvc.perform(get(location)).andExpect(status().isNotFound());
    }

    @Test
    void missingStudentsReturn404() throws Exception {
        mvc.perform(get("/api/students/999999")).andExpect(status().isNotFound());
        mvc.perform(put("/api/students/999999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Stanislav Pak\",\"email\":\"stanislav@example.com\"}"))
                .andExpect(status().isNotFound());
        mvc.perform(delete("/api/students/999999")).andExpect(status().isNotFound());
    }

    @Test
    void invalidInputReturns400() throws Exception {
        mvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\",\"email\":\"invalid\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());
    }
}
