package com.example.LostAndFound2.Repository;

import com.example.LostAndFound2.Enums.ItemType;
import com.example.LostAndFound2.Model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long> {

    boolean existsByDescriptionAndCategoryAndLocationAndItemTypeAndEventDateTime(
            String description,
            String category,
            String location,
            ItemType itemType,
            LocalDate eventDateTime
    );

    List<Item> findByCategoryIgnoreCaseOrderByEventDateTimeDesc(String category);
    List<Item> findByLocationIgnoreCaseOrderByEventDateTimeDesc(String location);
    List<Item> findByEventDateTimeOrderByEventDateTimeDesc(LocalDate eventDateTime);
}
