package kz.nik.credituserservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreditInfoDto {
    private int creditQuantity;
    private int creditRepaid;
    private int creditSum;
    private int repaidSum;
}
