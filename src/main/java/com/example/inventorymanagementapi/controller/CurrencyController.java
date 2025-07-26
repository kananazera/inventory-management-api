package com.example.inventorymanagementapi.controller;

import com.example.inventorymanagementapi.dto.currency.CurrencyCreateRequest;
import com.example.inventorymanagementapi.dto.currency.CurrencyFilterRequest;
import com.example.inventorymanagementapi.dto.currency.CurrencyResponse;
import com.example.inventorymanagementapi.dto.currency.CurrencyUpdateRequest;
import com.example.inventorymanagementapi.service.CurrencyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/currencies")
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyService currencyService;

    @PostMapping
    public ResponseEntity<CurrencyResponse> create(@Valid @RequestBody(required = false) CurrencyCreateRequest request) {
        if (request == null) {
            throw new RuntimeException("Request body must not be empty");
        }
        CurrencyResponse response = currencyService.createCurrency(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CurrencyResponse> update(@PathVariable Long id, @RequestBody CurrencyUpdateRequest request) {
        CurrencyResponse response = currencyService.updateCurrency(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CurrencyResponse>> getAll() {
        List<CurrencyResponse> list = currencyService.getAllCurrencies();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CurrencyResponse> getById(@PathVariable Long id) {
        CurrencyResponse response = currencyService.getCurrencyById(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        currencyService.deleteCurrency(id);
        ResponseEntity.ok("Currency deleted successfully");
    }

    @PostMapping("/filter")
    public ResponseEntity<List<CurrencyResponse>> filter(@RequestBody CurrencyFilterRequest filterRequest) {
        List<CurrencyResponse> list = currencyService.filterCurrencies(filterRequest);
        return ResponseEntity.ok(list);
    }
}