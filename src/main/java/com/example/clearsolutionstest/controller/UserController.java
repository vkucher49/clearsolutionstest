package com.example.clearsolutionstest.controller;

import com.example.clearsolutionstest.dto.UserDto;
import com.example.clearsolutionstest.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public UserDto createUser(@RequestBody @Valid UserDto userDto) {
        return userService.createUser(userDto);
    }

    @PutMapping("/{email}")
    public UserDto updateUser(@PathVariable String email, @RequestBody @Valid UserDto userDto) {
        return userService.updateUser(email, userDto);
    }

    @DeleteMapping("/{email}")
    public void deleteUser(@PathVariable String email) {
        userService.deleteUser(email);
    }

    @GetMapping
    public List<UserDto> getUserByBirthDate(@RequestParam LocalDate from, @RequestParam LocalDate to) {
        return userService.searchUser(from, to);
    }
}
