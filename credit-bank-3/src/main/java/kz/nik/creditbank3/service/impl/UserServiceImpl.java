package kz.nik.creditbank3.service.impl;

import kz.nik.creditbank3.dto.CreditInfoDto;
import kz.nik.creditbank3.dto.UserCreateDto;
import kz.nik.creditbank3.dto.UserInfoDto;
import kz.nik.creditbank3.mapper.UserMapper;
import kz.nik.creditbank3.model.User;
import kz.nik.creditbank3.repository.UserRepository;
import kz.nik.creditbank3.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    public final UserRepository userRepository;
    public final UserMapper userMapper;


    public void addUser(UserCreateDto userCreateDto) {
        Random random = new Random();
        int creditQuantity = random.nextInt(10) + 1;
        int creditRepaid = random.nextInt(creditQuantity + 1);
        int creditSum = (creditQuantity * 1000) + random.nextInt(1000);
        int repaidSum = (creditRepaid * 1000) + random.nextInt(1000);

        User user = new User();
        user.setFirstName(userCreateDto.getFirstName());
        user.setLastName(userCreateDto.getLastName());
        user.setEmail(userCreateDto.getEmail());
        user.setUsername(userCreateDto.getUsername());
        user.setPassword(userCreateDto.getPassword());
        user.setCreditQuantity(creditQuantity);
        user.setCreditRepaid(creditRepaid);
        user.setCreditSum(creditSum);
        user.setRepaidSum(repaidSum);

        userRepository.save(user);
    }
    public UserInfoDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return userMapper.toDto(user);
    }
    @Override
    public CreditInfoDto getCreditInfo(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return CreditInfoDto.builder()
                .creditQuantity(user.getCreditQuantity())
                .creditRepaid(user.getCreditRepaid())
                .creditSum(user.getCreditSum())
                .repaidSum(user.getRepaidSum())
                .build();
    }
}