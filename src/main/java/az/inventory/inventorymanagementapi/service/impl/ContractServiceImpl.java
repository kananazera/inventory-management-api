package az.inventory.inventorymanagementapi.service.impl;

import az.inventory.inventorymanagementapi.dto.contract.*;
import az.inventory.inventorymanagementapi.entity.Contract;
import az.inventory.inventorymanagementapi.entity.ContractFile;
import az.inventory.inventorymanagementapi.entity.Customer;
import az.inventory.inventorymanagementapi.entity.Supplier;
import az.inventory.inventorymanagementapi.exception.ResourceNotFoundException;
import az.inventory.inventorymanagementapi.exception.ValidationException;
import az.inventory.inventorymanagementapi.mapper.ContractMapper;
import az.inventory.inventorymanagementapi.repository.ContractFileRepository;
import az.inventory.inventorymanagementapi.repository.ContractRepository;
import az.inventory.inventorymanagementapi.repository.CustomerRepository;
import az.inventory.inventorymanagementapi.repository.SupplierRepository;
import az.inventory.inventorymanagementapi.service.ContractService;
import az.inventory.inventorymanagementapi.service.FileStorageService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;
    private final SupplierRepository supplierRepository;
    private final CustomerRepository customerRepository;
    private final ContractFileRepository contractFileRepository;
    private final FileStorageService fileStorageService;

    @Override
    public ContractResponse createContract(ContractCreateRequest request, List<MultipartFile> files) throws IOException {
        Supplier supplier = null;
        Customer customer = null;

        if (request.getEndDate() != null && request.getStartDate() != null) {
            long daysBetween = ChronoUnit.DAYS.between(request.getStartDate(), request.getEndDate());
            if (daysBetween < 7) {
                throw new ValidationException("Contract duration must be at least 7 days");
            }
        }

        if (request.getSupplierId() != null && request.getCustomerId() != null) {
            throw new ValidationException("Contract must be assigned to either a supplier or a customer, not both.");
        }

        if (request.getSupplierId() != null) {
            supplier = supplierRepository.findById(request.getSupplierId())
                    .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + request.getSupplierId()));
        }

        if (request.getCustomerId() != null) {
            customer = customerRepository.findById(request.getCustomerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + request.getCustomerId()));
        }

        Contract contract = ContractMapper.toEntity(request, supplier, customer);
        Contract savedContract = contractRepository.save(contract);

        saveContractFiles(savedContract, files);

        return ContractMapper.toResponse(savedContract);
    }

    @Override
    public ContractResponse updateContract(Long id, ContractUpdateRequest request, List<MultipartFile> files) throws IOException {
        if (request.getEndDate() != null && request.getStartDate() != null) {
            long daysBetween = ChronoUnit.DAYS.between(request.getStartDate(), request.getEndDate());
            if (daysBetween < 7) {
                throw new ValidationException("Contract duration must be at least 7 days");
            }
        }

        if (request.getSupplierId() != null && request.getCustomerId() != null) {
            throw new ValidationException("Contract must be assigned to either a supplier or a customer, not both.");
        }

        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contract not found with id: " + id));

        contract.setTitle(request.getTitle());
        contract.setDescription(request.getDescription());
        contract.setStartDate(request.getStartDate());
        contract.setEndDate(request.getEndDate());

        if (request.getSupplierId() != null) {
            Supplier supplier = supplierRepository.findById(request.getSupplierId())
                    .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + request.getSupplierId()));
            contract.setSupplier(supplier);
            contract.setCustomer(null);
        } else if (request.getCustomerId() != null) {
            Customer customer = customerRepository.findById(request.getCustomerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + request.getCustomerId()));
            contract.setCustomer(customer);
            contract.setSupplier(null);
        }

        Contract savedContract = contractRepository.save(contract);

        if (files != null && !files.isEmpty()) {
            saveContractFiles(savedContract, files);
        }

        return ContractMapper.toResponse(savedContract);
    }

    @Override
    public void deleteContract(Long id) {
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contract not found with id: " + id));

        List<ContractFile> files = contractFileRepository.findByContractId(id);

        for (ContractFile file : files) {
            try {
                fileStorageService.deleteFile(file.getFileUrl());
            } catch (Exception e) {
                System.err.println("Failed to delete file from storage: " + file.getFileUrl() + " error: " + e.getMessage());
            }
        }

        contractFileRepository.deleteAll(files);

        contractRepository.delete(contract);
    }

    @Override
    public ContractResponse getContractById(Long id) {
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contract not found with id: " + id));
        return ContractMapper.toResponse(contract);
    }

    @Override
    public List<ContractResponse> getAllContracts() {
        return contractRepository.findAll().stream()
                .map(ContractMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ContractResponse> filterContracts(ContractFilterRequest request) {
        Specification<Contract> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getTitle() != null && !request.getTitle().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("title")), "%" + request.getTitle().toLowerCase() + "%"));
            }

            if (request.getSupplierId() != null) {
                predicates.add(cb.equal(root.get("supplier").get("id"), request.getSupplierId()));
            }

            if (request.getCustomerId() != null) {
                predicates.add(cb.equal(root.get("customer").get("id"), request.getCustomerId()));
            }

            if (request.getStartDateFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("startDate"), request.getStartDateFrom()));
            }

            if (request.getStartDateTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("startDate"), request.getStartDateTo()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return contractRepository.findAll(spec).stream()
                .map(ContractMapper::toResponse)
                .collect(Collectors.toList());
    }

    private void saveContractFiles(Contract contract, List<MultipartFile> files) throws IOException {
        if (files == null || files.isEmpty()) return;

        List<ContractFile> contractFiles = new ArrayList<>();

        for (MultipartFile file : files) {
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null) continue;

            String storedFileUrl = fileStorageService.storeDocumentFile("contracts", file);

            ContractFile contractFile = ContractFile.builder()
                    .fileName(originalFilename)
                    .fileUrl(storedFileUrl)
                    .contract(contract)
                    .build();

            contractFiles.add(contractFile);
        }

        contractFileRepository.saveAll(contractFiles);
    }

    @Override
    public void addFileToContract(Long contractId, MultipartFile file) throws IOException {
        addFilesToContract(contractId, List.of(file));
    }

    @Override
    public void addFilesToContract(Long contractId, List<MultipartFile> files) throws IOException {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new ResourceNotFoundException("Contract not found with id: " + contractId));

        for (MultipartFile file : files) {
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || originalFilename.isBlank()) {
                throw new ValidationException("File name is invalid");
            }

            String fileUrl = fileStorageService.storeDocumentFile("contracts", file);

            ContractFile contractFile = ContractFile.builder()
                    .fileName(originalFilename)
                    .fileUrl(fileUrl)
                    .contract(contract)
                    .build();

            contractFileRepository.save(contractFile);
        }
    }

    @Override
    public void deleteFileFromContract(Long contractFileId) {
        ContractFile file = contractFileRepository.findById(contractFileId)
                .orElseThrow(() -> new ResourceNotFoundException("Contract file not found with id: " + contractFileId));

        fileStorageService.deleteFile(file.getFileUrl());

        contractFileRepository.delete(file);
    }

    @Override
    public void deleteAllFilesFromContract(Long contractId) {
        List<ContractFile> files = contractFileRepository.findByContractId(contractId);

        for (ContractFile file : files) {
            fileStorageService.deleteFile(file.getFileUrl());
        }

        contractFileRepository.deleteAll(files);
    }
}
