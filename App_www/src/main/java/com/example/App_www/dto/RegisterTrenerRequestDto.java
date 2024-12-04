package com.example.App_www.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class RegisterTrenerRequestDto {
    private String email;
    private String password;
    private String imie;
    private String nazwisko;

}
