package ru.job4j.accidents.controller;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.Main;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.JpaAccidentService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
class AccidentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JpaAccidentService accidentService;

    @Test
    @WithMockUser
    public void shouldReturnAccidentView() throws Exception {
        this.mockMvc.perform(get("/accidents"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("accidents"));
    }

    @Test
    @WithMockUser
    public void shouldReturnCreateAccidentView() throws Exception {
        this.mockMvc.perform(get("/createAccident"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("createAccident"));
    }

    @Test
    @WithMockUser
    public void shouldReturnUpdateAccidentView() throws Exception {
        when(accidentService.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(new Accident()));
        this.mockMvc.perform(get("/updateAccident").param("id", String.valueOf(1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("updateAccident"));
    }

    @Test
    @WithMockUser
    public void shouldReturnShowErrorView() throws Exception {
        this.mockMvc.perform(get("/showError"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("showError"));
    }

    @Test
    @WithMockUser
    public void shouldReturnSaveAccidentAction() throws Exception {
        this.mockMvc.perform(post("/saveAccident")
                .param("id", "0")
                .param("name", "Crash")
                .param("text", "Bad road")
                .param("address", "Moscow")
                .param("type.id", "1")
                .param("Ids", "1", "2"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
        ArgumentCaptor<Accident> captor = ArgumentCaptor.forClass(Accident.class);
        ArgumentCaptor<String[]> str = ArgumentCaptor.forClass(String[].class);
        verify(accidentService).add(captor.capture(), str.capture());
        assertThat(captor.getValue().getName()).isEqualTo("Crash");
    }

    @Test
    @WithMockUser
    public void shouldReturnUpdateAccidentAction() throws Exception {
        this.mockMvc.perform(post("/updateAccident")
                .param("id", "2")
                .param("name", "Crash")
                .param("text", "Bad road")
                .param("address", "Moscow")
                .param("type.id", "1")
                .param("Ids", "1", "2"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
        ArgumentCaptor<Accident> captor = ArgumentCaptor.forClass(Accident.class);
        ArgumentCaptor<String[]> str = ArgumentCaptor.forClass(String[].class);
        verify(accidentService).replace(captor.capture(), str.capture());
        assertThat(captor.getValue().getName()).isEqualTo("Crash");
    }
}