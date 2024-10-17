package kz.nik.credituserservice;

import kz.nik.credituserservice.dto.CreditDto;
import kz.nik.credituserservice.dto.UserDto;
import kz.nik.credituserservice.mapper.UserMapper;
import kz.nik.credituserservice.model.User;
import kz.nik.credituserservice.repository.UserRepository;
import kz.nik.credituserservice.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CreditUserServiceApplicationTests {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserServiceImpl userServiceImpl;


    @Test
    public void testToDto() {

        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setUsername("test");
        user.setPassword("asd");
        user.setFirstName("test");
        user.setLastName("test");
        user.setRate(65);
        user.setCreditRepaid(56);
        user.setCreditQuantity(60);
        user.setCreditSum(656464);
        user.setRepaidSum(56454);


        UserDto userDto = userMapper.toDto(user);


        assertNotNull(userDto);
        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getEmail(), userDto.getEmail());
        assertEquals(user.getUsername(), userDto.getUsername());
        assertEquals(user.getPassword(), userDto.getPassword());
        assertEquals(user.getFirstName(), userDto.getFirstName());
        assertEquals(user.getLastName(), userDto.getLastName());
        assertEquals(user.getRate(), userDto.getRate());
        assertEquals(user.getCreditRepaid(), userDto.getCreditRepaid());
        assertEquals(user.getCreditQuantity(), userDto.getCreditQuantity());
        assertEquals(user.getCreditSum(), userDto.getCreditSum());
        assertEquals(user.getRepaidSum(), userDto.getRepaidSum());

    }

    @Test
    public void testToEntity() {

        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setEmail("user@example.com");
        userDto.setUsername("user");
        userDto.setPassword("password");
        userDto.setFirstName("aSFASDF");
        userDto.setLastName("afsdfs");
        userDto.setRate(45);
        userDto.setCreditRepaid(45);
        userDto.setCreditQuantity(52);
        userDto.setCreditSum(5464654);
        userDto.setRepaidSum(564654);




        User user = userMapper.toEntity(userDto);


        assertNotNull(user);
        assertEquals(userDto.getId(), user.getId());
        assertEquals(userDto.getEmail(), user.getEmail());
        assertEquals(userDto.getUsername(), user.getUsername());
        assertEquals(userDto.getPassword(), user.getPassword());
        assertEquals(userDto.getFirstName(), user.getFirstName());
        assertEquals(userDto.getLastName(), user.getLastName());
        assertEquals(userDto.getRate(), user.getRate());
        assertEquals(userDto.getCreditRepaid(), user.getCreditRepaid());
        assertEquals(userDto.getCreditQuantity(), user.getCreditQuantity());
        assertEquals(userDto.getCreditSum(), user.getCreditSum());
        assertEquals(userDto.getRepaidSum(), user.getRepaidSum());

    }

    @Test
    public void testToDtoList() {

        User user1 = new User();
        user1.setId(1L);
        user1.setEmail("test@example.com");
        user1.setUsername("test");
        user1.setPassword("asd");
        user1.setFirstName("test");
        user1.setLastName("test");
        user1.setRate(65);
        user1.setCreditRepaid(56);
        user1.setCreditQuantity(60);
        user1.setCreditSum(656464);
        user1.setRepaidSum(56454);

        User user2 = new User();
        user2.setId(2L);
        user2.setEmail("asdasd@example.com");
        user2.setUsername("tryrt");
        user2.setPassword("vcbxc");
        user2.setFirstName("ewrr");
        user2.setLastName("hfghdf");
        user2.setRate(36);
        user2.setCreditRepaid(23);
        user2.setCreditQuantity(24);
        user2.setCreditSum(25004);
        user2.setRepaidSum(24500);

        List<UserDto> userDtoList = userMapper.toDtoList(Arrays.asList(user1, user2));


        assertNotNull(userDtoList);
        assertEquals(2, userDtoList.size());
        assertEquals(user1.getId(), userDtoList.get(0).getId());
        assertEquals(user2.getId(), userDtoList.get(1).getId());
    }


}