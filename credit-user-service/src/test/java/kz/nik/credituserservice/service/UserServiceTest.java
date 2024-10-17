package kz.nik.credituserservice.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import kz.nik.credituserservice.dto.UserDto;
import kz.nik.credituserservice.mapper.UserMapper;
import kz.nik.credituserservice.model.User;
import kz.nik.credituserservice.repository.UserRepository;
import kz.nik.credituserservice.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Test
    public void calculateAndSaveRate() {

        User user = new User();
        user.setUsername("testUser");
        user.setCreditQuantity(5);
        user.setCreditRepaid(3);
        user.setCreditSum(10000);
        user.setRepaidSum(5000);

        when(userRepository.findByUsername("testUser")).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.calculateAndSaveRate("testUser", "dummyToken");

        assertEquals(55.0, user.getRate(), 0.001);
        verify(userRepository).save(user);
    }


    @Test
    public void testGetUsers() {
        List<User> users = Arrays.asList(
                User.builder().id(1L).email("test1@example.com").username("user1").build(),
                User.builder().id(2L).email("test2@example.com").username("user2").build()
        );

        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.toDtoList(users)).thenReturn(Arrays.asList(
                UserDto.builder().id(1L).email("test1@example.com").username("user1").build(),
                UserDto.builder().id(2L).email("test2@example.com").username("user2").build()
        ));

        List<UserDto> result = userService.getUsers("dummy-token");

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("user1", result.get(0).getUsername());
        assertEquals("user2", result.get(1).getUsername());
    }

    @Test
    public void testGetUser_Success() {
        Long id = 1L;
        User user = User.builder().id(id).email("test@example.com").username("testuser").build();
        UserDto userDto = UserDto.builder().id(id).email("test@example.com").username("testuser").build();

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDto);

        UserDto result = userService.getUser(id, "dummy-token");

        assertNotNull(result);
        assertEquals(userDto, result);
    }

    @Test
    public void testGetUser_NotFound() {
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        UserDto result = userService.getUser(id, "dummy-token");

        assertNull(result);
    }
}
