package com.epic.spring_boot_CRUD.controller;

import com.epic.spring_boot_CRUD.dto.ProductDTO;
import com.epic.spring_boot_CRUD.entity.Product;
import com.epic.spring_boot_CRUD.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
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
    ProductService productService;

    @PostMapping("/add")

    //api documentation using Swagger
    @Operation(summary = "Create a new product", description = "Adds a new product to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product created", content = @Content(schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    public ProductDTO createProduct(@RequestBody ProductDTO productDTO){

        //logging a message with the data that is passed to the parameters
        log.info("Request to create and add a product: {}", productDTO);

        return productService.createProduct(productDTO);
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<ProductDTO>> getAllProducts(

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,

            //sorting the expected result based on ID where it will generate the output in ascending order
            @RequestParam(defaultValue = "id") String sortBy) {

        Page<ProductDTO> products = productService.getAllProducts(page, size, sortBy);

        //logging a message with all the products and the information related to each
        log.info("Returning all products :{}", products.getContent());

        return ResponseEntity.ok(products);
    }

    @GetMapping("/getAll/{id}")

    //api documentation using Swagger
    @Operation(summary = "Retrieving a product", description = "Retrieves a product by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product Found", content = @Content(schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "404", description = "Product Not Found", content = @Content)
    })

    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id){

        //logging a message with the id of the requested product
        log.info("Request to return the details of the product with product id: {}", id);

        ProductDTO product = productService.getProductById(id);

        //logging a message with the information of the product related to the specific id
        log.info("Returning the product details for the id: {}", product);

        return ResponseEntity.ok(product);
    }

    @PutMapping("/update/{id}")

    //api documentation using Swagger
    @Operation(summary = "Updating a product", description = "Updates an existing a product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated", content = @Content(schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product Not Found", content = @Content)
    })

    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDetails){

        //logging a message with the id and details that is requested to update
        log.info("Request to update product id: {}, with product details: {}", id, productDetails);

        ProductDTO updatedProduct = productService.updateProduct(id, productDetails);

        //logging a message with the updated details
        log.info("Returning updated product: {}", updatedProduct);

        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/delete/{id}")

    //api documentation using Swagger
    @Operation(summary = "Delete a product", description = "Deletes a pre existing product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product deleted", content = @Content(schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "404", description = "Product Not Found", content = @Content)
    })

    public void deleteProduct(@PathVariable Long id){

        //logging a message the product id that is requested to delete
        log.info("Request to delete a product with product id: {}", id);

        productService.deleteProduct(id);

        //logging a message indicating the deletion with the product id
        log.info("Product with product id {} was deleted", id);
    }
}