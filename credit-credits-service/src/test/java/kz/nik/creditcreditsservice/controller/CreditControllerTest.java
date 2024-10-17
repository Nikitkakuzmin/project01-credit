package kz.nik.creditcreditsservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.nik.creditcreditsservice.AbstractTest;
import kz.nik.creditcreditsservice.api.CreditController;
import kz.nik.creditcreditsservice.client.AuthClient;
import kz.nik.creditcreditsservice.dto.CreditDto;
import kz.nik.creditcreditsservice.feign.UserFeignClient;
import kz.nik.creditcreditsservice.repository.CreditRepository;
import kz.nik.creditcreditsservice.service.CreditService;
import kz.nik.creditcreditsservice.service.impl.CreditServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;




@WebMvcTest(CreditController.class)

public class CreditControllerTest  {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreditService creditService;

    @MockBean
    private AuthClient authClient;

    @MockBean
    private UserFeignClient userFeignClient;

    @MockBean
    private CreditRepository creditRepository;

    @Test
    public void testGetCredits_Unauthorized() throws Exception {
        String token = "invalid-token";
        when(authClient.isAuthenticated(token)).thenReturn(false);

        mockMvc.perform(get("/credit")
                        .header("Authorization", token))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testGetCredits_Success() throws Exception {
        String token = "valid-token";
        when(authClient.isAuthenticated(token)).thenReturn(true);
        when(creditService.getCredits()).thenReturn(Collections.singletonList(new CreditDto(1L, "Credit 1", 10000, 5.0)));

        mockMvc.perform(get("/credit")
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Credit 1"))
                .andExpect(jsonPath("$[0].maxAmount").value(10000))
                .andExpect(jsonPath("$[0].rate").value(5.0));
    }

    @Test
    public void testGetCredit_Success() throws Exception {
        Long id = 1L;
        CreditDto creditDto = new CreditDto();
        creditDto.setId(1L);
        creditDto.setName("creditName");
        creditDto.setMaxAmount(10000);
        creditDto.setRate(5.0);

        when(authClient.isAuthenticated(anyString())).thenReturn(true);
        when(creditService.getCredit(id)).thenReturn(creditDto);

        mockMvc.perform(get("/credit/{id}", id)
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"name\":\"creditName\",\"maxAmount\":10000,\"rate\":5.0}"));
    }

    @Test
    public void testGetCredit_Unauthorized() throws Exception {
        Long id = 1L;
        when(authClient.isAuthenticated(anyString())).thenReturn(false);

        mockMvc.perform(get("/credit/{id}", id)
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isUnauthorized());
    }
}
