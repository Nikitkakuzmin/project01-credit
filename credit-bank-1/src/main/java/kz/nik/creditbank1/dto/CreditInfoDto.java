package kz.nik.creditbank1.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreditInfoDto {
    private int creditQuantity;
    private int creditRepaid;
    private int creditSum;
    private int repaidSum;
}