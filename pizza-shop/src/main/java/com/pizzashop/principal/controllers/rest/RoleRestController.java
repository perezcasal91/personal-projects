package com.pizzashop.principal.controllers.rest;

import com.pizzashop.principal.dtos.ExceptionResponseDTO;
import com.pizzashop.principal.dtos.RoleRequestDTO;
import com.pizzashop.principal.entities.RoleEntity;
import com.pizzashop.principal.services.RoleService;
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
@RequestMapping("/api/v1/roles")
@SecurityRequirement(name = "Bearer Authentication")
@PreAuthorize("hasRole('ADMIN')")
@Slf4j
public class RoleRestController {

    @Autowired
    private RoleService roleService;

    @Operation(
            summary = "Save Role",
            description = "Save a new role into the database."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RoleEntity.class))),
            @ApiResponse(responseCode = "409", description = "Name is already in use.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponseDTO.class)))}
    )
    @PostMapping
    public ResponseEntity<?> saveRole(@RequestBody @Valid final RoleRequestDTO roleRequestDTO) {
        log.info("Executing saveRole from RoleRestController");

        return ResponseEntity.ok(roleService.saveRole(roleRequestDTO));
    }

    @Operation(
            summary = "Update Role",
            description = "Update a existent role from the database."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RoleEntity.class)))}
    )
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateRole(@PathVariable final Long id,
                                        @RequestBody @Valid final RoleRequestDTO roleRequestDTO) {
        log.info("Executing updateRole from RoleRestController");

        return ResponseEntity.ok(roleService.updateRole(id, roleRequestDTO));
    }

    @Operation(
            summary = "Delete Role",
            description = "Delete a existent role from the database."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RoleEntity.class)))}
    )
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable final Long id) {
        log.info("Executing deleteRole from RoleRestController");

        return ResponseEntity.ok(roleService.deleteRole(id));
    }

    @Operation(
            summary = "Find All Roles",
            description = "Find all existent roles from the database."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = List.class)))}
    )
    @GetMapping
    public ResponseEntity<?> getAllRoles() {
        log.info("Executing getAllRoles from RoleRestController");

        return ResponseEntity.ok(roleService.findAllRoles());
    }

    @Operation(
            summary = "Find a Role",
            description = "Find by id a existent role from the database."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RoleEntity.class)))}
    )
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getRoleById(@PathVariable final Long id) {
        log.info("Executing getRoleById from RoleRestController");

        return ResponseEntity.ok(roleService.findRoleById(id));
    }

    @Operation(
            summary = "Find a Role",
            description = "Find by name a existent role from the database."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RoleEntity.class)))}
    )
    @GetMapping(value = "/by/name/{name}")
    public ResponseEntity<?> getRoleByName(@PathVariable final String name) {
        log.info("Executing getRoleByName from RoleRestController");

        return ResponseEntity.ok(roleService.findRoleByName(name));
    }

}
