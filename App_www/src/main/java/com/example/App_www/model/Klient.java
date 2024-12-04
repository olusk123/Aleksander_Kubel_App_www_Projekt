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
@Table(name = "klient")
@DiscriminatorValue("KLIENT")
public class Klient extends User{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;
    private String imie;
    private String nazwisko;
    private String daneKontaktowe;

    public Klient(String email, String fullName, String password, String imie, String nazwisko,Role role) {
        super(email, fullName, password, role);
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.setRole(Role.KLIENT);
    }

    @ManyToOne
    @JoinColumn(name = "trener_id")
    private Trener trener;


    @OneToMany(mappedBy = "klient", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Goal> goals = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "klient_plan_treningowy",
            joinColumns = @JoinColumn(name = "klient_id"),
            inverseJoinColumns = @JoinColumn(name = "plan_treningowy_id")
    )
    private List<PlanTreningowy> planyTreningowe = new ArrayList<>();

    @OneToMany(mappedBy = "klient", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Zaproszenie> zaproszenia;

    public void addPlanTreningowy(PlanTreningowy plan) {
        this.planyTreningowe.add(plan);
        plan.getKlienci().add(this);
    }
    public void addGoal(Goal goal) {
        this.goals.add(goal);
        goal.setKlient(this);
    }


}
