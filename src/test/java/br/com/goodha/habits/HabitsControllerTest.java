package br.com.goodha.habits;

import br.com.goodha.GoodhaApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = GoodhaApplication.class)
@AutoConfigureMockMvc
class HabitsControllerTest {

    ObjectMapper mapper = new ObjectMapper();

    @Test
    void exampleTest(@Autowired MockMvc mvc) throws Exception {
        mvc.perform(get("/v1/habits/hello")).andExpect(status().isOk()).andExpect(content().string("Hello World!"));
    }

    @Test
    void deveListarHabitosCorretamente(@Autowired MockMvc mvc) throws Exception {
        mvc.perform(get("/v1/habits")).andExpect(status().isOk()).andDo(print());
    }

    @Test
    void deveCriarHabitosCorretamente(@Autowired MockMvc mvc) throws Exception {

        HabitPresentation novoHabito = getHabitPresentation();

        mvc.perform(MockMvcRequestBuilders.post("/v1/habits")
                .content(mapper.writeValueAsString(novoHabito))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
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