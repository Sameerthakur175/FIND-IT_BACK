package com.example.LostAndFound2.Controller;

import com.example.LostAndFound2.Service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/claims")
public class ClaimController {

    @Autowired
    private ItemService itemService;


    @PostMapping
    public ResponseEntity<String> createClaim(@RequestParam Long itemId, Principal principal) {
        String claimantEmailId = principal.getName();

        String result = itemService.claimItem(itemId, claimantEmailId);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("/{claimId}/approve")
    public ResponseEntity<String> approveClaim(@PathVariable Long claimId, Principal principal) {
        String approverEmailId = principal.getName();

        String result = itemService.approveClaim(claimId, approverEmailId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/{claimId}/deny")
    public ResponseEntity<String> denyClaim(@PathVariable Long claimId, Principal principal) {
        String denierEmailId = principal.getName();

        String result = itemService.denyClaim(claimId, denierEmailId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
