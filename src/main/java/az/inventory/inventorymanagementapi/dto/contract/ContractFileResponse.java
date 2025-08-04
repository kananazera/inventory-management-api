package az.inventory.inventorymanagementapi.dto.contract;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContractFileResponse {
    private Long id;
    private String fileName;
    private String fileUrl;
}
