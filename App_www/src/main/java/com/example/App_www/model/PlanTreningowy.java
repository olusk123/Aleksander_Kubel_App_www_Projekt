package com.example.App_www.model;

import com.example.App_www.model.Enums.DzienTygodnia;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "plan_treningowy")
public class PlanTreningowy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private List<DzienTygodnia> dzienTygodnia;
    private String cwiczenia;
    private String iloscSerii;
    private String zakresPowtorzen;
    private String obciazenie;
    private String przerwy;

    @ManyToMany(mappedBy = "planyTreningowe")
    @JsonIgnore
    private List<Klient> klienci = new ArrayList<>();


    @ManyToMany(mappedBy = "planyTreningowe")
    @JsonIgnore
    private List<Trener> Trenerzy = new ArrayList<>();

}