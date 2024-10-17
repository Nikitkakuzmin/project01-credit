package kz.nik.creditcreditsservice.service;


import feign.FeignException;
import kz.nik.creditcreditsservice.client.AuthClient;
import kz.nik.creditcreditsservice.dto.CreditDto;
import kz.nik.creditcreditsservice.dto.UserDto;
import kz.nik.creditcreditsservice.exception.UserNotFoundException;
import kz.nik.creditcreditsservice.feign.UserFeignClient;
import kz.nik.creditcreditsservice.mapper.CreditMapper;
import kz.nik.creditcreditsservice.model.Credit;
import kz.nik.creditcreditsservice.repository.CreditRepository;
import kz.nik.creditcreditsservice.service.impl.CreditServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.*;
import static org.junit.Assert.assertThrows;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CreditServiceImplTest {


    @MockBean
    private CreditRepository creditRepository;

    @Autowired
    private CreditServiceImpl creditServiceImpl;

    @MockBean
    private CreditMapper creditMapper;
    @Mock
    private UserFeignClient userFeignClient;

    @Mock
    private AuthClient authClient;

    @Test
    public void testGetCredits() {

        List<Credit> credits = Arrays.asList(
                new Credit(1L, "Small Credit", 10000, 3.5),
                new Credit(2L, "Big Credit", 20000, 4.0)
        );


        when(creditRepository.findAll()).thenReturn(credits);
        when(creditMapper.toDtoList(credits)).thenReturn(Arrays.asList(
                new CreditDto(1L, "Small Credit", 10000, 3.5),
                new CreditDto(2L, "Big Credit", 20000, 4.0)
        ));


        List<CreditDto> result = creditServiceImpl.getCredits();


        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Small Credit", result.get(0).getName());
        assertEquals(10000, result.get(0).getMaxAmount());
    }


    @Test
    public void testGetCredit_Success() {
        Long id = 1L;
        Credit credit = new Credit();
        CreditDto creditDto = new CreditDto();
        when(creditRepository.findById(id)).thenReturn(Optional.of(credit));
        when(creditMapper.toDto(credit)).thenReturn(creditDto);

        CreditDto result = creditServiceImpl.getCredit(id);

        assertNotNull(result);
        assertEquals(creditDto, result);
    }

    @Test
    public void testGetCredit_NotFound() {
        Long id = 1L;
        when(creditRepository.findById(id)).thenReturn(Optional.empty());

        CreditDto result = creditServiceImpl.getCredit(id);

        assertNull(result);
    }

}
