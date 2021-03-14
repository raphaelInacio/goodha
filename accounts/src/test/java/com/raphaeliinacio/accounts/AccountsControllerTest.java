package com.raphaeliinacio.accounts;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
                .email("contato.raphaelinacio@gmail.com")
                .name("Raphael Inacio")
                .build();
    }

}