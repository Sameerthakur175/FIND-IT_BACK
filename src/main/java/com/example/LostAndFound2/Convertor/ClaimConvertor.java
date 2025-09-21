package com.example.LostAndFound2.Convertor;


import com.example.LostAndFound2.DTO.ClaimResponseDto;
import com.example.LostAndFound2.Model.Claim;

public class ClaimConvertor {
    public static ClaimResponseDto claimToClaimResponseDto(Claim claim) {
        return ClaimResponseDto.builder()
                .id(claim.getId())
                .itemId(claim.getItem().getId())
                .itemDescription(claim.getItem().getDescription())
                .claimantEmail(claim.getClaimant().getEmailId())
                .claimantName(claim.getClaimant().getName())
                .status(claim.getStatus())
                .claimDate(claim.getClaimDate())
                .build();
    }
}
