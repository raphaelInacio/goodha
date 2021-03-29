package com.raphaeliinacio.records;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Random;

@SpringBootTest(classes = RecordsApplication.class)
@AutoConfigureMockMvc
class RecordsControllerTest {

    @Autowired
    private RecordRepository repository;

    @Test
    public void deveCriarUmRecordsComSucesso(@Autowired MockMvc mockMvc) throws Exception {

        RecordRepresentation recordRepresentation = new RecordRepresentation();
        recordRepresentation.setHabitId(new Random().nextLong());
        recordRepresentation.setAccountId(new Random().nextLong());
        recordRepresentation.setDone(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/records")
                .content(new ObjectMapper().writeValueAsString(recordRepresentation))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("habitId").value(recordRepresentation.getHabitId()))
                .andExpect(MockMvcResultMatchers.jsonPath("accountId").value(recordRepresentation.getAccountId()))
                .andExpect(MockMvcResultMatchers.jsonPath("accountId").value(recordRepresentation.getAccountId()))
                .andExpect(MockMvcResultMatchers.jsonPath("date").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("done").isBoolean())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    public void deveAtualizarUmRegistroComSucesso(@Autowired MockMvc mockMvc) throws Exception {

        Record record = repository.findAll().iterator().next();
        record.setHabitId(new Random().nextLong());
        record.setAccountId(new Random().nextLong());
        record.setDone(true);
        record.setDate(null);

        mockMvc.perform(MockMvcRequestBuilders.put("/v1/records/" + record.getId())
                .content(new ObjectMapper().writeValueAsString(RecordRepresentation.toRepresentation(record)))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("habitId").value(record.getHabitId()))
                .andExpect(MockMvcResultMatchers.jsonPath("accountId").value(record.getAccountId()))
                .andExpect(MockMvcResultMatchers.jsonPath("accountId").value(record.getAccountId()))
                .andExpect(MockMvcResultMatchers.jsonPath("date").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("done").isBoolean())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deveRecuperarOsRegistrosPorAccount(@Autowired MockMvc mockMvc) throws Exception {
        Record record = repository.findAll().iterator().next();

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/records/" + record.getAccountId())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deveRecuperarOsRegistrosPorAccountEHabito(@Autowired MockMvc mockMvc) throws Exception {

        Record record = repository.findAll().iterator().next();

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/records/" + record.getAccountId() + "/" + record.getHabitId())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

}