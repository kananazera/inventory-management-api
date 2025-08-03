package az.inventory.inventorymanagementapi.service;

import az.inventory.inventorymanagementapi.dto.currency.CurrencyCreateRequest;
import az.inventory.inventorymanagementapi.dto.currency.CurrencyFilterRequest;
import az.inventory.inventorymanagementapi.dto.currency.CurrencyResponse;
import az.inventory.inventorymanagementapi.dto.currency.CurrencyUpdateRequest;

import java.util.List;

public interface CurrencyService {

    CurrencyResponse createCurrency(CurrencyCreateRequest request);

    CurrencyResponse updateCurrency(Long id, CurrencyUpdateRequest request);

    void deleteCurrency(Long id);

    CurrencyResponse getCurrencyById(Long id);

    List<CurrencyResponse> getAllCurrencies();

    List<CurrencyResponse> filterCurrencies(CurrencyFilterRequest filterRequest);
}
