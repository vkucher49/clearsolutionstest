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
    public UserDto updateUserFields(Long id, Map<String, Object> fields) {
        UserDto userDto = users.get(id);
        if (userDto == null) {
            throw new IllegalArgumentException("User not found");
        }

        for (String field : fields.keySet()) {
            switch (field) {
                case "email":
                    userDto.setEmail((String) fields.get(field));
                    break;
                case "firstName":
                    userDto.setFirstName((String) fields.get(field));
                    break;
                case "lastName":
                    userDto.setLastName((String) fields.get(field));
                    break;
                case "birthDate":
                    userDto.setBirthDate(LocalDate.parse((String) fields.get(field)));
                    break;
                case "address":
                    userDto.setAddress((String) fields.get(field));
                    break;
                case "phoneNumber":
                    userDto.setPhoneNumber((String) fields.get(field));
                    break;
                default:
                    throw new IllegalArgumentException("Field " + field + " not found");
            }
        }
        return userDto;
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
