package ru.job4j.accidents.controller;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.Main;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.service.JpaAuthorityService;
import ru.job4j.accidents.service.JpaUserService;

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
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PasswordEncoder encoder;

    @MockBean
    private JpaUserService userService;

    @MockBean
    private JpaAuthorityService authorityService;

    @Test
    @WithMockUser
    public void shouldReturnRegView() throws Exception {
        this.mockMvc.perform(get("/reg"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("reg"));
    }

    @Test
    @WithMockUser
    public void shouldReturnLoginView() throws Exception {
        this.mockMvc.perform(get("/login"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    @WithMockUser
    public void shouldReturnLogoutView() throws Exception {
        this.mockMvc.perform(get("/logout"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());

    }

    @Test
    @WithMockUser
    public void shouldReturnRegAction() throws Exception {
        when(userService.save(Mockito.any(User.class))).thenReturn(true);
        this.mockMvc.perform(post("/reg")
                        .param("id", "0")
                        .param("username", "name")
                        .param("password", "pass"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
        ArgumentCaptor<User> user = ArgumentCaptor.forClass(User.class);
        verify(encoder).encode(Mockito.any(String.class));
        verify(authorityService).findByAuthority(Mockito.any(String.class));
        verify(userService).save(user.capture());
        assertThat(user.getValue().getUsername()).isEqualTo("name");
    }
}





