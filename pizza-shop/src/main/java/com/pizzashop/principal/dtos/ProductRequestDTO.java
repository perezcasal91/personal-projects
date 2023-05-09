package com.pizzashop.principal.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductRequestDTO {

    @Schema(name = "name", description = "Name",
            type = "String", example = "PIZZA")
    @NotEmpty
    @NotNull
    @NotBlank
    private String name;

    @Schema(name = "description", description = "Description",
            type = "String", example = "PIZZA of peperoni")
    private String description;

    @Schema(name = "amount", description = "Amount",
            type = "Integer", example = "20")
    private Integer amount;

    @Schema(name = "price", description = "Price",
            type = "Double", example = "17.5")
    @NotNull
    @DecimalMin(value = "0.0")
    @Digits(integer = 3, fraction = 2)
    private Double price;

    @Schema(name = "imageUrl", description = "Image Url",
            type = "String", example = "/static/img/p.1")
    @NotEmpty
    @NotNull
    @NotBlank
    private String imageUrl;

    @Schema(name = "statusValue", description = "Status",
            type = "String", example = "STOCK")
    private String status;

    @Schema(name = "categoryId", description = "Category Id",
            type = "Long", example = "1")
    @NotNull
    private Long categoryId;

}
