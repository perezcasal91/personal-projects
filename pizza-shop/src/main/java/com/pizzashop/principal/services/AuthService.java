package com.pizzashop.principal.services;

import com.pizzashop.principal.dtos.AuthRequestDTO;
import com.pizzashop.principal.dtos.AuthResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    AuthResponseDTO login(final AuthRequestDTO authRequestDTO);

    AuthResponseDTO refresh(HttpServletRequest request,
                            HttpServletResponse response);

    void logout();
}
