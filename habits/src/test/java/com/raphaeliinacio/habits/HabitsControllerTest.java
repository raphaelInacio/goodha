package com.raphaeliinacio.habits;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = HabitsApplication.class)
@AutoConfigureMockMvc
class HabitsControllerTest {

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private HabitRepository repository;

    @Test
    @DisplayName("Cria um novo habito")
    void deveListarHabitosCorretamente(@Autowired MockMvc mvc) throws Exception {
        mvc.perform(get("/v1/habits"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Cria um novo habito")
    void deveCriarHabitosCorretamente(@Autowired MockMvc mvc) throws Exception {

        HabitPresentation novoHabito = getHabitPresentation();

        mvc.perform(MockMvcRequestBuilders.post("/v1/habits")
                .content(mapper.writeValueAsString(novoHabito))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("name").value(novoHabito.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("image").value(novoHabito.getImage()))
                .andExpect(MockMvcResultMatchers.jsonPath("longDescription").value(novoHabito.getLongDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("longImage").value(novoHabito.getLongImage()))
                .andExpect(MockMvcResultMatchers.jsonPath("id").isNotEmpty())
                .andDo(print());

    }

    @Test
    @DisplayName("Atualiza um habito existente")
    void deveAtualizarUmHabitosCorretamente(@Autowired MockMvc mvc) throws Exception {

        Optional<Habit> first = repository.findAll().stream().findFirst();

        HabitPresentation habitoAtualizado = modelMapper.map(first.get(), HabitPresentation.class);
        habitoAtualizado.setImage("Imagem atualizada");
        habitoAtualizado.setLongDescription("LongDescription atualizada");

        mvc.perform(MockMvcRequestBuilders.put("/v1/habits/" + first.get().getId())
                .content(mapper.writeValueAsString(habitoAtualizado))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("name").value(habitoAtualizado.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("image").value(habitoAtualizado.getImage()))
                .andExpect(MockMvcResultMatchers.jsonPath("longDescription").value(habitoAtualizado.getLongDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("longImage").value(habitoAtualizado.getLongImage()))
                .andExpect(MockMvcResultMatchers.jsonPath("id").isNotEmpty())
                .andDo(print());

    }

    @Test
    @DisplayName("Recupera habitos por uma lista de Ids")
    void deveRecuperarHabitosPorUmaListaDeIds(@Autowired MockMvc mvc) throws Exception {

        var all = repository.findAll();

        List<Long> ids = all.stream().map(habit -> habit.getId()).collect(Collectors.toList()).subList(0, 2);

        mvc.perform(MockMvcRequestBuilders.post("/v1/habits/all-by-ids")
                .content(mapper.writeValueAsString(ids))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value(ids.get(0)))
                .andDo(print());

    }


    private HabitPresentation getHabitPresentation() {
        var novoHabito = new HabitPresentation();
        novoHabito.setImage("setImage");
        novoHabito.setLongDescription("setLongDescription");
        novoHabito.setLongImage("setLongImage");
        novoHabito.setName("Name");
        return novoHabito;
    }
}