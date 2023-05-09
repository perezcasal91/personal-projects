package com.pizzashop.principal.controllers.rest;

import com.pizzashop.principal.dtos.ProductRequestDTO;
import com.pizzashop.principal.entities.ProductEntity;
import com.pizzashop.principal.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@SecurityRequirement(name = "Bearer Authentication")
@PreAuthorize("hasRole('ADMIN')")
@Slf4j
public class ProductRestController {

    @Autowired
    private ProductService productService;

    @Operation(
            summary = "Save Product",
            description = "Save a new product into the database."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductEntity.class))),
            @ApiResponse(responseCode = "409", description = "Name is already in use.")}
    )
    @PostMapping
    public ResponseEntity<?> saveProduct(@RequestBody @Valid final ProductRequestDTO productRequestDTO) {
        log.info("Executing saveProduct from ProductRestController");

        return ResponseEntity.ok(productService.saveProduct(productRequestDTO));
    }

    @Operation(
            summary = "Update Product",
            description = "Update an existent product from the database."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductEntity.class)))}
    )
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable final Long id,
                                           @RequestBody @Valid final ProductRequestDTO productRequestDTO) {
        log.info("Executing updateProduct from ProductRestController");

        return ResponseEntity.ok(productService.updateProduct(id, productRequestDTO));
    }

    @Operation(
            summary = "Delete Product",
            description = "Delete a existent product from the database."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductEntity.class)))}
    )
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable final Long id) {
        log.info("Executing deleteProduct from ProductRestController");

        return ResponseEntity.ok(productService.deleteProduct(id));
    }

    @Operation(
            summary = "Find All Products",
            description = "Find all existent products from the database."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = List.class)))}
    )
    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        log.info("Executing getAllProducts from ProductRestController");

        return ResponseEntity.ok(productService.findAllProducts());
    }

    @Operation(
            summary = "Find Products",
            description = "Find by statusValue a existent products from the database."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = List.class)))}
    )
    @GetMapping(value = "/by/status/{value}")
    public ResponseEntity<?> getProductsByStatus(@PathVariable final String value) {
        log.info("Executing getProductByStatus from ProductRestController");

        return ResponseEntity.ok(productService.findProductsByStatus(value));
    }

    @Operation(
            summary = "Find Products",
            description = "Find by category id a existent products from the database."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = List.class)))}
    )
    @GetMapping(value = "/by/category/{id}")
    public ResponseEntity<?> getProductsByCategoryId(@PathVariable final Long id) {
        log.info("Executing getProductByCategory from ProductRestController");

        return ResponseEntity.ok(productService.findProductsByCategoryId(id));
    }

    @Operation(
            summary = "Find a Product",
            description = "Find by id a existent product from the database."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductEntity.class)))}
    )
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getProductById(@PathVariable final Long id) {
        log.info("Executing getProductById from ProductRestController");

        return ResponseEntity.ok(productService.findProductById(id));
    }

    @Operation(
            summary = "Find a Product",
            description = "Find by name a existent product from the database."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductEntity.class)))}
    )
    @GetMapping(value = "/by/name/{name}")
    public ResponseEntity<?> getProductByName(@PathVariable final String name) {
        log.info("Executing getProductByName from ProductRestController");

        return ResponseEntity.ok(productService.findProductByName(name));
    }


}
