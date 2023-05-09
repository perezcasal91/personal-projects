package com.pizzashop.principal.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

@Component
@Slf4j
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    /**
     * Access Denied Handler
     * Change: The response statusValue a FORBIDDEN
     * Message: Access Denied, login again!
     *
     * @param httpServletRequest    Http Servlet Request
     * @param httpServletResponse   Http Servlet Response
     * @param accessDeniedException Access Denied Exception
     * @throws IOException If something goes wrong writing the values in the response.
     */
    public void handle(
            final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse,
            final AccessDeniedException accessDeniedException)
            throws IOException {
        log.info("Executing handle from AccessDeniedHandlerImpl");

        httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
        httpServletResponse.setContentType("application/json");

        HashMap<String, Object> data = new HashMap<>();
        data.put("timestamp", new Date());
        data.put("statusValue", HttpStatus.FORBIDDEN.value());
        data.put("message", "Access Denied, login again!");
        data.put("path", httpServletRequest.getRequestURL().toString());

        ServletOutputStream out = httpServletResponse.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();

        mapper.writeValue(out, data);
        out.flush();
    }
}
