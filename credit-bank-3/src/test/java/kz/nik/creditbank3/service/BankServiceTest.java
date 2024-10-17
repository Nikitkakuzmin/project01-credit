package kz.nik.creditbank3.service;
import static junit.framework.TestCase.*;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import kz.nik.creditbank3.dto.UserCreateDto;
import kz.nik.creditbank3.dto.UserInfoDto;
import kz.nik.creditbank3.mapper.UserMapper;
import kz.nik.creditbank3.model.User;
import kz.nik.creditbank3.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@Transactional
public class BankServiceTest {
    @Autowired
    private UserService userService;



    @MockBean
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;



    @Test
    void testAddUser() {
        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setFirstName("John");
        userCreateDto.setLastName("Doe");
        userCreateDto.setEmail("john.doe@example.com");
        userCreateDto.setUsername("johndoe");
        userCreateDto.setPassword("password");

        userService.addUser(userCreateDto);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();

        assertEquals("John", savedUser.getFirstName());
        assertEquals("Doe", savedUser.getLastName());
        assertEquals("john.doe@example.com", savedUser.getEmail());
        assertEquals("johndoe", savedUser.getUsername());
        assertEquals("password", savedUser.getPassword());
        assertTrue(savedUser.getCreditQuantity() >= 1 && savedUser.getCreditQuantity() <= 10);
        assertTrue(savedUser.getCreditRepaid() >= 0 && savedUser.getCreditRepaid() <= savedUser.getCreditQuantity());
        assertTrue(savedUser.getCreditSum() >= (savedUser.getCreditQuantity() * 1000) && savedUser.getCreditSum() < (savedUser.getCreditQuantity() * 1000) + 1000);
        assertTrue(savedUser.getRepaidSum() >= (savedUser.getCreditRepaid() * 1000) && savedUser.getRepaidSum() < (savedUser.getCreditRepaid() * 1000) + 1000);
    }

    @Test
    public void testGetUserByEmail() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setUsername("testuser");

        when(userRepository.findByEmail("test@example.com")).thenReturn(user);

        UserInfoDto userInfoDto = userService.getUserByEmail("test@example.com");

        assertNotNull(userInfoDto);
    }

}
