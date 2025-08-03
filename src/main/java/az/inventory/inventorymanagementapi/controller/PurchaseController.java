package az.inventory.inventorymanagementapi.controller;

import az.inventory.inventorymanagementapi.dto.PurchaseRequest;
import az.inventory.inventorymanagementapi.service.PurchaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/purchases")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;

    @PostMapping
    public ResponseEntity<Void> createPurchase(@Valid @RequestBody PurchaseRequest request) {
        purchaseService.createPurchase(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}