package com.example.LostAndFound2.DTO;

import lombok.Data;

@Data
public class RegisterRequestDto {
    private String email;
    private String name;
    private String mobileNo;
    private String password;
}
