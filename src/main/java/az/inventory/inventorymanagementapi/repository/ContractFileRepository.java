package az.inventory.inventorymanagementapi.repository;

import az.inventory.inventorymanagementapi.entity.ContractFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractFileRepository extends JpaRepository<ContractFile, Long> {

    List<ContractFile> findByContractId(Long contractId);
}
