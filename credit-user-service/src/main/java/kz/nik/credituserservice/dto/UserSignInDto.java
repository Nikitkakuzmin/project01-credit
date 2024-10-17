package kz.nik.credituserservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignInDto {
    private String username;
    private String password;
}
