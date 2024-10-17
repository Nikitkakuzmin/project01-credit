package kz.nik.credituserservice.service.impl;


import kz.nik.credituserservice.client.*;
import kz.nik.credituserservice.dto.*;
import kz.nik.credituserservice.exception.UserNotFoundException;
import kz.nik.credituserservice.mapper.UserMapper;
import kz.nik.credituserservice.model.Role;
import kz.nik.credituserservice.model.User;
import kz.nik.credituserservice.repository.UserRepository;
import kz.nik.credituserservice.service.UserService;
import kz.nik.credituserservice.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final KeycloakClient keycloakClient;
    private final Bank1FeignClient bank1FeignClient;
    private final Bank2FeignClient bank2FeignClient;
    private final Bank3FeignClient bank3FeignClient;
    private final CreditFeignClient creditFeignClient;


    public UserDto getCurrentUser(String token) {
        String username = UserUtil.getCurrentUserName();

        User user = userRepository.findByUsername(username);

        if (user == null) {
            return null;
        }

        return userMapper.toDto(user);
    }

    public void aggregateAndSaveCreditInfo(String username,String token) {

        CreditInfoDto bank1Credits = bank1FeignClient.getCreditInfo(username);
        CreditInfoDto bank2Credits = bank2FeignClient.getCreditInfo(username);
        CreditInfoDto bank3Credits = bank3FeignClient.getCreditInfo(username);


        int totalCreditQuantity = bank1Credits.getCreditQuantity() + bank2Credits.getCreditQuantity() + bank3Credits.getCreditQuantity();
        int totalCreditRepaid = bank1Credits.getCreditRepaid() + bank2Credits.getCreditRepaid() + bank3Credits.getCreditRepaid();
        int totalCreditSum = bank1Credits.getCreditSum() + bank2Credits.getCreditSum() + bank3Credits.getCreditSum();
        int totalRepaidSum = bank1Credits.getRepaidSum() + bank2Credits.getRepaidSum() + bank3Credits.getRepaidSum();


        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }


        user.setCreditQuantity(totalCreditQuantity);
        user.setCreditRepaid(totalCreditRepaid);
        user.setCreditSum(totalCreditSum);
        user.setRepaidSum(totalRepaidSum);

        userRepository.save(user);
    }


    @Override
    public List<UserDto> getUsers(String token) {
        return userMapper.toDtoList(userRepository.findAll());
    }

    @Override
    public UserDto getUser(Long id, String token) {
        return userMapper.toDto(userRepository.findById(id).orElse(null));
    }

    @Override
    public void addUser(UserCreateDto userCreateDto) {
        log.info("Creating user with username: {}", userCreateDto.getEmail());


        keycloakClient.addUser(userCreateDto);


        saveUserToDatabase(userCreateDto);
    }

    public void changePassword(String email, String newPassword) {
        keycloakClient.changePassword(email, newPassword);
    }


    @Override
    public void addRoleToUser(String username, String roleName,String token) {
        keycloakClient.addRoleToUser(username, roleName);
        updateUserRolesInDatabase(username, roleName, true);
    }

    @Override
    public void removeRoleFromUser(String username, String roleName,String token) {
        keycloakClient.removeRoleFromUser(username, roleName);
        updateUserRolesInDatabase(username, roleName, false);
    }

    private void updateUserRolesInDatabase(String username, String roleName, boolean addRole) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            Role role = Role.valueOf(roleName.toUpperCase());
            if (addRole) {
                user.getRoles().add(role);
            } else {
                user.getRoles().remove(role);
            }
            userRepository.save(user);
        } else {

            throw new UserNotFoundException("User not found with username: " + username);

        }
    }

    @Override
    public void removeUser(String username,String token,Role role) {

        keycloakClient.removeUser(username);

        userRepository.deleteByUsername(username);
    }




    private void saveUserToDatabase(UserCreateDto userCreateDto) {

        User user = new User();
        user.setEmail(userCreateDto.getEmail());
        user.setUsername(userCreateDto.getUsername());
        user.setFirstName(userCreateDto.getFirstName());
        user.setLastName(userCreateDto.getLastName());
        user.setPassword(userCreateDto.getPassword());


        userRepository.save(user);
    }

    public void calculateAndSaveRate(String username,String token) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        int creditQuantity = user.getCreditQuantity();
        int creditRepaid = user.getCreditRepaid();
        int creditSum = user.getCreditSum();
        int repaidSum = user.getRepaidSum();

        double percentageCreditsPaid = (creditQuantity > 0) ? ((double) creditRepaid / creditQuantity) * 100 : 0;
        double percentageSumPaid = (creditSum > 0) ? ((double) repaidSum / creditSum) * 100 : 0;

        double rate = (percentageCreditsPaid + percentageSumPaid) / 2;

        user.setRate(rate);
        userRepository.save(user);
    }

    public String signIn(UserSignInDto userSignInDto) {
        return keycloakClient.signIn(userSignInDto);
    }


    @Override
    public List<CreditDto> getAvailableCredits(String token) {
        UserDto currentUser = getCurrentUser(token);
        if (currentUser == null) {
            log.error("User not found for token: {}", token);
            throw new RuntimeException("User not found");
        }

        double userRate = currentUser.getRate();
        log.info("Fetching credits for user with rate: {}", userRate);

        ResponseEntity<List<CreditDto>> response = creditFeignClient.getCredits(token);
        List<CreditDto> allCredits = response.getBody();

        if (allCredits == null) {
            log.warn("No credits found.");
            return Collections.emptyList();
        }

        log.info("Total credits received: {}", allCredits.size());

        List<CreditDto> availableCredits = allCredits.stream()
                .filter(credit -> isCreditAvailableForUser(userRate, credit))
                .collect(Collectors.toList());

        log.info("Available credits count: {}", availableCredits.size());
        return availableCredits;
    }

    private boolean isCreditAvailableForUser(double rate, CreditDto credit) {
        switch (credit.getName()) {
            case "decline":
                return rate < 25;
            case "small":
                return rate > 25;
            case "middle":
                return  rate > 50;
            case "big":
                return rate > 75;
            default:
                return false;
        }
    }

    @Override
    public void selectCreditForUser(Long creditId, String token) {
        UserDto userDto = getCurrentUser(token);
        if (userDto == null) {
            log.error("User not found for token: {}", token);
            throw new RuntimeException("User not found");
        }

        ResponseEntity<List<CreditDto>> creditResponse = creditFeignClient.getCredits(token);
        List<CreditDto> availableCredits = creditResponse.getBody();

        if (availableCredits == null || availableCredits.isEmpty()) {
            throw new RuntimeException("No available credits found");
        }

        CreditDto selectedCredit = availableCredits.stream()
                .filter(credit -> credit.getId().equals(creditId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Selected credit not found"));

        User user = userRepository.findByUsername(userDto.getUsername());
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        user.setCreditQuantity(user.getCreditQuantity() + 1);
        user.setCreditSum(user.getCreditSum() + selectedCredit.getMaxAmount());
        userRepository.save(user);

        log.info("Credit with ID {} added to user with username {}", creditId, userDto.getUsername());
    }

    @Override
    public void payTotalCredit(String token, int paymentAmount) {

        UserDto userDto = getCurrentUser(token);

        if (userDto == null) {
            throw new RuntimeException("User not found");
        }


        if (userDto.getCreditSum() <= 0) {
            throw new RuntimeException("No outstanding credit to pay");
        }

        if (paymentAmount <= 0) {
            throw new RuntimeException("Payment amount must be greater than zero");
        }


        int updatedCreditSum = Math.max(0, userDto.getCreditSum() - paymentAmount);


        int updatedRepaidSum = userDto.getRepaidSum() + paymentAmount;

        userDto.setCreditSum(updatedCreditSum);
        userDto.setRepaidSum(updatedRepaidSum);

        User user = userMapper.toEntity(userDto);
        userRepository.save(user);

        log.info("User {} successfully paid {} towards total credit. Total repaid: {}",
                userDto.getUsername(), paymentAmount, updatedRepaidSum);
    }



}
