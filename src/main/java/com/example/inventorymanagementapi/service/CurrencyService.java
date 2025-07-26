package com.example.inventorymanagementapi.service;

import com.example.inventorymanagementapi.dto.currency.CurrencyCreateRequest;
import com.example.inventorymanagementapi.dto.currency.CurrencyFilterRequest;
import com.example.inventorymanagementapi.dto.currency.CurrencyResponse;
import com.example.inventorymanagementapi.dto.currency.CurrencyUpdateRequest;

import java.util.List;

public interface CurrencyService {

    CurrencyResponse createCurrency(CurrencyCreateRequest request);

    CurrencyResponse updateCurrency(Long id, CurrencyUpdateRequest request);

    void deleteCurrency(Long id);

    CurrencyResponse getCurrencyById(Long id);

    List<CurrencyResponse> getAllCurrencies();

    List<CurrencyResponse> filterCurrencies(CurrencyFilterRequest filterRequest);
}
