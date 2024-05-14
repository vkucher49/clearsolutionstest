package com.example.clearsolutionstest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;
import java.time.LocalDate;

@Data
public class UserDto {

    @NotNull(message = "Id can't be null")
    private Long id;

    @Email(message = "Email should be valid.")
    private String email;

    @NotBlank(message = "First name is required.")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    private String lastName;

    @Past(message = "Birth date should be in the past.")
    private LocalDate birthDate;

    private String address;

    private String phoneNumber;
}
