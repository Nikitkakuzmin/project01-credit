package kz.nik.credituserservice.controller;

import kz.nik.credituserservice.api.UserController;
import kz.nik.credituserservice.client.Bank1FeignClient;
import kz.nik.credituserservice.client.Bank2FeignClient;
import kz.nik.credituserservice.client.Bank3FeignClient;
import kz.nik.credituserservice.client.CreditFeignClient;
import kz.nik.credituserservice.dto.UserDto;
import kz.nik.credituserservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)

@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private Bank1FeignClient bank1FeignClient;
    @MockBean
    private Bank2FeignClient bank2FeignClient;
    @MockBean
    private Bank3FeignClient bank3FeignClient;
    @MockBean
    private CreditFeignClient creditFeignClient;

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetUserWithValidToken() throws Exception {
        mockMvc.perform(get("/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "valid_token"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetUserWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }


    @Test
    public void testGetUserWithInvalidToken() throws Exception {
        mockMvc.perform(get("/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "invalid_token"))
                .andExpect(status().isUnauthorized());
    }


}
