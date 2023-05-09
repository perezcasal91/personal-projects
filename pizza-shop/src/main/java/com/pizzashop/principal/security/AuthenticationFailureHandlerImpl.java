package com.pizzashop.principal.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

@Component
@Slf4j
public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {

    /**
     * Access Denied Handler
     * Change: The response statusValue a FORBIDDEN
     * Message: Access Denied, Bad Credentials!! Try again!
     *
     * @param httpServletRequest      Http Servlet Request
     * @param httpServletResponse     Http Servlet Response
     * @param authenticationException Authentication Exception
     * @throws IOException IOException If something goes wrong writing the values in the response
     */
    @Override
    public void onAuthenticationFailure(
            final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse,
            final AuthenticationException authenticationException)
            throws IOException {
        log.info("Executing onAuthenticationFailure from AuthenticationFailureHandlerImpl");

        httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());

        HashMap<String, Object> data = new HashMap<>();
        data.put("timestamp", new Date());
        data.put("statusValue", HttpStatus.FORBIDDEN.value());
        data.put("message", "Access Denied, Bad Credentials!! Try again!");
        data.put("path", httpServletRequest.getRequestURL().toString());

        ServletOutputStream out = httpServletResponse.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, data);
        out.flush();
    }
}
