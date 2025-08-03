package az.inventory.inventorymanagementapi.service;

import az.inventory.inventorymanagementapi.dto.customer.CustomerCreateRequest;
import az.inventory.inventorymanagementapi.dto.customer.CustomerFilterRequest;
import az.inventory.inventorymanagementapi.dto.customer.CustomerResponse;
import az.inventory.inventorymanagementapi.dto.customer.CustomerUpdateRequest;

import java.util.List;

public interface CustomerService {

    CustomerResponse createCustomer(CustomerCreateRequest request);

    CustomerResponse updateCustomer(Long id, CustomerUpdateRequest request);

    void deleteCustomer(Long id);

    CustomerResponse getCustomerById(Long id);

    List<CustomerResponse> getAllCustomers();

    List<CustomerResponse> filterCustomers(CustomerFilterRequest filterRequest);
}
