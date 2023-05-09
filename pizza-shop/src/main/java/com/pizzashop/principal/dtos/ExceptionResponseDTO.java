package com.pizzashop.principal.dtos;

import java.time.LocalDate;

public record ExceptionResponseDTO(int statusValue, String message, LocalDate date) {
}
