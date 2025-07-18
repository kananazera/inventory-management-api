package com.example.inventorymanagementapi.service.impl;

import com.example.inventorymanagementapi.dto.productunit.ProductUnitCreateRequest;
import com.example.inventorymanagementapi.dto.productunit.ProductUnitFilterRequest;
import com.example.inventorymanagementapi.dto.productunit.ProductUnitResponse;
import com.example.inventorymanagementapi.dto.productunit.ProductUnitUpdateRequest;
import com.example.inventorymanagementapi.entity.ProductUnit;
import com.example.inventorymanagementapi.exception.ResourceNotFoundException;
import com.example.inventorymanagementapi.mapper.ProductUnitMapper;
import com.example.inventorymanagementapi.repository.ProductUnitRepository;
import com.example.inventorymanagementapi.service.ProductUnitService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductUnitServiceImpl implements ProductUnitService {

    private final ProductUnitRepository productUnitRepository;

    @Override
    public ProductUnitResponse createProductUnit(ProductUnitCreateRequest request) {
        ProductUnit Unit = ProductUnitMapper.toEntity(request);
        return ProductUnitMapper.toResponse(productUnitRepository.save(Unit));
    }

    @Override
    public ProductUnitResponse updateProductUnit(Long id, ProductUnitUpdateRequest request) {
        ProductUnit Unit = productUnitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product Unit not found with id: " + id));

        if (request.getName() != null) {
            Unit.setName(request.getName());
        }

        return ProductUnitMapper.toResponse(productUnitRepository.save(Unit));
    }

    @Override
    public void deleteProductUnit(Long id) {
        ProductUnit Unit = productUnitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product Unit not found with id: " + id));
        productUnitRepository.delete(Unit);
    }

    @Override
    public ProductUnitResponse getProductUnitById(Long id) {
        ProductUnit Unit = productUnitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product Unit not found with id: " + id));
        return ProductUnitMapper.toResponse(Unit);
    }

    @Override
    public List<ProductUnitResponse> getAllProductCategories() {
        return productUnitRepository.findAll().stream()
                .map(ProductUnitMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductUnitResponse> filterProductCategories(ProductUnitFilterRequest filterRequest) {
        Specification<ProductUnit> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filterRequest.getName() != null && !filterRequest.getName().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + filterRequest.getName().toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return productUnitRepository.findAll(spec).stream()
                .map(ProductUnitMapper::toResponse)
                .collect(Collectors.toList());
    }
}
