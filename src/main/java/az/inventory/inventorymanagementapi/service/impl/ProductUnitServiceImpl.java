package az.inventory.inventorymanagementapi.service.impl;

import az.inventory.inventorymanagementapi.dto.productunit.ProductUnitCreateRequest;
import az.inventory.inventorymanagementapi.dto.productunit.ProductUnitFilterRequest;
import az.inventory.inventorymanagementapi.dto.productunit.ProductUnitResponse;
import az.inventory.inventorymanagementapi.dto.productunit.ProductUnitUpdateRequest;
import az.inventory.inventorymanagementapi.entity.ProductUnit;
import az.inventory.inventorymanagementapi.exception.ResourceNotFoundException;
import az.inventory.inventorymanagementapi.mapper.ProductUnitMapper;
import az.inventory.inventorymanagementapi.repository.ProductUnitRepository;
import az.inventory.inventorymanagementapi.service.ProductUnitService;
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
        if (productUnitRepository.existsByNameIgnoreCase(request.getName())) {
            throw new RuntimeException("Product unit name already exists");
        }
        ProductUnit Unit = ProductUnitMapper.toEntity(request);
        return ProductUnitMapper.toResponse(productUnitRepository.save(Unit));
    }

    @Override
    public ProductUnitResponse updateProductUnit(Long id, ProductUnitUpdateRequest request) {
        ProductUnit Unit = productUnitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product Unit not found with id: " + id));

        if (productUnitRepository.existsByNameIgnoreCaseAndIdNot(request.getName(), id)) {
            throw new RuntimeException("Product unit name already exists");
        }

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
    public List<ProductUnitResponse> getAllProductUnits() {
        return productUnitRepository.findAll().stream()
                .map(ProductUnitMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductUnitResponse> filterProductUnits(ProductUnitFilterRequest filterRequest) {
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
