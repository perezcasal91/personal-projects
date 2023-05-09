package com.pizzashop.principal.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pizzashop.principal.services.impls.UserDetailsServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt.header.string}")
    public String HEADER_STRING;

    @Value("${jwt.token.prefix}")
    public String TOKEN_PREFIX;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private TokenProvider tokenProvider;

    /**
     * @param httpServletRequest  Http Servlet Request
     * @param httpServletResponse Http Servlet Response
     * @param filterChain         Filter Chain
     * @throws IOException      If something goes wrong its throw this Exception type
     * @throws ServletException If something goes wrong its throw this Exception type
     */
    @Override
    protected void doFilterInternal(
            final HttpServletRequest httpServletRequest,
            @NonNull final HttpServletResponse httpServletResponse,
            @NonNull final FilterChain filterChain)
            throws IOException, ServletException {
        log.info("Executing doFilterInternal from JwtAuthenticationFilter");

        final String header = httpServletRequest.getHeader(HEADER_STRING);
        String username = null;
        String authToken = null;
        boolean expired = false;

        if (header != null && header.startsWith(TOKEN_PREFIX)) {
            authToken = header.replace(TOKEN_PREFIX, "");

            try {
                username = tokenProvider.getUsernameFromToken(authToken);
            } catch (MalformedJwtException exception) {
                log.error("This is not a valid token.");
            } catch (IllegalArgumentException exception) {
                log.error("An error occurred while fetching Username from Token");
            } catch (ExpiredJwtException exception) {
                expired = true;
                log.error("The token has expired.");
            } catch (SignatureException exception) {
                log.error("Authentication Failed. Username or Password not valid.");
            }
        } else {
            log.warn("Couldn't find bearer string, header will be ignored");
        }
        if (!expired) {
            if (username != null &&
                    SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (tokenProvider.validateToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication =
                            tokenProvider.getAuthenticationToken(authToken, userDetails);

                    authentication.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(httpServletRequest)
                    );

                    log.info("Authenticated user " + username + ", setting security context");

                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(authentication);
                }
            }

            filterChain.doFilter(httpServletRequest, httpServletResponse);

        } else {
            tokenHasExpired(httpServletResponse);
        }

    }

    /**
     * Write in the response that the token has expired.
     *
     * @param response HttpServletResponse.
     * @throws IOException If something goes wrong writing the values in the response.
     */
    private void tokenHasExpired(HttpServletResponse response) throws IOException {
        log.info("Executing tokenHasExpired from JwtAuthenticationFilter");

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");

        HashMap<String, Object> data = new HashMap<>();
        data.put("token", "expired");

        ServletOutputStream out = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();

        mapper.writeValue(out, data);
        out.flush();
    }

}
