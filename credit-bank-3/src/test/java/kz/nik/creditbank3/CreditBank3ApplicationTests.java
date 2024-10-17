package kz.nik.creditbank3;

import kz.nik.creditbank3.dto.UserInfoDto;
import kz.nik.creditbank3.mapper.UserMapper;
import kz.nik.creditbank3.model.User;
import kz.nik.creditbank3.repository.UserRepository;
import kz.nik.creditbank3.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CreditBank3ApplicationTests {
    @Test
    void contextLoads() {
    }

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserServiceImpl userServiceImpl;




    @Test
    public void testToDto() {
        User user = new User();
        user.setEmail("user@example.com");
        user.setPassword("password");
        user.setUsername("John");
        user.setFirstName("John");
        user.setLastName("John");
        user.setCreditSum(215464);
        user.setCreditRepaid(4);
        user.setCreditQuantity(6);
        user.setRepaidSum(65465);

        UserInfoDto userInfoDto = userMapper.toDto(user);

        assertNotNull(userInfoDto);
        assertEquals(user.getEmail(), userInfoDto.getEmail());
        assertEquals(user.getPassword(), userInfoDto.getPassword());
        assertEquals(user.getFirstName(), userInfoDto.getFirstName());
        assertEquals(user.getLastName(), userInfoDto.getLastName());
        assertEquals(user.getCreditSum(), userInfoDto.getCreditSum());
        assertEquals(user.getCreditRepaid(), userInfoDto.getCreditRepaid());
        assertEquals(user.getCreditQuantity(), userInfoDto.getCreditQuantity());
        assertEquals(user.getRepaidSum(), userInfoDto.getRepaidSum());


    }

    @Test
    public void testToEntity() {
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setEmail("user@example.com");
        userInfoDto.setPassword("password");
        userInfoDto.setUsername("John");
        userInfoDto.setFirstName("John");
        userInfoDto.setLastName("John");
        userInfoDto.setCreditSum(215464);
        userInfoDto.setCreditRepaid(4);
        userInfoDto.setCreditQuantity(6);
        userInfoDto.setRepaidSum(65465);

        User user = userMapper.toEntity(userInfoDto);

        assertNotNull(user);
        assertEquals(userInfoDto.getEmail(), user.getEmail());
        assertEquals(userInfoDto.getPassword(), user.getPassword());
        assertEquals(userInfoDto.getFirstName(), user.getFirstName());
        assertEquals(userInfoDto.getLastName(), user.getLastName());
        assertEquals(userInfoDto.getCreditSum(), user.getCreditSum());
        assertEquals(userInfoDto.getCreditRepaid(), user.getCreditRepaid());
        assertEquals(userInfoDto.getCreditQuantity(), user.getCreditQuantity());
        assertEquals(userInfoDto.getRepaidSum(), user.getRepaidSum());
    }

    @Test
    public void testToDtoList() {
        User user = new User();
        user.setEmail("user@example.com");
        user.setPassword("password");
        user.setUsername("John");
        user.setFirstName("John");
        user.setLastName("John");
        user.setCreditSum(215464);
        user.setCreditRepaid(4);
        user.setCreditQuantity(6);
        user.setRepaidSum(65465);

        User user2 = new User();
        user2.setEmail("user2@example.com");
        user2.setPassword("password2");
        user2.setUsername("John2");
        user2.setFirstName("John2");
        user2.setLastName("John2");
        user2.setCreditSum(2154642);
        user2.setCreditRepaid(42);
        user2.setCreditQuantity(62);
        user2.setRepaidSum(654652);

        List<UserInfoDto> userInfoDtoList = userMapper.toDtoList(Arrays.asList(user, user2));

        assertNotNull(userInfoDtoList);
        assertEquals(2, userInfoDtoList.size());
        assertEquals(user.getUsername(), userInfoDtoList.get(0).getUsername());
        assertEquals(user2.getUsername(), userInfoDtoList.get(1).getUsername());
    }
}