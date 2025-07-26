package com.example.inventorymanagementapi.service.impl;

import com.example.inventorymanagementapi.dto.product.ProductCreateRequest;
import com.example.inventorymanagementapi.dto.product.ProductFilterRequest;
import com.example.inventorymanagementapi.dto.product.ProductResponse;
import com.example.inventorymanagementapi.dto.product.ProductUpdateRequest;
import com.example.inventorymanagementapi.entity.Product;
import com.example.inventorymanagementapi.entity.ProductBrand;
import com.example.inventorymanagementapi.entity.ProductCategory;
import com.example.inventorymanagementapi.entity.ProductUnit;
import com.example.inventorymanagementapi.exception.ResourceNotFoundException;
import com.example.inventorymanagementapi.mapper.ProductMapper;
import com.example.inventorymanagementapi.repository.ProductBrandRepository;
import com.example.inventorymanagementapi.repository.ProductCategoryRepository;
import com.example.inventorymanagementapi.repository.ProductRepository;
import com.example.inventorymanagementapi.repository.ProductUnitRepository;
import com.example.inventorymanagementapi.service.FileStorageService;
import com.example.inventorymanagementapi.service.ProductService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private final FileStorageService fileStorageService;

    @Override
    public ProductResponse createProduct(ProductCreateRequest request, MultipartFile image) throws IOException {
        if (productRepository.existsByNameIgnoreCase(request.getName())) {
            throw new RuntimeException("Product name already exists");
        }

        ProductCategory category = getCategory(request.getCategoryId());
        ProductBrand brand = getBrand(request.getBrandId());
        ProductUnit unit = getUnit(request.getUnitId());

        String imageUrl = null;
        if (image != null && !image.isEmpty()) {
            imageUrl = fileStorageService.storeFile(image);
        }

        Product product = ProductMapper.toEntity(request, category, brand, unit, imageUrl);
        return ProductMapper.toResponse(productRepository.save(product));
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductUpdateRequest request, MultipartFile image) throws IOException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));

        if (request.getName() != null && productRepository.existsByNameIgnoreCaseAndIdNot(request.getName(), id)) {
            throw new RuntimeException("Product name already exists");
        }

        if (request.getName() != null) product.setName(request.getName());
        if (request.getDescription() != null) product.setDescription(request.getDescription());
        if (request.getPrice() != null) product.setPrice(request.getPrice());
        if (request.getSku() != null) product.setSku(request.getSku());
        if (request.getActive() != null) product.setActive(request.getActive());

        if (request.getCategoryId() != null) product.setCategory(getCategory(request.getCategoryId()));
        if (request.getBrandId() != null) product.setBrand(getBrand(request.getBrandId()));
        if (request.getUnitId() != null) product.setUnit(getUnit(request.getUnitId()));

        if (image != null && !image.isEmpty()) {
            deleteImageFile(product.getImageUrl());
            String imageUrl = fileStorageService.storeFile(image);
            product.setImageUrl(imageUrl);
        }

        return ProductMapper.toResponse(productRepository.save(product));
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        deleteImageFile(product.getImageUrl());

        productRepository.delete(product);
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
            if (filterRequest.getCategory() != null) {
                predicates.add(cb.equal(root.get("category").get("id"), filterRequest.getCategory()));
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

    private ProductCategory getCategory(Long id) {
        if (id == null) return null;
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }

    private ProductBrand getBrand(Long id) {
        if (id == null) return null;
        return brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found with id: " + id));
    }

    private ProductUnit getUnit(Long id) {
        if (id == null) return null;
        return unitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Unit not found with id: " + id));
    }

    private void deleteImageFile(String imageUrl) {
        if (imageUrl == null || imageUrl.isBlank()) return;

        try {
            String filename = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
            Path filePath = Paths.get(fileStorageService.getUploadDir(), filename);
            Files.deleteIfExists(filePath);
        } catch (Exception e) {
            System.err.println("Could not delete file: " + imageUrl + " -> " + e.getMessage());
        }
    }
}
