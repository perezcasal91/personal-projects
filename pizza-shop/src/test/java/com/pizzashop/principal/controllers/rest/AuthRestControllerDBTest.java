package com.pizzashop.principal.controllers.rest;

import com.pizzashop.principal.dtos.AuthResponseDTO;
import com.pizzashop.principal.dtos.MessageResponseDTO;
import com.pizzashop.principal.helpers.JsonAuthHelper;
import com.pizzashop.principal.helpers.TestsHelper;
import com.pizzashop.principal.security.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@Slf4j
public class AuthRestControllerDBTest {

    private final String API_URL = "/api/v1/auth/";

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @LocalServerPort
    private int port;

    private TestsHelper testsHelper;

    private Map<String, Object> fields;

    @Autowired
    private TokenProvider tokenProvider;

    @BeforeEach
    void setUp() {
        testsHelper = new TestsHelper(restTemplateBuilder, port, tokenProvider);
        fields = new HashMap<>();
    }

    @Test
    @Order(1)
    @DisplayName("Login Admin Success Test")
    void login_Admin_Success_Test() {
        log.info("Executing the Test 1: login_Admin_Success_Test");

        initLoginFields("t_admin");

        testsHelper.verifySuccessTest(API_URL + "login",
                JsonAuthHelper.RoleAdmin,
                HttpMethod.POST, AuthResponseDTO.class, fields);
    }

    @Test
    @Order(2)
    @DisplayName("Login User Success Test")
    @Sql({"/user_data.sql"})
    void login_User_Success_Test() {
        log.info("Executing the Test 2: login_User_Success_Test");

        initLoginFields("t_user1");

        testsHelper.verifySuccessTest(API_URL + "login",
                JsonAuthHelper.RoleUser,
                HttpMethod.POST, AuthResponseDTO.class, fields);
    }

    @Test
    @Order(3)
    @DisplayName("Login Request Username Is Null Test")
    void login_Request_Username_Is_Null_Test() {
        log.info("Executing the Test 3: login_Request_Username_Is_Null_Test");

        testsHelper.verifyExceptionTest(
                API_URL + "login", JsonAuthHelper.UserWithUsernameNull, HttpMethod.POST,
                "Invalid request content. In field: username.",
                HttpStatus.BAD_REQUEST, fields);
    }

    @Test
    @Order(4)
    @DisplayName("Login Request Username Is Blank Test")
    void login_Request_Username_Is_Blank_Test() {
        log.info("Executing the Test 4: login_Request_Username_Is_Blank_Test");

        testsHelper.verifyExceptionTest(
                API_URL + "login", JsonAuthHelper.UserWithUsernameBlank, HttpMethod.POST,
                "Invalid request content. In field: username.",
                HttpStatus.BAD_REQUEST, fields);
    }

    @Test
    @Order(5)
    @DisplayName("Login Request Username Is Empty Test")
    void login_Request_Username_Is_Empty_Test() {
        log.info("Executing the Test 5: login_Request_Username_Is_Empty_Test");

        testsHelper.verifyExceptionTest(
                API_URL + "login", JsonAuthHelper.UserWithUsernameEmpty, HttpMethod.POST,
                "Invalid request content. In field: username.",
                HttpStatus.BAD_REQUEST, fields);
    }

    @Test
    @Order(6)
    @DisplayName("Login Request Password Is Null Test")
    void login_Request_Password_Is_Null_Test() {
        log.info("Executing the Test 6: login_Request_Password_Is_Null_Test");

        testsHelper.verifyExceptionTest(
                API_URL + "login", JsonAuthHelper.UserWithPasswordNull, HttpMethod.POST,
                "Invalid request content. In field: password.",
                HttpStatus.BAD_REQUEST, fields);
    }

    @Test
    @Order(7)
    @DisplayName("Login Request Password Is Blank Test")
    void login_Request_Password_Is_Blank_Test() {
        log.info("Executing the Test 7: login_Request_Password_Is_Blank_Test");

        testsHelper.verifyExceptionTest(
                API_URL + "login", JsonAuthHelper.UserWithPasswordBlank, HttpMethod.POST,
                "Invalid request content. In field: password.",
                HttpStatus.BAD_REQUEST, fields);
    }

    @Test
    @Order(8)
    @DisplayName("Login Request Password Is Empty Test")
    void login_Request_Password_Is_Empty_Test() {
        log.info("Executing the Test 8: login_Request_Password_Is_Empty_Test");

        testsHelper.verifyExceptionTest(
                API_URL + "login", JsonAuthHelper.UserWithPasswordEmpty, HttpMethod.POST,
                "Invalid request content. In field: password.",
                HttpStatus.BAD_REQUEST, fields);
    }

    @Test
    @Order(9)
    @DisplayName("Login Request Is Empty Test")
    void login_Request_Is_Empty_Test() {
        log.info("Executing the Test 9: login_Request_Is_Empty_Test");

        testsHelper.verifyExceptionTest(
                API_URL + "login", null, HttpMethod.POST,
                "Required request body is missing: public " +
                        "org.springframework.http.ResponseEntity<?> " +
                        "com.pizzashop.principal.controllers.rest" +
                        ".AuthRestController.login(com.pizzashop.principal" +
                        ".dtos.AuthRequestDTO)", HttpStatus.BAD_REQUEST, fields);
    }

    @Test
    @Order(10)
    @DisplayName("Refresh Admin Success Test")
    void refresh_Admin_Success_Test() {
        log.info("Executing the Test 10: refresh_Admin_Success_Test");

        initRefreshFields(null, "t_admin");

        testsHelper.verifySuccessTest(API_URL + "refresh",
                JsonAuthHelper.RoleAdmin,
                HttpMethod.POST, AuthResponseDTO.class, fields);
    }

    @Test
    @Order(11)
    @DisplayName("Refresh User Success Test")
    void refresh_User_Success_Test() {
        log.info("Executing the Test 11: refresh_User_Success_Test");

        initRefreshFields(null, "t_user1");

        testsHelper.verifySuccessTest(API_URL + "refresh",
                JsonAuthHelper.RoleUser,
                HttpMethod.POST, AuthResponseDTO.class, fields);
    }

    @Test
    @Order(12)
    @DisplayName("Refresh Header Is Null Test")
    void refresh_Header_Is_Null_Test() {
        log.info("Executing the Test 12: refresh_Header_Is_Null_Test");

        initRefreshFields("header_null", null);

        testsHelper.verifyExceptionTest(
                API_URL + "refresh", JsonAuthHelper.RoleAdmin, HttpMethod.POST,
                "Couldn't find bearer string.", HttpStatus.BAD_REQUEST, fields);
    }


    @Test
    @Order(13)
    @DisplayName("Refresh Bearer Token Is Null Test")
    void refresh_Bearer_Token_Is_Null_Test() {
        log.info("Executing the Test 13: refresh_Bearer_Token_Is_Null_Test");

        initRefreshFields("bearer_null", null);

        testsHelper.verifyExceptionTest(
                API_URL + "refresh", JsonAuthHelper.RoleAdmin, HttpMethod.POST,
                "This is not a valid token.", HttpStatus.BAD_REQUEST, fields);
    }

    @Test
    @Order(14)
    @DisplayName("Refresh Bearer Token Is Malformed Missing Dot Test")
    void refresh_Bearer_Token_Is_Malformed_Missing_Dot_Test() {
        log.info("Staring the Test 14: refresh_Bearer_Token_Is_Malformed_Missing_Dot_Test");

        initRefreshFields("malformed_token_missing", null);

        testsHelper.verifyExceptionTest(
                API_URL + "refresh", JsonAuthHelper.RoleAdmin, HttpMethod.POST,
                "This is not a valid token.", HttpStatus.BAD_REQUEST, fields);
    }

    @Test
    @Order(15)
    @DisplayName("Refresh Bearer Token Is Malformed Test")
    void refresh_Bearer_Token_Is_Malformed_Test() {
        log.info("Staring the Test 15: refreshBearerTokenIsMalformedTest");

        initRefreshFields("malformed_token", null);

        testsHelper.verifyExceptionTest(
                API_URL + "refresh", JsonAuthHelper.RoleAdmin, HttpMethod.POST,
                "Authentication Failed. Token not valid.", HttpStatus.BAD_REQUEST, fields);
    }

//    @Test
//    @Order(16)
//    @DisplayName("Refresh Bearer Token Is Malformed Test")
//    void refresh_Bearer_Token_Is_Malformed_Test() {
//        log.info("Staring the Test 15: refreshBearerTokenIsMalformedTest");
//
//        initRefreshFields("malformed_token", null);
//
//        testsHelper.verifyExceptionTest(
//                API_URL + "refresh", JsonAuthHelper.RoleAdmin, HttpMethod.POST,
//                "Authentication Failed. Token not valid.", HttpStatus.BAD_REQUEST, fields);
//    }

    @Test
    @Order(16)
    @DisplayName("Logout Admin Success Test")
    void logout_Admin_Success_Test() {
        log.info("Executing the Test 16: logout_Admin_Success_Test");

        initLogoutFields();

        testsHelper.verifySuccessTest(API_URL + "logout",
                JsonAuthHelper.RoleAdmin,
                HttpMethod.POST, MessageResponseDTO.class, fields);

    }

    @Test
    @Order(17)
    @DisplayName("Logout User Success Test")
    void logout_User_Success_Test() {
        log.info("Executing the Test 17: logout_User_Success_Test");

        initLogoutFields();

        testsHelper.verifySuccessTest(API_URL + "logout",
                JsonAuthHelper.RoleUser,
                HttpMethod.POST, MessageResponseDTO.class, fields);
    }

    @Test
    @Order(18)
    @DisplayName("Token Is Expired Test")
    void token_Is_Expired_Test() {
        log.info("Executing the Test 16: token_Is_Expired_Test");

        initExpiredFields();

        testsHelper.verifySuccessTest("/api/v1/users", JsonAuthHelper.RoleAdmin,
                HttpMethod.GET, Map.class, fields);
    }

    /**
     * Init the Map fields for the Login Test.
     *
     * @param user String value.
     */
    private void initLoginFields(String user) {
        initCommonFields( "login");
        fields.put("user", user);
    }

    /**
     * Init the Map fields for the Refresh Test.
     *
     * @param test String type of the test.
     * @param user String value.
     */
    private void initRefreshFields(String test, String user) {
        initCommonFields( "refresh");
        fields.put("test", test);
        fields.put("user", user);
    }

    /**
     * Init the Map fields for the Logout Test.
     */
    private void initLogoutFields() {
        initCommonFields("logout");
        fields.put("message", "Logout successfully.");
    }

    /**
     * Init the Map fields for the Expired Token Test.
     */
    private void initExpiredFields() {
        initCommonFields("expired");
    }

    /**
     * Init the Map fields with common values. .
     *
     * @param method String value.
     */
    private void initCommonFields(String method) {
        fields.put("entity", "auth");
        fields.put("method", method);
    }

}