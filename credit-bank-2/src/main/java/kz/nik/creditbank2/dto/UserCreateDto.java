package kz.nik.creditbank2.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDto {
    private String email;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private int creditQuantity;
    private int creditRepaid;
    private int creditSum;
    private int repaidSum;
}