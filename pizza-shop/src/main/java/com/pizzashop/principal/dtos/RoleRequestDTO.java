package com.pizzashop.principal.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleRequestDTO {

    @Schema(name = "name", description = "Name",
            type = "String", example = "USER")
    @NotEmpty
    @NotNull
    @NotBlank
    private String name;

    @Schema(name = "description", description = "Description",
            type = "String", example = "USER")
    @NotEmpty
    @NotNull
    @NotBlank
    private String description;

}
