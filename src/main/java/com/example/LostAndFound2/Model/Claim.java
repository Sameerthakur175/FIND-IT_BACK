package com.example.LostAndFound2.Model;

import com.example.LostAndFound2.Enums.ClaimStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Builder
@Entity
@AllArgsConstructor
@Data
@Table(name = "claim")
public class Claim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "claimant_user_email", nullable = false)
    private User claimant;

    @Enumerated(EnumType.STRING)
    private ClaimStatus status;

    @CreationTimestamp
    private LocalDateTime claimDate;

    public Claim() {
        this.status = ClaimStatus.PENDING;
    }


}
