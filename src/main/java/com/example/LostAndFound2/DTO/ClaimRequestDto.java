package com.example.LostAndFound2.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClaimRequestDto {

    private Long itemId;


    private String claimantEmailId;
}
