package kz.nik.credituserservice.service;

import kz.nik.credituserservice.dto.*;
import kz.nik.credituserservice.model.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    void addUser(UserCreateDto userCreateDto);

    String signIn(UserSignInDto userSignInDto);

    UserDto getCurrentUser(String token);
    void changePassword(String email, String newPassword);

    void addRoleToUser(String username, String roleName, String token);

    void removeRoleFromUser(String username, String roleName, String token);

    void removeUser(String username, String token, Role role);


    void aggregateAndSaveCreditInfo(String username,String token);
    void calculateAndSaveRate(String username,String token);

     List<UserDto> getUsers(String token);

     UserDto getUser(Long id, String token);

    List<CreditDto> getAvailableCredits(String token);
    void selectCreditForUser(Long creditId, String token);
    void payTotalCredit(String token, int paymentAmount);
}



