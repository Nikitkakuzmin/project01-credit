package kz.nik.creditcreditsservice;


import kz.nik.creditcreditsservice.dto.CreditDto;

import kz.nik.creditcreditsservice.mapper.CreditMapper;
import kz.nik.creditcreditsservice.model.Credit;
import kz.nik.creditcreditsservice.repository.CreditRepository;
import kz.nik.creditcreditsservice.service.impl.CreditServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

class CreditCreditsServiceApplicationTests {

   @Autowired
   private CreditMapper creditMapper;
   @Autowired
   private CreditRepository creditRepository;
   @Autowired
   private CreditServiceImpl creditServiceImpl;




    @Test
    public void testToDto() {

        Credit credit = new Credit();
        credit.setId(1L);
        credit.setMaxAmount(10000);
        credit.setName("small");
        credit.setRate(25);


        CreditDto creditDto = creditMapper.toDto(credit);


        assertNotNull(creditDto);
        assertEquals(credit.getId(), creditDto.getId());
        assertEquals(credit.getMaxAmount(), creditDto.getMaxAmount());
        assertEquals(credit.getName(), creditDto.getName());
        assertEquals(credit.getRate(), creditDto.getRate());
    }

    @Test
    public void testToEntity() {

        CreditDto creditDto = new CreditDto();
        creditDto.setId(1L);
        creditDto.setMaxAmount(10000);
        creditDto.setName("small");
        creditDto.setRate(25);


        Credit credit = creditMapper.toEntity(creditDto);


        assertNotNull(credit);
        assertEquals(creditDto.getId(), credit.getId());
        assertEquals(creditDto.getMaxAmount(), credit.getMaxAmount());
        assertEquals(creditDto.getName(), credit.getName());
        assertEquals(creditDto.getRate(), credit.getRate());
    }

    @Test
    public void testToDtoList() {

        Credit credit1 = new Credit();
        credit1.setId(1L);
        credit1.setMaxAmount(10000);
        credit1.setName("small");
        credit1.setRate(25);

        Credit credit2 = new Credit();
        credit2.setId(2L);
        credit2.setMaxAmount(20000);
        credit2.setName("middle");
        credit2.setRate(50);


        List<CreditDto> creditDtoList = creditMapper.toDtoList(Arrays.asList(credit1, credit2));


        assertNotNull(creditDtoList);
        assertEquals(2, creditDtoList.size());
        assertEquals(credit1.getId(), creditDtoList.get(0).getId());
        assertEquals(credit2.getId(), creditDtoList.get(1).getId());
    }
}
