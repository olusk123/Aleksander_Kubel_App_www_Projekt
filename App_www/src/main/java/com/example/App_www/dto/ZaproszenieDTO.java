package com.example.App_www.dto;


import com.example.App_www.model.Enums.StatusZaproszenia;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ZaproszenieDTO {
    private Long id;
    private StatusZaproszenia status;
    private String trenerImie;
    private String trenerNazwisko;
    private String klientImie;
    private String klientNazwisko;
}
