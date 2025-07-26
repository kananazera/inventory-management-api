    package com.example.inventorymanagementapi.controller;
    
    import com.example.inventorymanagementapi.dto.product.ProductFilterRequest;
    import com.example.inventorymanagementapi.dto.product.ProductCreateRequest;
    import com.example.inventorymanagementapi.dto.product.ProductResponse;
    import com.example.inventorymanagementapi.dto.product.ProductUpdateRequest;
    import com.example.inventorymanagementapi.service.ProductService;
    import jakarta.validation.Valid;
    import lombok.RequiredArgsConstructor;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.multipart.MultipartFile;
    
    import java.io.IOException;
    import java.util.List;
    
    @RestController
    @RequestMapping("/api/products")
    @RequiredArgsConstructor
    public class ProductController {
    
        private final ProductService productService;
    
        @PostMapping(consumes = {"multipart/form-data"})
        public ResponseEntity<ProductResponse> create(
                @Valid @ModelAttribute ProductCreateRequest request,
                @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
    
            ProductResponse response = productService.createProduct(request, image);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
    
        @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
        public ResponseEntity<ProductResponse> update(
                @PathVariable Long id,
                @ModelAttribute ProductUpdateRequest request,
                @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
    
            ProductResponse response = productService.updateProduct(id, request, image);
            return ResponseEntity.ok(response);
        }
    
        @GetMapping
        public ResponseEntity<List<ProductResponse>> getAll() {
            List<ProductResponse> list = productService.getAllProducts();
            return ResponseEntity.ok(list);
        }
    
        @GetMapping("/{id}")
        public ResponseEntity<ProductResponse> getById(@PathVariable Long id) {
            ProductResponse response = productService.getProductById(id);
            return ResponseEntity.ok(response);
        }
    
        @DeleteMapping("/{id}")
        public ResponseEntity<String> delete(@PathVariable Long id) {
            productService.deleteProduct(id);
            return ResponseEntity.ok("Product deleted successfully");
        }
    
        @PostMapping("/filter")
        public ResponseEntity<List<ProductResponse>> filter(@RequestBody ProductFilterRequest filterRequest) {
            List<ProductResponse> list = productService.filterProducts(filterRequest);
            return ResponseEntity.ok(list);
        }
    }
