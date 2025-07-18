package com.example.inventorymanagementapi.service.impl;

import com.example.inventorymanagementapi.dto.productbrand.ProductBrandCreateRequest;
import com.example.inventorymanagementapi.dto.productbrand.ProductBrandFilterRequest;
import com.example.inventorymanagementapi.dto.productbrand.ProductBrandResponse;
import com.example.inventorymanagementapi.dto.productbrand.ProductBrandUpdateRequest;
import com.example.inventorymanagementapi.entity.ProductBrand;
import com.example.inventorymanagementapi.exception.ResourceNotFoundException;
import com.example.inventorymanagementapi.mapper.ProductBrandMapper;
import com.example.inventorymanagementapi.repository.ProductBrandRepository;
import com.example.inventorymanagementapi.service.ProductBrandService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductBrandServiceImpl implements ProductBrandService {

    private final ProductBrandRepository productBrandRepository;

    @Override
    public ProductBrandResponse createProductBrand(ProductBrandCreateRequest request) {
        ProductBrand Brand = ProductBrandMapper.toEntity(request);
        return ProductBrandMapper.toResponse(productBrandRepository.save(Brand));
    }

    @Override
    public ProductBrandResponse updateProductBrand(Long id, ProductBrandUpdateRequest request) {
        ProductBrand Brand = productBrandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product Brand not found with id: " + id));

        if (request.getName() != null) {
            Brand.setName(request.getName());
        }

        return ProductBrandMapper.toResponse(productBrandRepository.save(Brand));
    }

    @Override
    public void deleteProductBrand(Long id) {
        ProductBrand Brand = productBrandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product Brand not found with id: " + id));
        productBrandRepository.delete(Brand);
    }

    @Override
    public ProductBrandResponse getProductBrandById(Long id) {
        ProductBrand Brand = productBrandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product Brand not found with id: " + id));
        return ProductBrandMapper.toResponse(Brand);
    }

    @Override
    public List<ProductBrandResponse> getAllProductCategories() {
        return productBrandRepository.findAll().stream()
                .map(ProductBrandMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductBrandResponse> filterProductCategories(ProductBrandFilterRequest filterRequest) {
        Specification<ProductBrand> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filterRequest.getName() != null && !filterRequest.getName().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + filterRequest.getName().toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return productBrandRepository.findAll(spec).stream()
                .map(ProductBrandMapper::toResponse)
                .collect(Collectors.toList());
    }
}
