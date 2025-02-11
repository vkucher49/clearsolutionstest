package com.example.clearsolutionstest.controller;

import com.example.clearsolutionstest.dto.UserDto;
import com.example.clearsolutionstest.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateUser() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setEmail("test@example.com");
        userDto.setFirstName("Joe");
        userDto.setLastName("Doe");
        userDto.setBirthDate(LocalDate.of(2000, 1, 1));

        when(userService.createUser(userDto)).thenReturn(userDto);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateUser() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setEmail("test@example.com");
        userDto.setFirstName("Joe");
        userDto.setLastName("Doe");
        userDto.setBirthDate(LocalDate.of(2000, 1, 1));

        when(userService.updateUser(userDto.getId(), userDto)).thenReturn(userDto);

        mockMvc.perform(put("/users/" + userDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk());
    }

    @Test
    void testModifyUserDetails() throws Exception {
        Long id = 1L;
        UserDto userDto = new UserDto();
        userDto.setId(id);
        userDto.setEmail("newemail@example.com");
        userDto.setFirstName("NewFirstName");
        userDto.setLastName("NewLastName");
        userDto.setBirthDate(LocalDate.of(2000,1,1));

        when(userService.modifyUserDetails(id, userDto)).thenReturn(userDto);

        mockMvc.perform(patch("/users/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(userDto.getEmail())))
                .andExpect(jsonPath("$.firstName", is(userDto.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(userDto.getLastName())))
                .andExpect(jsonPath("$.birthDate", is(userDto.getBirthDate().toString())))
                .andExpect(jsonPath("$.address", is(userDto.getAddress())))
                .andExpect(jsonPath("$.phoneNumber", is(userDto.getPhoneNumber())));
    }


    @Test
    void testDeleteUser() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/users/" + id))
                .andExpect(status().isOk());
    }

    @Test
    void testSearchUser() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setEmail("test@example.com");
        userDto.setFirstName("Joe");
        userDto.setLastName("Doe");
        userDto.setBirthDate(LocalDate.of(2000, 1, 1));

        when(userService.searchUser(userDto.getBirthDate(), userDto.getBirthDate()))
                .thenReturn(Collections.singletonList(userDto));

        mockMvc.perform(get("/users")
                .param("from", "2000-01-01")
                .param("to", "2000-01-01"))
                .andExpect(status().isOk());
    }
}
