package com.example.inventorymanagementapi.service.impl;

import com.example.inventorymanagementapi.dto.productcategory.ProductCategoryCreateRequest;
import com.example.inventorymanagementapi.dto.productcategory.ProductCategoryFilterRequest;
import com.example.inventorymanagementapi.dto.productcategory.ProductCategoryResponse;
import com.example.inventorymanagementapi.dto.productcategory.ProductCategoryUpdateRequest;
import com.example.inventorymanagementapi.entity.ProductCategory;
import com.example.inventorymanagementapi.exception.ResourceNotFoundException;
import com.example.inventorymanagementapi.mapper.ProductCategoryMapper;
import com.example.inventorymanagementapi.repository.ProductCategoryRepository;
import com.example.inventorymanagementapi.service.ProductCategoryService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    @Override
    public ProductCategoryResponse createProductCategory(ProductCategoryCreateRequest request) {
        ProductCategory category = ProductCategoryMapper.toEntity(request);
        return ProductCategoryMapper.toResponse(productCategoryRepository.save(category));
    }

    @Override
    public ProductCategoryResponse updateProductCategory(Long id, ProductCategoryUpdateRequest request) {
        ProductCategory category = productCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product Category not found with id: " + id));

        if (request.getName() != null) {
            category.setName(request.getName());
        }

        return ProductCategoryMapper.toResponse(productCategoryRepository.save(category));
    }

    @Override
    public void deleteProductCategory(Long id) {
        ProductCategory category = productCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product Category not found with id: " + id));
        productCategoryRepository.delete(category);
    }

    @Override
    public ProductCategoryResponse getProductCategoryById(Long id) {
        ProductCategory category = productCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product Category not found with id: " + id));
        return ProductCategoryMapper.toResponse(category);
    }

    @Override
    public List<ProductCategoryResponse> getAllProductCategories() {
        return productCategoryRepository.findAll().stream()
                .map(ProductCategoryMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductCategoryResponse> filterProductCategories(ProductCategoryFilterRequest filterRequest) {
        Specification<ProductCategory> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filterRequest.getName() != null && !filterRequest.getName().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + filterRequest.getName().toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return productCategoryRepository.findAll(spec).stream()
                .map(ProductCategoryMapper::toResponse)
                .collect(Collectors.toList());
    }
}
