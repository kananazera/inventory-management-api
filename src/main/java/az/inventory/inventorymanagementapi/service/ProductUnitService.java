package az.inventory.inventorymanagementapi.service;

import az.inventory.inventorymanagementapi.dto.productunit.ProductUnitCreateRequest;
import az.inventory.inventorymanagementapi.dto.productunit.ProductUnitFilterRequest;
import az.inventory.inventorymanagementapi.dto.productunit.ProductUnitResponse;
import az.inventory.inventorymanagementapi.dto.productunit.ProductUnitUpdateRequest;

import java.util.List;

public interface ProductUnitService {

    ProductUnitResponse createProductUnit(ProductUnitCreateRequest request);

    ProductUnitResponse updateProductUnit(Long id, ProductUnitUpdateRequest request);

    void deleteProductUnit(Long id);

    ProductUnitResponse getProductUnitById(Long id);

    List<ProductUnitResponse> getAllProductUnits();

    List<ProductUnitResponse> filterProductUnits(ProductUnitFilterRequest filterRequest);
}
