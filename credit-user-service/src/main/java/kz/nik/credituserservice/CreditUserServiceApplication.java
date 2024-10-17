package kz.nik.credituserservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CreditUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CreditUserServiceApplication.class, args);
    }

}
