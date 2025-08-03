package az.inventory.inventorymanagementapi.service;

import az.inventory.inventorymanagementapi.dto.productbrand.ProductBrandCreateRequest;
import az.inventory.inventorymanagementapi.dto.productbrand.ProductBrandFilterRequest;
import az.inventory.inventorymanagementapi.dto.productbrand.ProductBrandResponse;
import az.inventory.inventorymanagementapi.dto.productbrand.ProductBrandUpdateRequest;

import java.util.List;

public interface ProductBrandService {

    ProductBrandResponse createProductBrand(ProductBrandCreateRequest request);

    ProductBrandResponse updateProductBrand(Long id, ProductBrandUpdateRequest request);

    void deleteProductBrand(Long id);

    ProductBrandResponse getProductBrandById(Long id);

    List<ProductBrandResponse> getAllProductBrands();

    List<ProductBrandResponse> filterProductBrands(ProductBrandFilterRequest filterRequest);
}
