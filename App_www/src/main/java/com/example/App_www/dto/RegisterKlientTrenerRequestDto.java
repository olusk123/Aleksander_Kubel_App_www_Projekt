package com.example.App_www.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class RegisterKlientTrenerRequestDto {
    private Long id;
    private String imie;
    private String nazwisko;
    private String daneKontaktowe;
}
