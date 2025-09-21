package com.example.LostAndFound2.Repository;

import com.example.LostAndFound2.Enums.ClaimStatus;
import com.example.LostAndFound2.Model.Claim;
import com.example.LostAndFound2.Model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ClaimRepository extends JpaRepository<Claim,Long>  {


        List<Claim> findByItemAndStatus(Item item, ClaimStatus status);
    }

