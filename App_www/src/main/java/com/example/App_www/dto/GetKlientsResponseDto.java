package com.example.App_www.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetKlientsResponseDto {
    private Long klientId;
    private String klientImie;
    private String klientNazwisko;
}
