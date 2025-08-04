package az.inventory.inventorymanagementapi.controller;

import az.inventory.inventorymanagementapi.dto.contract.*;
import az.inventory.inventorymanagementapi.service.ContractService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/contracts")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ContractResponse> createContract(
            @Valid @RequestPart("contract") ContractCreateRequest request,
            @RequestPart(value = "files", required = false) List<MultipartFile> files
    ) throws IOException {
        ContractResponse response = contractService.createContract(request, files);
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ContractResponse> updateContract(
            @PathVariable Long id,
            @Valid @RequestPart("contract") ContractUpdateRequest request,
            @RequestPart(value = "files", required = false) List<MultipartFile> files
    ) throws IOException {
        ContractResponse response = contractService.updateContract(id, request, files);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContract(@PathVariable Long id) {
        contractService.deleteContract(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContractResponse> getContractById(@PathVariable Long id) {
        ContractResponse response = contractService.getContractById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ContractResponse>> getAllContracts() {
        List<ContractResponse> contracts = contractService.getAllContracts();
        return ResponseEntity.ok(contracts);
    }

    @PostMapping("/filter")
    public ResponseEntity<List<ContractResponse>> filterContracts(@RequestBody ContractFilterRequest filterRequest) {
        List<ContractResponse> filtered = contractService.filterContracts(filterRequest);
        return ResponseEntity.ok(filtered);
    }

    @PostMapping("/{id}/files")
    public ResponseEntity<Void> addFilesToContract(
            @PathVariable Long id,
            @RequestParam("files") List<MultipartFile> files) throws IOException {

        contractService.addFilesToContract(id, files);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/files/{fileId}")
    public ResponseEntity<Void> deleteFileFromContract(@PathVariable Long fileId) {
        contractService.deleteFileFromContract(fileId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/files")
    public ResponseEntity<Void> deleteAllFilesFromContract(@PathVariable Long id) {
        contractService.deleteAllFilesFromContract(id);
        return ResponseEntity.noContent().build();
    }
}
