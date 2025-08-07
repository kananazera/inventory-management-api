package az.inventory.inventorymanagementapi.controller;

import az.inventory.inventorymanagementapi.dto.purchase.PurchaseCreateRequest;
import az.inventory.inventorymanagementapi.dto.purchase.PurchaseResponse;
import az.inventory.inventorymanagementapi.service.PurchaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchases")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;

    @PostMapping
    public ResponseEntity<PurchaseResponse> createPurchase(@Valid @RequestBody PurchaseCreateRequest request) {
        PurchaseResponse response = purchaseService.createPurchase(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PurchaseResponse>> getAllPurchases() {
        List<PurchaseResponse> purchases = purchaseService.getAllPurchases();
        return ResponseEntity.ok(purchases);
    }
}