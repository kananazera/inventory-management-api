package az.inventory.inventorymanagementapi.service;

import az.inventory.inventorymanagementapi.dto.contract.ContractCreateRequest;
import az.inventory.inventorymanagementapi.dto.contract.ContractFilterRequest;
import az.inventory.inventorymanagementapi.dto.contract.ContractResponse;
import az.inventory.inventorymanagementapi.dto.contract.ContractUpdateRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ContractService {

    ContractResponse createContract(ContractCreateRequest request, List<MultipartFile> files) throws IOException;

    ContractResponse updateContract(Long id, ContractUpdateRequest request, List<MultipartFile> files) throws IOException;

    void deleteContract(Long id);

    ContractResponse getContractById(Long id);

    List<ContractResponse> getAllContracts();

    List<ContractResponse> filterContracts(ContractFilterRequest filterRequest);

    void addFileToContract(Long contractId, MultipartFile file) throws IOException;

    void addFilesToContract(Long contractId, List<MultipartFile> files) throws IOException;

    void deleteFileFromContract(Long contractFileId);

    void deleteAllFilesFromContract(Long contractId);
}