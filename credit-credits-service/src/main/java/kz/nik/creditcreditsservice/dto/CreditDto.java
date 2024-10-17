package kz.nik.creditcreditsservice.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreditDto {
    private Long id;
    private String name;
    private int maxAmount;
    private double rate;
}
