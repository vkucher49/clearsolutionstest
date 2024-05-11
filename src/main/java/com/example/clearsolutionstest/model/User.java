package com.example.clearsolutionstest.model;

import lombok.Data;
import java.time.LocalDate;

@Data
public class User {

    private String email;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
}
