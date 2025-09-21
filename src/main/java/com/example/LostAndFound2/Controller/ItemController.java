package com.example.LostAndFound2.Controller;


import com.example.LostAndFound2.Convertor.ItemConvertor;
import com.example.LostAndFound2.DTO.ItemDto;
import com.example.LostAndFound2.DTO.ItemResponseDto;
import com.example.LostAndFound2.Model.Item;
import com.example.LostAndFound2.Service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    ItemService itemService;


    @PostMapping
    public ResponseEntity<String> reportItem(@RequestBody ItemDto itemDto, Principal principal) {

        String userEmail = principal.getName();
        String response = itemService.reportItem(itemDto, userEmail);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping("/search")
    public ResponseEntity<Map<String, List<ItemResponseDto>>> search(
            @RequestParam String field,
            @RequestParam String value,
            @RequestParam(defaultValue = "both") String type) {

        Object parsedValue = value;
        if (field.equalsIgnoreCase("date")) {
            try {
                parsedValue = LocalDate.parse(value);
            } catch (Exception e) {
                return ResponseEntity.badRequest().build();
            }
        }

        Map<String, List<Item>> searchResult = itemService.search(field, parsedValue, type);


        Map<String, List<ItemResponseDto>> responseDtoMap = searchResult.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(ItemConvertor::itemToItemResponseDto)
                                .collect(Collectors.toList())
                ));

        return ResponseEntity.ok(responseDtoMap);
    }
}

