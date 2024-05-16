package com.example.clearsolutionstest.service.impl;

import com.example.clearsolutionstest.dto.UserDto;
import com.example.clearsolutionstest.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Value("${user.min.age}")
    private int minAge;

    private Map<Long, UserDto> users = new HashMap<>();

    @Override
    public UserDto createUser(UserDto userDto) {
        if (users.containsKey(userDto.getId())) {
            throw new IllegalArgumentException("User with this email already exists");
        }

        if (Period.between(userDto.getBirthDate(), LocalDate.now()).getYears() < minAge) {
            throw new IllegalArgumentException("User birth date must be at least " + minAge + " years old");
        }

        users.put(userDto.getId(), userDto);
        return userDto;
    }

    @Override
    public UserDto updateUser(Long id,UserDto userDto) {
        if (!users.containsKey(id)) {
            throw new IllegalArgumentException("User not found");
        }
        users.put(id, userDto);
        return userDto;
    }

    @Override
    public UserDto modifyUserDetails(Long id, UserDto userDto) {
        UserDto existingUserDto = users.get(id);
        if (existingUserDto == null) {
            throw new IllegalArgumentException("User not found");
        }

        existingUserDto.setEmail(userDto.getEmail());
        existingUserDto.setFirstName(userDto.getFirstName());
        existingUserDto.setLastName(userDto.getLastName());
        existingUserDto.setBirthDate(userDto.getBirthDate());
        existingUserDto.setAddress(userDto.getAddress());
        existingUserDto.setPhoneNumber(userDto.getPhoneNumber());

        return existingUserDto;
    }

    @Override
    public void deleteUser(Long id) {
        if (!users.containsKey(id)) {
            throw new IllegalArgumentException("User not found");
        }

        users.remove(id);
    }

    @Override
    public List<UserDto> searchUser(LocalDate from, LocalDate to) {
        return users.values().stream()
                .filter(user -> !user.getBirthDate().isBefore(from) && !user.getBirthDate().isAfter(to))
                .collect(Collectors.toList());
    }
}
