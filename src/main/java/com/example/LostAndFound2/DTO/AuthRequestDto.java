package com.example.LostAndFound2.DTO;

import lombok.Data;

@Data
public class AuthRequestDto {
    private String email;
    private String password;
}