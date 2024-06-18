package com.epic.spring_boot_CRUD.controller;

import com.epic.spring_boot_CRUD.dto.ProductDTO;
import com.epic.spring_boot_CRUD.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@Slf4j
@Tag(name = "Product", description = "Product management APIs")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/add")

    @Operation(summary = "Create a new product", description = "Adds a new product to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product created", content = @Content(schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })

    public ProductDTO createProduct(@RequestBody ProductDTO productDTO) {
        log.info("Request to create and add a product: {}", productDTO);
        return productService.createProduct(productDTO);
    }

    @GetMapping("/getAll")

    @Operation(summary = "Get all products", description = "Returns a paginated list of products sorted by the specified field")

    public ResponseEntity<Page<ProductDTO>> getAllProducts(

            @Parameter(description = "Page number (0-based)", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size", example = "10") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Sort by field", example = "id") @RequestParam(defaultValue = "id") String sortBy) {

        Page<ProductDTO> products = productService.getAllProducts(page, size, sortBy);
        log.info("Returning all products :{}", products.getContent());
        return ResponseEntity.ok(products);
    }

    @GetMapping("/getAll/{id}")

    @Operation(summary = "Retrieving a product", description = "Retrieves a product by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product Found", content = @Content(schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "404", description = "Product Not Found", content = @Content)
    })

    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        log.info("Request to return the details of the product with product id: {}", id);
        ProductDTO product = productService.getProductById(id);
        log.info("Returning the product details for the id: {}", product);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/update/{id}")

    @Operation(summary = "Updating a product", description = "Updates an existing product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated", content = @Content(schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product Not Found", content = @Content)
    })

    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDetails) {
        log.info("Request to update product id: {}, with product details: {}", id, productDetails);
        ProductDTO updatedProduct = productService.updateProduct(id, productDetails);
        log.info("Returning updated product: {}", updatedProduct);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/delete/{id}")

    @Operation(summary = "Delete a product", description = "Deletes a pre-existing product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product deleted", content = @Content(schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "404", description = "Product Not Found", content = @Content)
    })

    public void deleteProduct(@PathVariable Long id) {
        log.info("Request to delete a product with product id: {}", id);
        productService.deleteProduct(id);
        log.info("Product with product id {} was deleted", id);
    }
}
