package com.example.App_www.dto;


import com.example.App_www.model.Enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class RegisterUserDto {
    private String email;

    private String password;

    private String fullName;

    private String imie;

    private String nazwisko;

    private Role userType;

}