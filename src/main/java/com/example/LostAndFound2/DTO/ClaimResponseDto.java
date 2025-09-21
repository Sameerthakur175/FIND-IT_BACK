package com.example.LostAndFound2.DTO;

import com.example.LostAndFound2.Enums.ClaimStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClaimResponseDto {
    private Long id;
    private Long itemId;
    private String itemDescription;
    private String claimantEmail;
    private String claimantName;
    private ClaimStatus status;
    private LocalDateTime claimDate;
}
