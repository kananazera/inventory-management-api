package az.inventory.inventorymanagementapi.controller;

import az.inventory.inventorymanagementapi.dto.purchase.PurchaseCreateRequest;
import az.inventory.inventorymanagementapi.dto.purchase.PurchaseFilterRequest;
import az.inventory.inventorymanagementapi.dto.purchase.PurchaseResponse;
import az.inventory.inventorymanagementapi.enums.PurchaseStatus;
import az.inventory.inventorymanagementapi.service.PurchaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updatePurchaseStatus(
            @PathVariable Long id,
            @RequestParam("status") PurchaseStatus status) {
        purchaseService.updateStatus(id, status);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<PurchaseResponse>> getAllPurchases() {
        List<PurchaseResponse> purchases = purchaseService.getAllPurchases();
        return ResponseEntity.ok(purchases);
    }

    @PostMapping("/filter")
    public ResponseEntity<List<PurchaseResponse>> filter(@RequestBody PurchaseFilterRequest filterRequest) {
        List<PurchaseResponse> list = purchaseService.filterPurchases(filterRequest);
        return ResponseEntity.ok(list);
    }
}