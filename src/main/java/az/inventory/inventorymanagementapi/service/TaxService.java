package az.inventory.inventorymanagementapi.service;

import az.inventory.inventorymanagementapi.dto.tax.TaxCreateRequest;
import az.inventory.inventorymanagementapi.dto.tax.TaxFilterRequest;
import az.inventory.inventorymanagementapi.dto.tax.TaxResponse;
import az.inventory.inventorymanagementapi.dto.tax.TaxUpdateRequest;

import java.util.List;

public interface TaxService {

    TaxResponse createTax(TaxCreateRequest request);

    TaxResponse updateTax(Long id, TaxUpdateRequest request);

    void deleteTax(Long id);

    TaxResponse getTaxById(Long id);

    List<TaxResponse> getAllTaxes();

    List<TaxResponse> filterTaxes(TaxFilterRequest filterRequest);
}
