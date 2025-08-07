package az.inventory.inventorymanagementapi.service.impl;

import az.inventory.inventorymanagementapi.dto.tax.TaxCreateRequest;
import az.inventory.inventorymanagementapi.dto.tax.TaxFilterRequest;
import az.inventory.inventorymanagementapi.dto.tax.TaxResponse;
import az.inventory.inventorymanagementapi.dto.tax.TaxUpdateRequest;
import az.inventory.inventorymanagementapi.entity.Tax;
import az.inventory.inventorymanagementapi.exception.ResourceNotFoundException;
import az.inventory.inventorymanagementapi.mapper.TaxMapper;
import az.inventory.inventorymanagementapi.repository.TaxRepository;
import az.inventory.inventorymanagementapi.service.TaxService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaxServiceImpl implements TaxService {

    private final TaxRepository productUnitRepository;

    @Override
    public TaxResponse createTax(TaxCreateRequest request) {
        if (productUnitRepository.existsByNameIgnoreCase(request.getName())) {
            throw new RuntimeException("Tax name already exists");
        }
        Tax Unit = TaxMapper.toEntity(request);
        return TaxMapper.toResponse(productUnitRepository.save(Unit));
    }

    @Override
    public TaxResponse updateTax(Long id, TaxUpdateRequest request) {
        Tax tax = productUnitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tax not found with id: " + id));

        if (productUnitRepository.existsByNameIgnoreCaseAndIdNot(request.getName(), id)) {
            throw new RuntimeException("Tax name already exists");
        }

        if (request.getName() != null) {
            tax.setName(request.getName());
        }

        if (request.getRate() != null) {
            tax.setRate(request.getRate());
        }

        return TaxMapper.toResponse(productUnitRepository.save(tax));
    }

    @Override
    public void deleteTax(Long id) {
        Tax Unit = productUnitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tax not found with id: " + id));
        productUnitRepository.delete(Unit);
    }

    @Override
    public TaxResponse getTaxById(Long id) {
        Tax Unit = productUnitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("tax not found with id: " + id));
        return TaxMapper.toResponse(Unit);
    }

    @Override
    public List<TaxResponse> getAllTaxes() {
        return productUnitRepository.findAll().stream()
                .map(TaxMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaxResponse> filterTaxes(TaxFilterRequest filterRequest) {
        Specification<Tax> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filterRequest.getName() != null && !filterRequest.getName().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + filterRequest.getName().toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return productUnitRepository.findAll(spec).stream()
                .map(TaxMapper::toResponse)
                .collect(Collectors.toList());
    }
}
