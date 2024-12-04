package com.example.App_www.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class RaportDto {
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
