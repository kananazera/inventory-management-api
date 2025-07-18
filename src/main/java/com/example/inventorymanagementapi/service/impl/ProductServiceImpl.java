package com.example.inventorymanagementapi.service.impl;

import com.example.inventorymanagementapi.dto.product.ProductFilterRequest;
import com.example.inventorymanagementapi.dto.product.ProductCreateRequest;
import com.example.inventorymanagementapi.dto.product.ProductResponse;
import com.example.inventorymanagementapi.dto.product.ProductUpdateRequest;
import com.example.inventorymanagementapi.entity.*;
import com.example.inventorymanagementapi.exception.ResourceNotFoundException;
import com.example.inventorymanagementapi.mapper.ProductMapper;
import com.example.inventorymanagementapi.repository.*;
import com.example.inventorymanagementapi.service.ProductService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository categoryRepository;
    private final ProductBrandRepository brandRepository;
    private final ProductUnitRepository unitRepository;

    @Override
    public ProductResponse createProduct(ProductCreateRequest request) {
        ProductCategory category = null;
        if (request.getCategoryId() != null) {
            category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + request.getCategoryId()));
        }

        ProductBrand brand = null;
        if (request.getBrandId() != null) {
            brand = brandRepository.findById(request.getBrandId())
                    .orElseThrow(() -> new ResourceNotFoundException("Brand not found with id: " + request.getBrandId()));
        }

        ProductUnit unit = null;
        if (request.getUnitId() != null) {
            unit = unitRepository.findById(request.getUnitId())
                    .orElseThrow(() -> new ResourceNotFoundException("Unit not found with id: " + request.getUnitId()));
        }

        Product product = ProductMapper.toEntity(request, category, brand, unit);
        return ProductMapper.toResponse(productRepository.save(product));
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductUpdateRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));

        if (request.getName() != null) {
            product.setName(request.getName());
        }

        if (request.getDescription() != null) {
            product.setDescription(request.getDescription());
        }

        if (request.getPrice() != null) {
            product.setPrice(request.getPrice());
        }

        if (request.getSku() != null) {
            product.setSku(request.getSku());
        }

        if (request.getManufactureDate() != null) {
            product.setManufactureDate(request.getManufactureDate());
        }

        if (request.getExpiryDate() != null) {
            product.setExpiryDate(request.getExpiryDate());
        }

        if (request.getActive() != null) {
            product.setActive(request.getActive());
        }

        if (request.getCategoryId() != null) {
            ProductCategory category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + request.getCategoryId()));
            product.setCategory(category);
        }

        if (request.getBrandId() != null) {
            ProductBrand brand = brandRepository.findById(request.getBrandId())
                    .orElseThrow(() -> new ResourceNotFoundException("Brand not found with id: " + request.getBrandId()));
            product.setBrand(brand);
        }

        if (request.getUnitId() != null) {
            ProductUnit unit = unitRepository.findById(request.getUnitId())
                    .orElseThrow(() -> new ResourceNotFoundException("Unit not found with id: " + request.getUnitId()));
            product.setUnit(unit);
        }

        return ProductMapper.toResponse(productRepository.save(product));
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public ProductResponse getProductById(Long id) {
        return productRepository.findById(id)
                .map(ProductMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> filterProducts(ProductFilterRequest filterRequest) {
        Specification<Product> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filterRequest.getName() != null && !filterRequest.getName().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + filterRequest.getName().toLowerCase() + "%"));
            }

            if (filterRequest.getCategoryId() != null) {
                predicates.add(cb.equal(root.get("category").get("id"), filterRequest.getCategoryId()));
            }

            if (filterRequest.getBrandId() != null) {
                predicates.add(cb.equal(root.get("brand").get("id"), filterRequest.getBrandId()));
            }

            if (filterRequest.getUnitId() != null) {
                predicates.add(cb.equal(root.get("unit").get("id"), filterRequest.getUnitId()));
            }

            if (filterRequest.getMinPrice() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("price"), filterRequest.getMinPrice()));
            }

            if (filterRequest.getMaxPrice() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("price"), filterRequest.getMaxPrice()));
            }

            if (filterRequest.getActive() != null) {
                predicates.add(cb.equal(root.get("active"), filterRequest.getActive()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return productRepository.findAll(spec).stream()
                .map(ProductMapper::toResponse)
                .collect(Collectors.toList());
    }
}