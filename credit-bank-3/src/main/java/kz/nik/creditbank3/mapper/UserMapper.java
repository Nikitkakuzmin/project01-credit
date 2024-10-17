package kz.nik.creditbank3.mapper;

import kz.nik.creditbank3.dto.UserInfoDto;
import kz.nik.creditbank3.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserInfoDto toDto(User user);

    User toEntity(UserInfoDto userInfoDto);

    List<UserInfoDto> toDtoList(List<User> pictures);
}