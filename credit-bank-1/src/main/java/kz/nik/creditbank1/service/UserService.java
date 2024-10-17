package kz.nik.creditbank1.service;


import kz.nik.creditbank1.dto.CreditInfoDto;
import kz.nik.creditbank1.dto.UserCreateDto;
import kz.nik.creditbank1.dto.UserInfoDto;

public interface UserService {
    void addUser(UserCreateDto userCreateDto);
    UserInfoDto getUserByEmail(String email);
    CreditInfoDto getCreditInfo(String username);


}