package kz.nik.creditcreditsservice.client;

import feign.FeignException;
import kz.nik.creditcreditsservice.feign.UserFeignClient;
import org.springframework.stereotype.Component;

@Component
public class AuthClient {
    private final UserFeignClient userFeignClient;

    public AuthClient(UserFeignClient userFeignClient) {
        this.userFeignClient = userFeignClient;
    }

    public boolean isAuthenticated(String token) {
        try {

            userFeignClient.getCurrentUser(token);
            return true;
        } catch (FeignException e) {
            return false;
        }
    }
}
