package kz.nik.creditcreditsservice.service;

import kz.nik.creditcreditsservice.dto.CreditDto;
import kz.nik.creditcreditsservice.model.Credit;
import org.springframework.context.annotation.Primary;

import java.util.List;

public interface CreditService {
    List<CreditDto> getCredits();

    CreditDto getCredit(Long id);

    CreditDto addCredit(CreditDto creditDto, String token);

    CreditDto updateCredit(CreditDto creditDto, String token);

    void deleteCredit(Long id);
}








