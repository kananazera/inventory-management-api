package az.inventory.inventorymanagementapi.controller;

import az.inventory.inventorymanagementapi.dto.customer.CustomerCreateRequest;
import az.inventory.inventorymanagementapi.dto.customer.CustomerResponse;
import az.inventory.inventorymanagementapi.dto.customer.CustomerUpdateRequest;
import az.inventory.inventorymanagementapi.dto.customer.CustomerFilterRequest;
import az.inventory.inventorymanagementapi.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponse> create(@Valid @RequestBody CustomerCreateRequest request) {
        CustomerResponse response = customerService.createCustomer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody CustomerUpdateRequest request) {
        CustomerResponse response = customerService.updateCustomer(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAll() {
        List<CustomerResponse> list = customerService.getAllCustomers();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getById(@PathVariable Long id) {
        CustomerResponse response = customerService.getCustomerById(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok("Customer deleted successfully");
    }

    @PostMapping("/filter")
    public ResponseEntity<List<CustomerResponse>> filter(@RequestBody CustomerFilterRequest filterRequest) {
        List<CustomerResponse> list = customerService.filterCustomers(filterRequest);
        return ResponseEntity.ok(list);
    }
}
