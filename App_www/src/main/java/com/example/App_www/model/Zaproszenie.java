package com.example.App_www.model;


import com.example.App_www.model.Enums.StatusZaproszenia;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "zaproszenie")
public class Zaproszenie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trener_id", nullable = false)
    private Trener trener;

    @ManyToOne
    @JoinColumn(name = "klient_id", nullable = false)
    private Klient klient;

    @Enumerated(EnumType.STRING)
    private StatusZaproszenia status;

    private LocalDateTime dataUtworzenia = LocalDateTime.now();
}