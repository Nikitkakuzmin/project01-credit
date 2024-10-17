package kz.nik.credituserservice.client;

import kz.nik.credituserservice.config.FeignConfig;
import kz.nik.credituserservice.dto.CreditDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "credit-credit-client",url = "http://localhost:7774",configuration =  FeignConfig.class)
public interface CreditFeignClient {


    @GetMapping("/credit")
    ResponseEntity<List<CreditDto>> getCredits(@RequestHeader("Authorization") String token);


}
