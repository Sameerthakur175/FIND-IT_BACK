package com.example.LostAndFound2.DTO;

import com.example.LostAndFound2.Enums.ItemStatus;
import com.example.LostAndFound2.Enums.ItemType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ItemResponseDto {
    private Long id;
    private String description;
    private String category;
    private String location;
    private LocalDate eventDateTime;
    private ItemType itemType;
    private ItemStatus status;
    private String reportedByEmail;
}