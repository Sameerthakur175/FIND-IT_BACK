package com.example.LostAndFound2.Model;

import com.example.LostAndFound2.Enums.ItemStatus;
import com.example.LostAndFound2.Enums.ItemType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private String category;

    private String location;

    private LocalDate eventDateTime;

    @Enumerated(value = EnumType.STRING)
    private ItemType itemType;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ItemStatus status;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_by_user_email")
    private User reportedBy;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Claim> claims = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.status = ItemStatus.OPEN;
    }
    @ManyToOne
    @JoinColumn
    private User reportedby;


}
