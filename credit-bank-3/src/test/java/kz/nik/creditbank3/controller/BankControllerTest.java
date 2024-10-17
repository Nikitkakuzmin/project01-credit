package kz.nik.creditbank3.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import kz.nik.creditbank3.api.BankController;
import kz.nik.creditbank3.dto.UserCreateDto;
import kz.nik.creditbank3.dto.UserInfoDto;
import kz.nik.creditbank3.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BankController.class)
public class BankControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @InjectMocks
    private BankController bankController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddUser() throws Exception {
        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setEmail("test@example.com");
        userCreateDto.setFirstName("First");
        userCreateDto.setLastName("Last");
        userCreateDto.setUsername("username");
        userCreateDto.setPassword("password");

        mockMvc.perform(post("/bank/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@example.com\",\"firstName\":\"First\",\"lastName\":\"Last\",\"username\":\"username\",\"password\":\"password\"}"))
                .andExpect(status().isOk());

        verify(userService, times(1)).addUser(any(UserCreateDto.class));
    }

    @Test
    public void testGetUserByEmail() throws Exception {
        String email = "test@example.com";
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setEmail(email);
        userInfoDto.setFirstName("First");
        userInfoDto.setLastName("Last");
        userInfoDto.setUsername("username");

        when(userService.getUserByEmail(email)).thenReturn(userInfoDto);

        mockMvc.perform(get("/bank/info")
                        .param("email", email))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(email));

        verify(userService, times(1)).getUserByEmail(email);
    }


}

