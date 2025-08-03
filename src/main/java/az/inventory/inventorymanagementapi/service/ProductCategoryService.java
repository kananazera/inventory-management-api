package az.inventory.inventorymanagementapi.service;

import az.inventory.inventorymanagementapi.dto.productcategory.ProductCategoryCreateRequest;
import az.inventory.inventorymanagementapi.dto.productcategory.ProductCategoryResponse;
import az.inventory.inventorymanagementapi.dto.productcategory.ProductCategoryUpdateRequest;
import az.inventory.inventorymanagementapi.dto.productcategory.ProductCategoryFilterRequest;

import java.util.List;

public interface ProductCategoryService {

    ProductCategoryResponse createProductCategory(ProductCategoryCreateRequest request);

    ProductCategoryResponse updateProductCategory(Long id, ProductCategoryUpdateRequest request);

    void deleteProductCategory(Long id);

    ProductCategoryResponse getProductCategoryById(Long id);

    List<ProductCategoryResponse> getAllProductCategories();

    List<ProductCategoryResponse> filterProductCategories(ProductCategoryFilterRequest filterRequest);
}
