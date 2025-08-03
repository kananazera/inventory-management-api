package az.inventory.inventorymanagementapi.service.impl;

import az.inventory.inventorymanagementapi.dto.customer.CustomerCreateRequest;
import az.inventory.inventorymanagementapi.dto.customer.CustomerFilterRequest;
import az.inventory.inventorymanagementapi.dto.customer.CustomerResponse;
import az.inventory.inventorymanagementapi.dto.customer.CustomerUpdateRequest;
import az.inventory.inventorymanagementapi.entity.Customer;
import az.inventory.inventorymanagementapi.exception.ResourceNotFoundException;
import az.inventory.inventorymanagementapi.mapper.CustomerMapper;
import az.inventory.inventorymanagementapi.repository.CustomerRepository;
import az.inventory.inventorymanagementapi.service.CustomerService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public CustomerResponse createCustomer(CustomerCreateRequest request) {
        if (customerRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        Customer customer = CustomerMapper.toEntity(request);
        return CustomerMapper.toResponse(customerRepository.save(customer));
    }

    @Override
    public CustomerResponse updateCustomer(Long id, CustomerUpdateRequest request) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));

        if (request.getEmail() != null && !request.getEmail().equalsIgnoreCase(customer.getEmail())) {
            if (customerRepository.existsByEmailIgnoreCaseAndIdNot(request.getEmail(), id)) {
                throw new RuntimeException("Email already exists");
            }
            customer.setEmail(request.getEmail());
        }

        if (request.getFullName() != null) {
            customer.setFullName(request.getFullName());
        }

        if (request.getPhone() != null) {
            customer.setPhone(request.getPhone());
        }

        if (request.getAddress() != null) {
            customer.setAddress(request.getAddress());
        }

        if (request.getGender() != null) {
            customer.setGender(request.getGender());
        }

        if (request.getBirthDate() != null) {
            customer.setBirthDate(request.getBirthDate());
        }

        if (request.getActive() != null) {
            customer.setActive(request.getActive());
        }

        if (request.getContactType() != null) {
            customer.setContactType(request.getContactType());
        }

        if (request.getTin() != null) {
            customer.setTin(request.getTin());
        }

        return CustomerMapper.toResponse(customerRepository.save(customer));
    }

    @Override
    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        customerRepository.delete(customer);
    }

    @Override
    public CustomerResponse getCustomerById(Long id) {
        return customerRepository.findById(id)
                .map(CustomerMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(CustomerMapper::toResponse)
                .toList();
    }

    @Override
    public List<CustomerResponse> filterCustomers(CustomerFilterRequest filterRequest) {
        Specification<Customer> spec = (root, query, cb) -> {
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

        return customerRepository.findAll(spec).stream()
                .map(CustomerMapper::toResponse)
                .toList();
    }
}
