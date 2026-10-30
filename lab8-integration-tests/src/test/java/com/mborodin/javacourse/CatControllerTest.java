package com.mborodin.javacourse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mborodin.javacourse.model.Cat;
import com.mborodin.javacourse.model.CatBehaviour;
import com.mborodin.javacourse.repository.CatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
@Import(TestJpaConfig.class)
public class CatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CatRepository catRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        catRepository.deleteAll();
    }

    @Test
    void testCreateCat_success() throws Exception {
        Cat cat = new Cat("Oscar", CatBehaviour.CALM);

        mockMvc.perform(post("/cats")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cat)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Oscar")))
                .andExpect(jsonPath("$.behaviour", is("CALM")));
    }

    @Test
    void testCreateCat_conflictAlreadyExists() throws Exception {
        Cat cat = new Cat("Oscar", CatBehaviour.CALM);
        catRepository.save(cat);

        mockMvc.perform(post("/cats")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cat)))
                .andExpect(status().isConflict());
    }

    @Test
    void testGetAllCats() throws Exception {
        catRepository.save(new Cat("Barsik", CatBehaviour.CALM));
        catRepository.save(new Cat("Murzik", CatBehaviour.CRAZY));

        mockMvc.perform(get("/cats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].name", containsInAnyOrder("Barsik", "Murzik")));
    }

    @Test
    void testGetCatsByBehaviour() throws Exception {
        catRepository.save(new Cat("Barsik", CatBehaviour.CALM));
        catRepository.save(new Cat("Murzik", CatBehaviour.CRAZY));

        mockMvc.perform(get("/cats")
                        .param("behaviour", "CALM"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Barsik")))
                .andExpect(jsonPath("$[0].behaviour", is("CALM")));
    }

    @Test
    void testGetCatByName_found() throws Exception {
        catRepository.save(new Cat("Tom", CatBehaviour.NORMAL));

        mockMvc.perform(get("/cats/Tom"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Tom")))
                .andExpect(jsonPath("$.behaviour", is("NORMAL")));
    }

    @Test
    void testGetCatByName_notFound() throws Exception {
        mockMvc.perform(get("/cats/UnknownCat"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateCat_success() throws Exception {
        catRepository.save(new Cat("Oscar", CatBehaviour.CALM));

        Cat updatedCat = new Cat("Oscar", CatBehaviour.CRAZY);

        mockMvc.perform(put("/cats/Oscar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedCat)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Oscar")))
                .andExpect(jsonPath("$.behaviour", is("CRAZY")));
    }

    @Test
    void testUpdateCat_notFound() throws Exception {
        Cat cat = new Cat("NonExistent", CatBehaviour.NORMAL);

        mockMvc.perform(put("/cats/NonExistent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cat)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteCat_success() throws Exception {
        catRepository.save(new Cat("Oscar", CatBehaviour.CALM));

        mockMvc.perform(delete("/cats/Oscar"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/cats/Oscar"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteCat_notFound() throws Exception {
        mockMvc.perform(delete("/cats/NonExistent"))
                .andExpect(status().isNotFound());
    }
}