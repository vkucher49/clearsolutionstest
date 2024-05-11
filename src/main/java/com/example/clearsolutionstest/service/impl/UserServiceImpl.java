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

    private Map<String, UserDto> users = new HashMap<>();

    @Override
    public UserDto createUser(UserDto userDto) {
        if (users.containsKey(userDto.getEmail())) {
            throw new IllegalArgumentException("User with this email already exists");
        }

        if (Period.between(userDto.getBirthDate(), LocalDate.now()).getYears() < minAge) {
            throw new IllegalArgumentException("User birth date must be at least " + minAge + " years old");
        }

        users.put(userDto.getEmail(), userDto);
        return userDto;
    }

    @Override
    public UserDto updateUser(String email,UserDto userDto) {
        if (!users.containsKey(email)) {
            throw new IllegalArgumentException("User not found");
        }
        users.put(email, userDto);
        return userDto;
    }

    @Override
    public void deleteUser(String email) {
        if (!users.containsKey(email)) {
            throw new IllegalArgumentException("User not found");
        }

        users.remove(email);
    }

    @Override
    public List<UserDto> searchUser(LocalDate from, LocalDate to) {
        return users.values().stream()
                .filter(user -> !user.getBirthDate().isBefore(from) && !user.getBirthDate().isAfter(to))
                .collect(Collectors.toList());
    }
}
