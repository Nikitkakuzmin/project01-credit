package kz.nik.creditbank2.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String username;
    private int creditQuantity;
    private int creditRepaid;
    private int creditSum;
    private int repaidSum;
}