package kz.nik.creditcreditsservice.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private int creditQuantity;
    private int creditRepaid;
    private int creditSum;
    private int repaidSum;
    private double rate;

}