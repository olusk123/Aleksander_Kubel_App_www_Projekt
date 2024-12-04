package com.example.App_www.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class RegisterRaportRequestDto {

    private Long klientId;
    private Long trenerId;
    private int numerTygodnia;
    private double waga;
    private double obwodBiodra;
    private double obwodPas;
    private double obwodTalia;
    private double obwodUdo;
    private double obwodBiceps;
    private int odczuwanieTreningu;
    private int checPodjarania;
    private int jakoscSnu;
    private int samopoczucie;
    private int poziomEnergii;
    private LocalDate dataRaportu;
    private String klientImie;
    private String klientNazwisko;
}
