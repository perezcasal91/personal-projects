package com.pizzashop.principal.services.impls;

import com.pizzashop.principal.dtos.AuthRequestDTO;
import com.pizzashop.principal.dtos.AuthResponseDTO;
import com.pizzashop.principal.helpers.HttpServletRequestHelper;
import com.pizzashop.principal.security.TokenProvider;
import com.pizzashop.principal.services.AuthService;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@Slf4j
public class AuthServiceImplDBTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private TokenProvider tokenProvider;

    private AuthRequestDTO roleAdmin;
    private AuthRequestDTO roleUser;
    private AuthRequestDTO userNotExist;

    @BeforeEach
    void setUp() {
        roleAdmin = AuthRequestDTO.builder()
                .username("t_admin")
                .password("Admin1234")
                .build();

        roleUser = AuthRequestDTO.builder()
                .username("t_user1")
                .password("User1234")
                .build();

        userNotExist = AuthRequestDTO.builder()
                .username("user")
                .password("Password")
                .build();
    }

    @Test
    @Order(1)
    @DisplayName("Login Admin Success Test")
    void login_Admin_Success_Test() {
        log.info("Staring the Test 1: loginAdminSuccessTest");

        AuthResponseDTO authResponseDTO = authService.login(roleAdmin);

        testResponseStatusOK(authResponseDTO, "t_admin");
    }

    @Test
    @Order(2)
    @DisplayName("Login User Success Test")
    @Sql({"/user_data.sql"})
    void loginUserSuccessTest() {
        log.info("Staring the Test 2: loginUserSuccessTest");

        AuthResponseDTO authResponseDTO = authService.login(roleUser);

        testResponseStatusOK(authResponseDTO, "t_user1");
    }

    @Test
    @Order(3)
    @DisplayName("Login User Not Found Test")
    void loginUserNotFoundTest() {
        log.info("Staring the Test 3: loginUserNotFoundTest");

        Exception badRequest = assertThrows(BadCredentialsException.class,
                () -> authService.login(userNotExist));

        assertEquals("Bad credentials", badRequest.getMessage());
    }

    @Test
    @Order(4)
    @DisplayName("Login Request Is Missing Test")
    void loginRequestIsMissingTest() {
        log.info("Staring the Test 4: loginRequestIsMissingTest");

        Exception nullRequest = assertThrows(NullPointerException.class,
                () -> authService.login(null));

        assertEquals("Cannot invoke " +
                "\"com.pizzashop.principal.dtos.AuthRequestDTO.getUsername()\"" +
                " because \"authRequestDTO\" is null", nullRequest.getMessage());
    }

    @Test
    @Order(5)
    @DisplayName("Refresh Admin Success Test")
    void refreshAdminSuccessTest() {
        log.info("Staring the Test 5: refreshAdminSuccessTest");

        AuthResponseDTO authResponseDTO = authService.login(roleAdmin);

        HttpServletRequestHelper helper = new HttpServletRequestHelper(request);

        helper.putHeader("Authorization", "Bearer " + authResponseDTO.getRefreshToken());

        AuthResponseDTO refreshResponseDTO = authService.refresh(helper, response);

        testResponseStatusOK(refreshResponseDTO, "t_admin");

    }

    @Test
    @Order(6)
    @DisplayName("Refresh User Success Test")
    void refreshUserSuccessTest() {
        log.info("Staring the Test 6: refreshUserSuccessTest");

        AuthResponseDTO authResponseDTO = authService.login(roleUser);

        HttpServletRequestHelper helper = new HttpServletRequestHelper(request);

        helper.putHeader("Authorization", "Bearer " + authResponseDTO.getRefreshToken());

        AuthResponseDTO refreshResponseDTO = authService.refresh(helper, response);

        testResponseStatusOK(refreshResponseDTO, "t_user1");

    }

    @Test
    @Order(7)
    @DisplayName("Refresh Authentication Header Is Null Test")
    void refreshAuthenticationHeaderIsNullTest() {
        log.info("Staring the Test 7: refreshAuthenticationHeaderIsNullTest");

        Exception nullHeader = assertThrows(IllegalArgumentException.class,
                () -> authService.refresh(request, response));

        assertEquals("Couldn't find bearer string.", nullHeader.getMessage());
    }

    @Test
    @Order(8)
    @DisplayName("Refresh Bearer Token Is null Test")
    void refreshBearerTokenIsWrongTest() {
        log.info("Staring the Test 8: refreshBearerTokenIsWrongTest");

        HttpServletRequestHelper helper = new HttpServletRequestHelper(request);

        helper.putHeader("Authorization", "Bearer ");

        Exception nullBearerToken = assertThrows(IllegalArgumentException.class,
                () -> authService.refresh(helper, response));

        assertEquals("JWT String argument cannot be null or empty.",
                nullBearerToken.getMessage());
    }

    @Test
    @Order(9)
    @DisplayName("Refresh Bearer Token Is Malformed Missing Dot Test")
    void refreshBearerTokenIsMalformedMissingDotTest() {
        log.info("Staring the Test 9: refreshBearerTokenIsMalformedMissingDotTest");

        AuthResponseDTO authResponseDTO = authService.login(roleAdmin);

        HttpServletRequestHelper helper = new HttpServletRequestHelper(request);

        String[] tokenParts = authResponseDTO.getRefreshToken().split("\\.");

        String refreshToken = tokenParts[0] + "" + tokenParts[1] + "." + tokenParts[2];

        helper.putHeader("Authorization", "Bearer " + refreshToken);

        Exception nullBearerToken = assertThrows(MalformedJwtException.class,
                () -> authService.refresh(helper, response));

        assertEquals("JWT strings must contain exactly 2 period characters. Found: 1",
                nullBearerToken.getMessage());
    }

    @Test
    @Order(9)
    @DisplayName("Refresh Bearer Token Is Malformed Test")
    void refreshBearerTokenIsMalformedTest() {
        log.info("Staring the Test 9: refreshBearerTokenIsMalformedTest");

        AuthResponseDTO authResponseDTO = authService.login(roleAdmin);

        HttpServletRequestHelper helper = new HttpServletRequestHelper(request);

        String[] tokenParts = authResponseDTO.getRefreshToken().split("\\.");

        String refreshToken = tokenParts[0] + "." + tokenParts[1] + "." + tokenParts[2] + "zxc";

        helper.putHeader("Authorization", "Bearer " + refreshToken);

        Exception nullBearerToken = assertThrows(SignatureException.class,
                () -> authService.refresh(helper, response));

        assertEquals("JWT signature does not match locally computed signature." +
                        " JWT validity cannot be asserted and should not be trusted.",
                nullBearerToken.getMessage());
    }

    @Test
    @Order(10)
    @DisplayName("Logout Test")
    void logoutTest() {
        log.info("Staring the Test 10: logoutTest");

        authService.logout();

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    private void testResponseStatusOK(AuthResponseDTO responseDTO, String user) {
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals(user, SecurityContextHolder.getContext().getAuthentication().getName());
        assertNotNull(responseDTO);
        assertTrue(responseDTO.getAccessToken().length() > 0);
        assertTrue(responseDTO.getRefreshToken().length() > 0);
        assertEquals(user, tokenProvider.getUsernameFromToken(responseDTO.getAccessToken()));
        assertEquals(user, tokenProvider.getUsernameFromToken(responseDTO.getRefreshToken()));
    }

}