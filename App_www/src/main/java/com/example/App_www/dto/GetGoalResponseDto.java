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
public class GetGoalResponseDto {
    private Long goalId;
    private String goalOpis;
    private GoalType goalType;
    private LocalDate goalStartDate;
    private LocalDate goalEndDate;

}
