package com.example.clearsolutionstest.service;

import com.example.clearsolutionstest.dto.UserDto;
import java.time.LocalDate;
import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userDto);

    UserDto updateUser(String email,UserDto userDto);

    void deleteUser(String email);

    List<UserDto> searchUser(LocalDate from, LocalDate to);
}
