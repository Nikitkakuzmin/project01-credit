package kz.nik.credituserservice.client;

import kz.nik.credituserservice.config.FeignConfig;
import kz.nik.credituserservice.dto.BankUserCreateDto;

import kz.nik.credituserservice.dto.CreditInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "bank1-feign-client",url = "http://localhost:7771",configuration =  FeignConfig.class)
public interface Bank1FeignClient   {

    @PostMapping(value = "/bank/add")
    BankUserCreateDto addUser(@RequestBody BankUserCreateDto bankUserCreateDto);

    @GetMapping("/bank/credit-info")
    CreditInfoDto getCreditInfo(@RequestParam String username);
}
