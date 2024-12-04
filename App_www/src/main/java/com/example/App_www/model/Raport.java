package com.example.App_www.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Raport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "klient_id", nullable = false)
    private Klient klient;

    @ManyToOne
    @JoinColumn(name = "trener_id", nullable = false)
    private Trener trener;

    private int numerTygodnia;
    private double waga;
    private double obwodBiodra;
    private double obwodPas;
    private double obwodTalia;
    private double obwodUdo;
    private double obwodBiceps;

    @Column(name = "odczuwanie_treningu")
    private int odczuwanieTreningu;

    @Column(name = "chec_podjarania")
    private int checPodjarania;

    @Column(name = "jakosc_snu")
    private int jakoscSnu;

    @Column(name = "samopoczucie")
    private int samopoczucie;

    @Column(name = "poziom_energii")
    private int poziomEnergii;

    private LocalDate dataRaportu;

    private String klientImie;
    private String klientNazwisko;

}