package com.example.clearsolutionstest.service;

import com.example.clearsolutionstest.dto.UserDto;
import com.example.clearsolutionstest.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser() {
        when(userDto.getEmail()).thenReturn("test@example.com");
        when(userDto.getBirthDate()).thenReturn(LocalDate.of(2000, 1, 1));

        UserDto createdUser = userService.createUser(userDto);

        assertEquals("test@example.com", createdUser.getEmail());
    }

    @Test
    void testUpdateUser() {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setEmail("test@example.com");
        userDto.setFirstName("Joe");
        userDto.setLastName("Doe");
        userDto.setBirthDate(LocalDate.of(2000, 1, 1));

        userService.createUser(userDto);

        UserDto updatedUser = userService.updateUser(userDto.getId(), userDto);

        assertEquals("test@example.com", updatedUser.getEmail());
    }

    @Test
    void testUpdateUserFields() {
        Long id = 1L;
        Map<String, Object> fields = new HashMap<>();
        fields.put("email", "newemail@example.com");
        fields.put("firstName", "NewFirstName");
        fields.put("lastName", "NewLastName");

        UserDto userDto = new UserDto();
        userDto.setId(id);
        userDto.setEmail("newemail@example.com");
        userDto.setFirstName("NewFirstName");
        userDto.setLastName("NewLastName");
        userDto.setBirthDate(LocalDate.of(2000, 1, 1));

        userService.createUser(userDto);

        UserDto updatedUser = userService.updateUserFields(id, fields);

        assertEquals("newemail@example.com", updatedUser.getEmail());
        assertEquals("NewFirstName", updatedUser.getFirstName());
        assertEquals("NewLastName", updatedUser.getLastName());
    }


    @Test
    void testDeleteUser() {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setEmail("test@example.com");
        userDto.setFirstName("Joe");
        userDto.setLastName("Doe");
        userDto.setBirthDate(LocalDate.of(2000, 1, 1));

        userService.createUser(userDto);
        userService.deleteUser(1L);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.updateUser(1L, userDto);
        });

        String expectedMessage = "User not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testSearchUser() {
        when(userDto.getEmail()).thenReturn("test@example.com");
        when(userDto.getBirthDate()).thenReturn(LocalDate.of(2000, 1, 1));

        userService.createUser(userDto);
        List<UserDto> users = userService.searchUser(LocalDate.of(2000, 1, 1),
                LocalDate.of(2000, 1, 1));

        assertEquals(1, users.size());
        assertEquals("test@example.com", users.get(0).getEmail());
    }
}
