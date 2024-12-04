package com.example.App_www.dto;


import com.example.App_www.model.Enums.DzienTygodnia;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterPlanTreningowyRequestDto {
    private List<DzienTygodnia> dzienTygodnia;
    private String cwiczenia;
    private String iloscSerii;
    private String zakresPowtorzen;
    private String obciazenie;
    private String przerwy;

}
