package com.example.App_www.model;

import com.example.App_www.model.Enums.GoalType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Goal")
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String opis;

    @Enumerated(EnumType.STRING)
    private GoalType goalType;

    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "klient_id")
    private Klient klient;


    public void setKlient(Klient klient) {
        if (this.klient != null) {
            this.klient.getGoals().remove(this);
        }
        this.klient = klient;
        if (klient != null && !klient.getGoals().contains(this)) {
            klient.getGoals().add(this);
        }
    }
}
