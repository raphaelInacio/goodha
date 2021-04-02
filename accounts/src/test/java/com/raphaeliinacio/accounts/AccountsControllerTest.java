package com.raphaeliinacio.accounts;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Random;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest(classes = AccountsApplication.class)
@AutoConfigureMockMvc
class AccountsControllerTest {

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private AccountsRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Test
    @DisplayName("Criar uma nova conta para o usuário")
    void deveCriarUmaNovaAccountParaUsuario(@Autowired MockMvc mvc) throws Exception {

        AccountPresentation newAccountPresentation = getAccountPresentation();

        mvc.perform(MockMvcRequestBuilders.post("/v1/accounts")
                .content(mapper.writeValueAsString(newAccountPresentation))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("email").value(newAccountPresentation.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("name").value(newAccountPresentation.getName()))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("Não permite o cadastro de duas accounts para mesmo usuário")
    void naoDevePermitirCadastrarDuasAcccountsComMesmoEmail(@Autowired MockMvc mvc) throws Exception {
        AccountPresentation newAccountPresentation = getAccountPresentation();

        var savedAccount = repository.save(modelMapper.map(newAccountPresentation, Account.class));

        mvc.perform(MockMvcRequestBuilders.post("/v1/accounts")
                .content(mapper.writeValueAsString(AccountPresentation.fromDomain(savedAccount, null)))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is4xxClientError())
                .andDo(print());
    }


    @Test
    @Disabled
    @DisplayName("Lista todas as accounts")
    void deveListarTodasAccounts(@Autowired MockMvc mvc) throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/v1/accounts")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Recupera uma account por ID")
    void deveRecuperarUmaNovaAccountPorId(@Autowired MockMvc mvc) throws Exception {

        AccountPresentation newAccountPresentation = getAccountPresentation();

        var savedAccount = repository.save(modelMapper.map(newAccountPresentation, Account.class));

        mvc.perform(MockMvcRequestBuilders.get("/v1/accounts/" + savedAccount.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(savedAccount.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("email").value(savedAccount.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("name").value(savedAccount.getName()))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isOk())
                .andDo(print());
    }


    @Test
    @DisplayName("Adciona um novo habito para uma account existente")
    void deveAdicionarUmaNovoHabitoParaUmaAccount(@Autowired MockMvc mvc) throws Exception {

        var newAccountPresentation = getAccountPresentation();
        var addHabitPresentation = getAddHabitPresentation();


        var savedAccount = repository.save(modelMapper.map(newAccountPresentation, Account.class));

        mvc.perform(MockMvcRequestBuilders.post("/v1/accounts/" + savedAccount.getId() + "/add-habit")
                .content(mapper.writeValueAsString(addHabitPresentation))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isOk())
                .andDo(print());

        mvc.perform(MockMvcRequestBuilders.get("/v1/accounts/" + savedAccount.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(savedAccount.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("email").value(savedAccount.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("name").value(savedAccount.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.myHabits").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.myHabits.[0].id").value(addHabitPresentation.getId()))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isOk())
                .andDo(print());

    }

    @Test
    @DisplayName("Quando uma account não existir verifica se http Status 204 é retornado")
    void quandoNaoExistirAccountDeveRetornar204(@Autowired MockMvc mvc) throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/v1/accounts/" + 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isNoContent())
                .andDo(print());
    }

    private AccountPresentation getAccountPresentation() {
        return AccountPresentation.
                builder()
                .accountTypeEnum(AccountTypeEnum.USER)
                .email(new Random().nextInt() + "@gmail.com")
                .name("Raphael Inacio")
                .build();
    }

    private AddHabitPresentation getAddHabitPresentation() {
        return AddHabitPresentation
                .builder()
                .id(5073695114526720L)
                .build();
    }

}