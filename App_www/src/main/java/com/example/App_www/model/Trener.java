package com.example.App_www.model;

import com.example.App_www.model.Enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trener")
@DiscriminatorValue("TRENER")

public class Trener extends User{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String imie;
    private String nazwisko;

    public Trener(String email, String fullName, String password, String imie, String nazwisko,Role role) {
        super(email, fullName, password,role);
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.setRole(Role.TRENER);
    }

    @OneToMany(mappedBy = "trener")
    @JsonIgnore
    private List<Klient> podopieczni = new ArrayList<>();

    @OneToMany(mappedBy = "trener", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Zaproszenie> zaproszenia;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "trener_plan_treningowy",
            joinColumns = @JoinColumn(name = "trener_id"),
            inverseJoinColumns = @JoinColumn(name = "plan_treningowy_id")
    )
    private List<PlanTreningowy> planyTreningowe = new ArrayList<>();

}