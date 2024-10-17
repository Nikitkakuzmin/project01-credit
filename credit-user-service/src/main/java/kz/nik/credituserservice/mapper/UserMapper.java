package kz.nik.credituserservice.mapper;


import kz.nik.credituserservice.dto.UserDto;
import kz.nik.credituserservice.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);

    User toEntity(UserDto userDto);

    List<UserDto> toDtoList(List<User> users);
}
