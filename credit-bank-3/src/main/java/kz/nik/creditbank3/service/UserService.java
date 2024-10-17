package kz.nik.creditbank3.service;

import kz.nik.creditbank3.dto.CreditInfoDto;
import kz.nik.creditbank3.dto.UserCreateDto;
import kz.nik.creditbank3.dto.UserInfoDto;

public interface UserService {
    void addUser(UserCreateDto userCreateDto);
    UserInfoDto getUserByEmail(String email);
    CreditInfoDto getCreditInfo(String username);


}