package com.example.App_www.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {

    private String token;
    private String userType;
    private Long userId;

}
