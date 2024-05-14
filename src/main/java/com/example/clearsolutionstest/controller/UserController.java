package com.example.clearsolutionstest.controller;

import com.example.clearsolutionstest.dto.UserDto;
import com.example.clearsolutionstest.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public UserDto createUser(@RequestBody @Valid UserDto userDto) {
        return userService.createUser(userDto);
    }

    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable Long id, @RequestBody @Valid UserDto userDto) {
        return userService.updateUser(id, userDto);
    }

    @PatchMapping("/{id}")
    public UserDto updateUserFields(@PathVariable Long id, @RequestBody Map<String, Object> fields) {
        return userService.updateUserFields(id, fields);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping
    public List<UserDto> getUserByBirthDate(@RequestParam LocalDate from, @RequestParam LocalDate to) {
        return userService.searchUser(from, to);
    }
}
