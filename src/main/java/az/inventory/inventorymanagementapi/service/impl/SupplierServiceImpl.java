package az.inventory.inventorymanagementapi.service.impl;

import az.inventory.inventorymanagementapi.dto.supplier.SupplierCreateRequest;
import az.inventory.inventorymanagementapi.dto.supplier.SupplierFilterRequest;
import az.inventory.inventorymanagementapi.dto.supplier.SupplierResponse;
import az.inventory.inventorymanagementapi.dto.supplier.SupplierUpdateRequest;
import az.inventory.inventorymanagementapi.entity.Supplier;
import az.inventory.inventorymanagementapi.exception.ResourceNotFoundException;
import az.inventory.inventorymanagementapi.mapper.SupplierMapper;
import az.inventory.inventorymanagementapi.repository.SupplierRepository;
import az.inventory.inventorymanagementapi.service.SupplierService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    @Override
    public SupplierResponse createSupplier(SupplierCreateRequest request) {
        if (supplierRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        Supplier supplier = SupplierMapper.toEntity(request);
        return SupplierMapper.toResponse(supplierRepository.save(supplier));
    }

    @Override
    public SupplierResponse updateSupplier(Long id, SupplierUpdateRequest request) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + id));

        if (request.getEmail() != null && !request.getEmail().equalsIgnoreCase(supplier.getEmail())) {
            if (supplierRepository.existsByEmailIgnoreCaseAndIdNot(request.getEmail(), id)) {
                throw new RuntimeException("Email already exists");
            }
            supplier.setEmail(request.getEmail());
        }

        if (request.getFullName() != null) {
            supplier.setFullName(request.getFullName());
        }

        if (request.getPhone() != null) {
            supplier.setPhone(request.getPhone());
        }

        if (request.getAddress() != null) {
            supplier.setAddress(request.getAddress());
        }

        if (request.getGender() != null) {
            supplier.setGender(request.getGender());
        }

        if (request.getBirthDate() != null) {
            supplier.setBirthDate(request.getBirthDate());
        }

        if (request.getActive() != null) {
            supplier.setActive(request.getActive());
        }

        if (request.getContactType() != null) {
            supplier.setContactType(request.getContactType());
        }

        if (request.getTin() != null) {
            supplier.setTin(request.getTin());
        }

        return SupplierMapper.toResponse(supplierRepository.save(supplier));
    }

    @Override
    public void deleteSupplier(Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + id));
        supplierRepository.delete(supplier);
    }

    @Override
    public SupplierResponse getSupplierById(Long id) {
        return supplierRepository.findById(id)
                .map(SupplierMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + id));
    }

    @Override
    public List<SupplierResponse> getAllSuppliers() {
        return supplierRepository.findAll().stream()
                .map(SupplierMapper::toResponse)
                .toList();
    }

    @Override
    public List<SupplierResponse> filterSuppliers(SupplierFilterRequest filterRequest) {
        Specification<Supplier> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filterRequest.getEmail() != null && !filterRequest.getEmail().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("email")),
                        "%" + filterRequest.getEmail().toLowerCase() + "%"));
            }

            if (filterRequest.getFullName() != null && !filterRequest.getFullName().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("fullName")),
                        "%" + filterRequest.getFullName().toLowerCase() + "%"));
            }

            if (filterRequest.getPhone() != null && !filterRequest.getPhone().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("phone")),
                        "%" + filterRequest.getPhone().toLowerCase() + "%"));
            }

            if (filterRequest.getGender() != null) {
                predicates.add(cb.equal(root.get("gender"), filterRequest.getGender()));
            }

            if (filterRequest.getBirthDate() != null) {
                predicates.add(cb.equal(root.get("birthDate"), filterRequest.getBirthDate()));
            }

            if (filterRequest.getActive() != null) {
                predicates.add(cb.equal(root.get("active"), filterRequest.getActive()));
            }

            if (filterRequest.getContactType() != null) {
                predicates.add(cb.equal(root.get("contactType"), filterRequest.getContactType()));
            }

            if (filterRequest.getTin() != null) {
                predicates.add(cb.equal(root.get("tin"), filterRequest.getTin()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return supplierRepository.findAll(spec).stream()
                .map(SupplierMapper::toResponse)
                .toList();
    }
}
