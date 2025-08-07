package az.inventory.inventorymanagementapi.controller;

import az.inventory.inventorymanagementapi.dto.tax.TaxCreateRequest;
import az.inventory.inventorymanagementapi.dto.tax.TaxFilterRequest;
import az.inventory.inventorymanagementapi.dto.tax.TaxResponse;
import az.inventory.inventorymanagementapi.dto.tax.TaxUpdateRequest;
import az.inventory.inventorymanagementapi.service.TaxService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/taxes")
@RequiredArgsConstructor
public class TaxController {

    private final TaxService taxService;

    @PostMapping
    public ResponseEntity<TaxResponse> create(@Valid @RequestBody(required = false) TaxCreateRequest request) {
        if (request == null) {
            throw new RuntimeException("Request body must not be empty");
        }
        TaxResponse response = taxService.createTax(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaxResponse> update(@PathVariable Long id, @RequestBody TaxUpdateRequest request) {
        TaxResponse response = taxService.updateTax(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<TaxResponse>> getAll() {
        List<TaxResponse> list = taxService.getAllTaxes();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaxResponse> getById(@PathVariable Long id) {
        TaxResponse response = taxService.getTaxById(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        taxService.deleteTax(id);
        ResponseEntity.ok("Tax deleted successfully");
    }

    @PostMapping("/filter")
    public ResponseEntity<List<TaxResponse>> filter(@RequestBody TaxFilterRequest filterRequest) {
        List<TaxResponse> list = taxService.filterTaxes(filterRequest);
        return ResponseEntity.ok(list);
    }
}
