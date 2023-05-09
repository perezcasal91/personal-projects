package com.pizzashop.principal.controllers.rest;

import com.pizzashop.principal.dtos.AuthRequestDTO;
import com.pizzashop.principal.dtos.AuthResponseDTO;
import com.pizzashop.principal.dtos.MessageResponseDTO;
import com.pizzashop.principal.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/")
@Slf4j
public class AuthRestController {

    @Autowired
    HttpServletRequest request;
    @Autowired
    HttpServletResponse response;
    @Autowired
    private AuthService authService;

    @Operation(
            summary = "App Login",
            description = "Login into the app with username and password."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "406", description = "Not acceptable")}
    )
    @PostMapping(value = "login")
    public ResponseEntity<?> login(@RequestBody @Valid final AuthRequestDTO authRequestDTO) {
        log.info("Executing login from AuthRestController");

        return ResponseEntity.ok(authService.login(authRequestDTO));
    }

    @Operation(
            summary = "App Refresh",
            description = "Refresh the token when is expired."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponseDTO.class)))}
    )
    @PostMapping(value = "refresh")
    public ResponseEntity<?> refresh() {
        log.info("Executing refresh from AuthRestController");

        return ResponseEntity.ok(authService.refresh(request, response));
    }

    @Operation(
            summary = "App Logout",
            description = "Logout from the app.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponseDTO.class)))}
    )
    @PostMapping(value = "logout")
    public ResponseEntity<?> logout() {
        log.info("Executing logout from AuthRestController");

        authService.logout();

        return ResponseEntity.ok(new MessageResponseDTO("Logout successfully."));
    }

}
