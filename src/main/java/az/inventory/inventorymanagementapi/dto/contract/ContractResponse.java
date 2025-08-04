package az.inventory.inventorymanagementapi.dto.contract;

import az.inventory.inventorymanagementapi.entity.Customer;
import az.inventory.inventorymanagementapi.entity.Supplier;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class ContractResponse {

    private Long id;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long supplierId;
    private Long customerId;
    private String supplierFullName;
    private String customerFullName;
    private List<ContractFileResponse> files;
}
