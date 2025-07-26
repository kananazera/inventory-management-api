package com.example.inventorymanagementapi.service.impl;

import com.example.inventorymanagementapi.dto.currency.CurrencyCreateRequest;
import com.example.inventorymanagementapi.dto.currency.CurrencyFilterRequest;
import com.example.inventorymanagementapi.dto.currency.CurrencyResponse;
import com.example.inventorymanagementapi.dto.currency.CurrencyUpdateRequest;
import com.example.inventorymanagementapi.entity.Currency;
import com.example.inventorymanagementapi.exception.ResourceNotFoundException;
import com.example.inventorymanagementapi.mapper.CurrencyMapper;
import com.example.inventorymanagementapi.repository.CurrencyRepository;
import com.example.inventorymanagementapi.service.CurrencyService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;

    @Override
    public CurrencyResponse createCurrency(CurrencyCreateRequest request) {
        if (currencyRepository.existsByCodeIgnoreCase(request.getCode())) {
            throw new RuntimeException("Currency code already exists");
        }

        if (currencyRepository.existsByNameIgnoreCase(request.getName())) {
            throw new RuntimeException("Currency name already exists");
        }

        Currency currency = CurrencyMapper.toEntity(request);
        return CurrencyMapper.toResponse(currencyRepository.save(currency));
    }

    @Override
    public CurrencyResponse updateCurrency(Long id, CurrencyUpdateRequest request) {
        Currency currency = currencyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Currency not found with id: " + id));

        if (request.getCode() != null && !request.getCode().equalsIgnoreCase(currency.getCode())) {
            if (currencyRepository.existsByCodeIgnoreCaseAndIdNot(request.getCode(), id)) {
                throw new RuntimeException("Currency code already exists");
            }
            currency.setCode(request.getCode());
        }

        if (request.getName() != null && !request.getName().equalsIgnoreCase(currency.getName())) {
            if (currencyRepository.existsByNameIgnoreCaseAndIdNot(request.getName(), id)) {
                throw new RuntimeException("Currency name already exists");
            }
            currency.setName(request.getName());
        }

        return CurrencyMapper.toResponse(currencyRepository.save(currency));
    }

    @Override
    public void deleteCurrency(Long id) {
        if (!currencyRepository.existsById(id)) {
            throw new ResourceNotFoundException("Currency not found with id: " + id);
        }
        currencyRepository.deleteById(id);
    }

    @Override
    public CurrencyResponse getCurrencyById(Long id) {
        return currencyRepository.findById(id)
                .map(CurrencyMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Currency not found with id: " + id));
    }

    @Override
    public List<CurrencyResponse> getAllCurrencies() {
        return currencyRepository.findAll().stream()
                .map(CurrencyMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CurrencyResponse> filterCurrencies(CurrencyFilterRequest filterRequest) {
        Specification<Currency> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filterRequest.getCode() != null && !filterRequest.getCode().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("code")), "%" + filterRequest.getCode().toLowerCase() + "%"));
            }

            if (filterRequest.getName() != null && !filterRequest.getName().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + filterRequest.getName().toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return currencyRepository.findAll(spec).stream()
                .map(CurrencyMapper::toResponse)
                .collect(Collectors.toList());
    }
}
