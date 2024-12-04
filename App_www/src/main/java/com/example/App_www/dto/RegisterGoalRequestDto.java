package com.example.App_www.dto;


import com.example.App_www.model.Enums.GoalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterGoalRequestDto {
    private Long KlientId; // ID klienta
    private String opis;
    private GoalType goalType;
    private LocalDate startDate;
    private LocalDate endDate;
}