package com.pizzashop.principal.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AuthRequestDTO {

    @Schema(name = "username", description = "Username",
            type = "String", example = "elio")
    @NotEmpty
    @NotNull
    @NotBlank
    private String username;

    @Schema(name = "password", description = "Password",
            type = "String", example = "Elio2023")
    @NotEmpty
    @NotNull
    @NotBlank
    private String password;

}
