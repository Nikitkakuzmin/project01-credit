package kz.nik.creditcreditsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CreditCreditsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CreditCreditsServiceApplication.class, args);
    }

}
