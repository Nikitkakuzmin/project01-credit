package kz.nik.creditcreditsservice.mapper;

import kz.nik.creditcreditsservice.dto.CreditDto;
import kz.nik.creditcreditsservice.model.Credit;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CreditMapper {
    CreditDto toDto(Credit credit);
    Credit toEntity(CreditDto creditDto);
    List<CreditDto> toDtoList(List<Credit> list);
}
