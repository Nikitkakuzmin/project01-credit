package kz.nik.creditcreditsservice.feign;

import kz.nik.creditcreditsservice.config.FeignConfig;
import kz.nik.creditcreditsservice.dto.CreditDto;
import kz.nik.creditcreditsservice.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "user-feign-client",url = "http://localhost:7001",configuration =  FeignConfig.class)
public interface UserFeignClient {
    @GetMapping(value ="/user/current")
    ResponseEntity<UserDto> getCurrentUser(@RequestHeader("Authorization") String token);

    @PostMapping(value = "/user/credit-add")
    void updateUserCredit(@RequestParam String username, @RequestBody CreditDto creditDto);




}
