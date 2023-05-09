package com.pizzashop.principal.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRequestDTO {

    @Schema(name = "firstName", description = "First Name",
            type = "String", example = "Elieser")
    @NotEmpty
    @NotNull
    @NotBlank
    @Pattern(regexp = "^\\S(.*\\S)?$")
    private String firstName;

    @Schema(name = "middleName", description = "Middle Name",
            type = "String", example = "Elio")
    private String middleName;

    @Schema(name = "lastName", description = "Last Name",
            type = "String", example = "Perez Casal")
    @NotEmpty
    @NotNull
    @NotBlank
    private String lastName;

    @Schema(name = "username", description = "Username",
            type = "String", example = "elio")
    @NotEmpty
    @NotNull
    @NotBlank
    @Pattern(regexp = "^[a-z0-9]+(_?[a-z0-9]+)*$")
    private String username;

    @Schema(name = "email", description = "Email",
            type = "String", example = "elio@elio.com")
    @NotEmpty
    @NotNull
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private String email;

    @Schema(name = "password", description = "Password",
            type = "String", example = "Elio2023")
    @NotEmpty
    @NotNull
    @NotBlank
    @Size(min = 8)
    private String password;

    @Schema(name = "phone", description = "Phone",
            type = "String", example = "555-555-5555")
    @Pattern(regexp = "^\\d{2,3}-\\d{2,3}-\\d{4}$")
    private String phone;

    @Schema(name = "roles", description = "Roles",
            type = "String", example = "ADMIN, USER")
    @NotEmpty
    @NotNull
    @NotBlank
    private String roles;

}
