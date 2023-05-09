package com.pizzashop.principal.controllers.rest;

import com.pizzashop.principal.dtos.CategoryRequestDTO;
import com.pizzashop.principal.entities.CategoryEntity;
import com.pizzashop.principal.services.CategoryService;
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
@RequestMapping("/api/v1/categories")
@SecurityRequirement(name = "Bearer Authentication")
@PreAuthorize("hasRole('ADMIN')")
@Slf4j
public class CategoryRestController {

    @Autowired
    private CategoryService categoryService;

    @Operation(
            summary = "Save Category",
            description = "Save a new category into the database."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoryEntity.class))),
            @ApiResponse(responseCode = "409", description = "Name is already in use.")}
    )
    @PostMapping
    public ResponseEntity<?> saveCategory(@RequestBody @Valid final
                                          CategoryRequestDTO categoryRequestDTO) {
        log.info("Executing saveCategory from CategoryRestController");

        return ResponseEntity.ok(categoryService.saveCategory(categoryRequestDTO));
    }

    @Operation(
            summary = "Update Category",
            description = "Update a existent category from the database."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoryEntity.class)))}
    )
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable final Long id,
                                            @RequestBody @Valid final
                                            CategoryRequestDTO categoryRequestDTO) {
        log.info("Executing updateCategory from CategoryRestController");

        return ResponseEntity.ok(categoryService.updateCategory(id, categoryRequestDTO));
    }

    @Operation(
            summary = "Delete Category",
            description = "Delete a existent category from the database."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoryEntity.class)))}
    )
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable final Long id) {
        log.info("Executing deleteCategory from CategoryRestController");

        return ResponseEntity.ok(categoryService.deleteCategory(id));
    }

    @Operation(
            summary = "Find All Categories",
            description = "Find all existent categories from the database."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = List.class)))}
    )
    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        log.info("Executing getAllCategories from CategoryRestController");

        return ResponseEntity.ok(categoryService.findAllCategories());
    }

    @Operation(
            summary = "Find a Category",
            description = "Find by id a existent category from the database."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoryEntity.class)))}
    )
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable final Long id) {
        log.info("Executing getCategoryById from CategoryRestController");

        return ResponseEntity.ok(categoryService.findCategoryById(id));
    }

    @Operation(
            summary = "Find a Category",
            description = "Find by name a existent category from the database."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoryEntity.class)))}
    )
    @GetMapping(value = "/by/name/{name}")
    public ResponseEntity<?> getCategoryByName(@PathVariable final String name) {
        log.info("Executing getCategoryByName from CategoryRestController");

        return ResponseEntity.ok(categoryService.findCategoryByName(name));
    }

}
