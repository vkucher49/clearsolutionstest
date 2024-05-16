package com.example.clearsolutionstest.service;

import com.example.clearsolutionstest.dto.UserDto;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface UserService {

    UserDto createUser(UserDto userDto);

    UserDto updateUser(Long id,UserDto userDto);

    UserDto modifyUserDetails(Long id, UserDto userDto);

    void deleteUser(Long id);

    List<UserDto> searchUser(LocalDate from, LocalDate to);
}
