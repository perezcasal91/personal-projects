package com.pizzashop.principal.controllers.rest;

import com.pizzashop.principal.dtos.UserRequestDTO;
import com.pizzashop.principal.entities.UserEntity;
import com.pizzashop.principal.services.UserService;
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
@RequestMapping("/api/v1/users")
@SecurityRequirement(name = "Bearer Authentication")
@PreAuthorize("hasRole('ADMIN')")
@Slf4j
public class UserRestController {

    @Autowired
    private UserService userService;

    @Operation(
            summary = "Save User",
            description = "Save a new user into the database."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))),
            @ApiResponse(responseCode = "409",
                    description = "Username or Email is already in use.")
    })
    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody @Valid final UserRequestDTO UserRequestDTO) {
        log.info("Executing saveUser from UserRestController");

        return ResponseEntity.ok(userService.saveUser(UserRequestDTO));
    }

    @Operation(
            summary = "Update User",
            description = "Update a existent user from the database."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class)))}
    )
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateUser(@PathVariable final Long id,
                                        @RequestBody @Valid final UserRequestDTO UserRequestDTO) {
        log.info("Executing updateUser from UserRestController");

        return ResponseEntity.ok(userService.updateUser(id, UserRequestDTO));
    }

    @Operation(
            summary = "Delete User",
            description = "Delete a existent user from the database."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class)))}
    )
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable final Long id) {
        log.info("Executing deleteUser from UserRestController");

        return ResponseEntity.ok(userService.deleteUser(id));
    }

    @Operation(
            summary = "Find All Users",
            description = "Find all existent users from the database."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = List.class)))}
    )
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        log.info("Executing getAllUsers from UserRestController");

        return ResponseEntity.ok(userService.findAllUsers());
    }

    @Operation(
            summary = "Find a User",
            description = "Find by id a existent user from the database."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class)))}
    )
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getUserById(@PathVariable final Long id) {
        log.info("Executing getUserById from UserRestController");

        return ResponseEntity.ok(userService.findUserById(id));
    }

    @Operation(
            summary = "Find a User",
            description = "Find by username a existent user from the database."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class)))}
    )
    @GetMapping(value = "/by/username/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable final String username) {
        log.info("Executing getUserByUsername from UserRestController");

        return ResponseEntity.ok(userService.findUserByUsername(username));
    }

    @Operation(
            summary = "Find a User",
            description = "Find by email a existent user from the database."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class)))}
    )
    @GetMapping(value = "/by/email/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable final String email) {
        log.info("Executing getUserByEmail from UserRestController");

        return ResponseEntity.ok(userService.findUserByEmail(email));
    }


}
