package com.example.LostAndFound2.Service;

import com.example.LostAndFound2.Convertor.ItemConvertor;
import com.example.LostAndFound2.DTO.ItemDto;
import com.example.LostAndFound2.Enums.ClaimStatus;
import com.example.LostAndFound2.Enums.ItemStatus;
import com.example.LostAndFound2.Enums.ItemType;
import com.example.LostAndFound2.Model.Claim;
import com.example.LostAndFound2.Model.Item;
import com.example.LostAndFound2.Model.User;
import com.example.LostAndFound2.Repository.ClaimRepository;
import com.example.LostAndFound2.Repository.ItemRepository;
import com.example.LostAndFound2.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ItemService {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    ItemConvertor itemConvertor;


    @Autowired
    private ClaimRepository claimRepository;
    public String reportItem(ItemDto itemDto, String userEmail) {

        itemDto.setEventDateTime(LocalDate.now());


        boolean exists = itemRepository.existsByDescriptionAndCategoryAndLocationAndItemTypeAndEventDateTime(
                itemDto.getDescription(),
                itemDto.getCategory(),
                itemDto.getLocation(),
                itemDto.getItemType(),
                itemDto.getEventDateTime()
        );

        if (exists) {
            throw new IllegalArgumentException("This item has already been reported.");
        }


        Item item = itemConvertor.itemDtoToItem(itemDto, userEmail);

        itemRepository.save(item);
        return "Item has been successfully reported.";
    }


    public Map<String, List<Item>> search(String field, Object value, String type) {

        List<Item> items;
        switch (field.toLowerCase()) {
            case "category" -> items = itemRepository.findByCategoryIgnoreCaseOrderByEventDateTimeDesc(value.toString());
            case "location" -> items = itemRepository.findByLocationIgnoreCaseOrderByEventDateTimeDesc(value.toString());
            case "date"     -> items = itemRepository.findByEventDateTimeOrderByEventDateTimeDesc((LocalDate) value);
            default         -> throw new IllegalArgumentException("Invalid search field: " + field);
        }


        return switch (type.toLowerCase()) {
            case "lost" -> Map.of("lostItems",
                    items.stream()
                            .filter(i -> i.getItemType() == ItemType.LOST)
                            .toList());
            case "found" -> Map.of("foundItems",
                    items.stream()
                            .filter(i -> i.getItemType() == ItemType.FOUND)
                            .toList());
            case "both" -> {

                Map<ItemType, List<Item>> groupedItems = items.stream()
                        .collect(Collectors.groupingBy(Item::getItemType));

                yield Map.of(
                        "lostItems", groupedItems.getOrDefault(ItemType.LOST, Collections.emptyList()),
                        "foundItems", groupedItems.getOrDefault(ItemType.FOUND, Collections.emptyList())
                );
            }
            default -> throw new IllegalArgumentException("Invalid type: " + type);
        };
    }

    @Transactional
    public String claimItem(Long itemId, String claimantEmailId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("Item not found with ID: " + itemId));


        User claimant = userRepository.findById(claimantEmailId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + claimantEmailId));


        if (item.getItemType() != ItemType.FOUND) {
            throw new IllegalStateException("You can only claim items that have been found.");
        }
        if (item.getStatus() != ItemStatus.OPEN) {
            throw new IllegalStateException("This item is not open for claims.");
        }

        if (item.getReportedBy().equals(claimant)) {
            throw new IllegalStateException("You cannot claim an item you reported yourself.");
        }

        Claim claim = new Claim();
        claim.setItem(item);
        claim.setClaimant(claimant);
        claimRepository.save(claim);

        item.setStatus(ItemStatus.CLAIM_PENDING);
        itemRepository.save(item);

        return "Claim submitted successfully. The finder has been notified.";
    }


    @Transactional
    public String approveClaim(Long claimId, String approverEmailId) {
        Claim claimToApprove = claimRepository.findById(claimId)
                .orElseThrow(() -> new EntityNotFoundException("Claim not found with ID: " + claimId));

        Item item = claimToApprove.getItem();


        if (!item.getReportedBy().getEmailId().equals(approverEmailId)) {
            throw new SecurityException("You are not authorized to approve this claim.");
        }
        if(claimToApprove.getStatus() != ClaimStatus.PENDING) {
            throw new IllegalStateException("This claim has already been actioned.");
        }

        claimToApprove.setStatus(ClaimStatus.APPROVED);
        item.setStatus(ItemStatus.RETURNED);

        List<Claim> otherClaims = claimRepository.findByItemAndStatus(item, ClaimStatus.PENDING);
        for (Claim otherClaim : otherClaims) {
            otherClaim.setStatus(ClaimStatus.DENIED);
        }

        claimRepository.save(claimToApprove);
        claimRepository.saveAll(otherClaims);
        itemRepository.save(item);

        return "Claim approved. The item has been marked as returned.";
    }


    @Transactional
    public String denyClaim(Long claimId, String denierEmailId) {
        Claim claimToDeny = claimRepository.findById(claimId)
                .orElseThrow(() -> new EntityNotFoundException("Claim not found with ID: " + claimId));

        Item item = claimToDeny.getItem();


        if (!item.getReportedBy().getEmailId().equals(denierEmailId)) {
            throw new SecurityException("You are not authorized to deny this claim.");
        }
        if(claimToDeny.getStatus() != ClaimStatus.PENDING) {
            throw new IllegalStateException("This claim has already been actioned.");
        }

        claimToDeny.setStatus(ClaimStatus.DENIED);
        claimRepository.save(claimToDeny);

        List<Claim> otherPendingClaims = claimRepository.findByItemAndStatus(item, ClaimStatus.PENDING);
        if (otherPendingClaims.isEmpty()) {
            item.setStatus(ItemStatus.OPEN);
            itemRepository.save(item);
        }

        return "Claim has been denied.";
    }
}


