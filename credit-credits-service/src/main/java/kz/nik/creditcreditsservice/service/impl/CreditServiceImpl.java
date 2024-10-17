package kz.nik.creditcreditsservice.service.impl;

import kz.nik.creditcreditsservice.dto.CreditDto;
import kz.nik.creditcreditsservice.dto.UserDto;
import kz.nik.creditcreditsservice.exception.CreditNotFoundException;
import kz.nik.creditcreditsservice.exception.UserNotFoundException;
import kz.nik.creditcreditsservice.feign.UserFeignClient;
import kz.nik.creditcreditsservice.exception.UserNotFoundException;
import kz.nik.creditcreditsservice.mapper.CreditMapper;
import kz.nik.creditcreditsservice.model.Credit;

import kz.nik.creditcreditsservice.repository.CreditRepository;

import kz.nik.creditcreditsservice.service.CreditService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
@RequiredArgsConstructor

public class CreditServiceImpl implements CreditService {

    private final CreditRepository creditRepository;
    private final CreditMapper creditMapper;
    private final UserFeignClient userFeignClient;


    @Override
    public List<CreditDto> getCredits() {
        return creditMapper.toDtoList(creditRepository.findAll());
    }

    @Override
    public CreditDto getCredit(Long id) {
        return creditMapper.toDto(creditRepository.findById(id).orElse(null));
    }

    @Override
    public CreditDto addCredit(CreditDto creditDto, String token) {
        ResponseEntity<UserDto> response = userFeignClient.getCurrentUser(token);
        UserDto userDto = response.getBody();

        if (userDto != null) {
            return creditMapper.toDto(creditRepository.save(creditMapper.toEntity(creditDto)));
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    @Override
    public CreditDto updateCredit(CreditDto creditDto, String token) {
        ResponseEntity<UserDto> response = userFeignClient.getCurrentUser(token);
        UserDto userDto = response.getBody();

        if (userDto != null) {
            return creditMapper.toDto(creditRepository.save(creditMapper.toEntity(creditDto)));
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    @Override
    public void deleteCredit(Long id) {
        Credit deleteCredit = creditRepository.findById(id).orElse(null);
        if (deleteCredit != null) {
            creditRepository.delete(deleteCredit);
        } else {

        }
    }


}



