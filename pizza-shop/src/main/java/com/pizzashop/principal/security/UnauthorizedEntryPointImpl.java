package com.pizzashop.principal.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;

@Component
@Slf4j
public class UnauthorizedEntryPointImpl implements AuthenticationEntryPoint, Serializable {

    /**
     * Unauthorized Entry Point: If the authentication is not successfully
     * Change: The response statusValue to SC_UNAUTHORIZED
     * Message: Error: Unauthorized.
     *
     * @param httpServletRequest      Http Servlet Request
     * @param httpServletResponse     Http Servlet Response
     * @param authenticationException Authentication Exception
     * @throws IOException IOException If something goes wrong writing the values in the response
     */
    @Override
    public void commence(
            final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse,
            final AuthenticationException authenticationException)
            throws IOException {
        log.info("Executing commence from UnauthorizedEntryPointImpl");

        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized.");

        httpServletResponse.getOutputStream().flush();
    }

}
