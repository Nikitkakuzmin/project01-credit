package kz.nik.creditbank2.service;

import kz.nik.creditbank2.dto.CreditInfoDto;
import kz.nik.creditbank2.dto.UserCreateDto;
import kz.nik.creditbank2.dto.UserInfoDto;

public interface UserService {
    void addUser(UserCreateDto userCreateDto);
    UserInfoDto getUserByEmail(String email);
    CreditInfoDto getCreditInfo(String username);


}