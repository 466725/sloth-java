package unit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import demo.LoginController;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class LoginControllerTest {

    @Test
    void loginReturnsSuccessForValidCredentials() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new LoginController()).build();
        mockMvc.perform(post("/api/login")
                .param("username", "username")
                .param("password", "password"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("login successful"));
    }

    @Test
    void loginReturnsWrongCredentialsForInvalidCredentials() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new LoginController()).build();
        mockMvc.perform(post("/api/login")
                .param("username", "admin")
                .param("password", "admin"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("wrong credentials"));
    }
}
