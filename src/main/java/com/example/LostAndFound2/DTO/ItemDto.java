package com.example.LostAndFound2.DTO;

import com.example.LostAndFound2.Enums.ItemType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {

    private String description;

    private String category;

    private String location;

    private LocalDate eventDateTime;

    @Enumerated(value = EnumType.STRING)
    private ItemType itemType;

    private String reportedBy;

}
