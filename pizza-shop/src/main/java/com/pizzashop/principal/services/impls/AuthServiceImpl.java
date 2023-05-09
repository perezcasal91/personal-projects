package com.pizzashop.principal.services.impls;

import com.pizzashop.principal.dtos.AuthRequestDTO;
import com.pizzashop.principal.dtos.AuthResponseDTO;
import com.pizzashop.principal.security.TokenProvider;
import com.pizzashop.principal.services.AuthService;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Value("${jwt.header.string}")
    public String HEADER_STRING;

    @Value("${jwt.token.prefix}")
    public String TOKEN_PREFIX;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    /**
     * Login into the Security Context.
     *
     * @param authRequestDTO It will have the username and password to login.
     * @return AuthResponseDTO It will have the access and refresh token as response.
     */
    @Override
    public AuthResponseDTO login(@NotNull final AuthRequestDTO authRequestDTO) {
        log.info("Executing login from AuthServiceImpl");

        final Authentication authentication;

        authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequestDTO.getUsername(),
                        authRequestDTO.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String accessToken = tokenProvider.generateAccessToken(authentication);
        final String refreshToken = tokenProvider.generateRefreshToken(authentication);

        return AuthResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    /**
     * Refresh the access token using the refresh token,
     * and then they will be contained into the response.
     *
     * @param request  HttpServletRequest.
     * @param response HttpServletResponse.
     * @throws RuntimeException If the OutputStream can't be done.
     */
    @Override
    public AuthResponseDTO refresh(final HttpServletRequest request,
                                   final HttpServletResponse response) {
        log.info("Executing refresh from AuthServiceImpl");

        final String header = request.getHeader(HEADER_STRING);
        final String username;
        final String refreshToken;

        if (header != null && header.startsWith(TOKEN_PREFIX)) {
            refreshToken = header.replace(TOKEN_PREFIX, "");

            username = tokenProvider.getUsernameFromToken(refreshToken);

            if (username != null) {
                UserDetails userDetails = this.userDetailsService
                        .loadUserByUsername(username);

                if (tokenProvider.validateToken(refreshToken, userDetails)) {
                    final String accessToken = tokenProvider.generateAccessToken(
                            SecurityContextHolder
                                    .getContext()
                                    .getAuthentication()
                    );

                    return AuthResponseDTO
                            .builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshToken)
                            .build();

                } else {
                    throw new MalformedJwtException("This is not a valid token.");
                }
            } else {
                throw new UsernameNotFoundException("An error occurred while fetching Username from Token.");
            }
        } else {
            throw new IllegalArgumentException("Couldn't find bearer string.");
        }
    }

    /**
     * Logout from the Security Context.
     */
    @Override
    public void logout() {
        log.info("Executing logOut from AuthServiceImpl");

        SecurityContextHolder.getContext().setAuthentication(null);
        SecurityContextHolder.clearContext();
    }

}
