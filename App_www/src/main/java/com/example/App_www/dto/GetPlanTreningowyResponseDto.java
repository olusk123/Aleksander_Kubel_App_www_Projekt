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
public class GetPlanTreningowyResponseDto {
    private Long planTreningowyId;
    private List<DzienTygodnia> PlanTreningowyDzienTygodnia;
    private String PlanTreningowyCwiczenia;
    private String PlanTreningowyIloscSerii;
    private String PlanTreningowyZakresPowtorzen;
    private String PlanTreningowyObciazenie;
    private String PlanTreningowyPrzerwy;

}
