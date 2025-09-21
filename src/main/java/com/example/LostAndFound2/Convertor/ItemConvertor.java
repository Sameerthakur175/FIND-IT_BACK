package com.example.LostAndFound2.Convertor;

import com.example.LostAndFound2.DTO.ItemDto;
import com.example.LostAndFound2.DTO.ItemResponseDto;
import com.example.LostAndFound2.Model.Item;
import com.example.LostAndFound2.Model.User;
import com.example.LostAndFound2.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ItemConvertor {

    @Autowired
    UserRepository userRepository;

    public Item itemDtoToItem(ItemDto itemDto, String userEmail){
        User user = userRepository.findById(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + userEmail));

        return Item.builder()
                .description(itemDto.getDescription())
                .category(itemDto.getCategory())
                .location(itemDto.getLocation())
                .eventDateTime(itemDto.getEventDateTime())
                .itemType(itemDto.getItemType())
                .reportedBy(user)
                .build();
    }


    public static ItemResponseDto itemToItemResponseDto(Item item) {
        return ItemResponseDto.builder()
                .id(item.getId())
                .description(item.getDescription())
                .category(item.getCategory())
                .location(item.getLocation())
                .eventDateTime(item.getEventDateTime())
                .itemType(item.getItemType())
                .status(item.getStatus())
                .reportedByEmail(item.getReportedBy().getEmailId())
                .build();
    }
}
