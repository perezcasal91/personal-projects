package com.pizzashop.principal.controllers.rest;

import com.pizzashop.principal.entities.RoleEntity;
import com.pizzashop.principal.helpers.JsonUserHelper;
import com.pizzashop.principal.helpers.TestsHelper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@Slf4j
public class UserFieldRestControllerDBTest {

    private final String API_URL = "/api/v1/users";
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;
    @LocalServerPort
    private int port;
    private TestsHelper testsHelper;
    private Map<String, Object> fields;

    @BeforeEach
    void setUp() {
        testsHelper = new TestsHelper(restTemplateBuilder, port);
        fields = new HashMap<>();
    }

    @Test
    @Order(1)
    @DisplayName("Save User Username Already Exist Test")
    void save_User_Username_Already_Exist_Test() {
        log.info("Executing the Test 1: save_User_Username_Already_Exist_Test");

        testsHelper.verifyExceptionTest(
                API_URL, JsonUserHelper.UsernameExist, HttpMethod.POST,
                "409 CONFLICT \"Busy username.\"", HttpStatus.CONFLICT, fields);
    }

    @Test
    @Order(2)
    @DisplayName("Save User Email Already Exist Test")
    void save_User_Email_Already_Exist_Test() {
        log.info("Executing the Test 2: save_User_Email_Already_Exist_Test");

        testsHelper.verifyExceptionTest(
                API_URL, JsonUserHelper.EmailExist, HttpMethod.POST,
                "409 CONFLICT \"Busy email.\"", HttpStatus.CONFLICT, fields);
    }

    @Test
    @Order(3)
    @DisplayName("Save User Invalid Email Test")
    void save_User_Invalid_Email_Test() {
        log.info("Executing the Test 3: save_User_Invalid_Email_Test");

        testsHelper.verifyExceptionTest(
                API_URL, JsonUserHelper.InvalidEmail, HttpMethod.POST,
                "Invalid request content. In field: email.", HttpStatus.BAD_REQUEST, fields);
    }

    @Test
    @Order(4)
    @DisplayName("Save User FirstName Is Null Test")
    void save_User_FirstName_Is_Null_Test() {
        log.info("Executing the Test 4: save_User_FirstName_Is_Null_Test");

        testsHelper.verifyExceptionTest(
                API_URL, JsonUserHelper.UserWithFirstNameNull, HttpMethod.POST,
                "Invalid request content. In field: firstName.", HttpStatus.BAD_REQUEST, fields);
    }

    @Test
    @Order(5)
    @DisplayName("Save User FirstName Is Blank Test")
    void save_User_FirstName_Is_Blank_Test() {
        log.info("Executing the Test 5: save_User_FirstName_Is_Blank_Test");

        testsHelper.verifyExceptionTest(
                API_URL, JsonUserHelper.UserWithFirstNameBlank, HttpMethod.POST,
                "Invalid request content. In field: firstName.", HttpStatus.BAD_REQUEST, fields);
    }

    @Test
    @Order(6)
    @DisplayName("Save User FirstName Is Empty Test")
    void save_User_FirstName_Is_Empty_Test() {
        log.info("Executing the Test 6: save_User_FirstName_Is_Empty_Test");

        testsHelper.verifyExceptionTest(
                API_URL, JsonUserHelper.UserWithFirstNameEmpty, HttpMethod.POST,
                "Invalid request content. In field: firstName.", HttpStatus.BAD_REQUEST, fields);
    }

    @Test
    @Order(7)
    @DisplayName("Save User LastName Is Null Test")
    void save_User_LastName_Is_Null_Test() {
        log.info("Executing the Test 7: save_User_LastName_Is_Null_Test");

        testsHelper.verifyExceptionTest(
                API_URL, JsonUserHelper.UserWithLastNameNull, HttpMethod.POST,
                "Invalid request content. In field: lastName.", HttpStatus.BAD_REQUEST, fields);
    }

    @Test
    @Order(8)
    @DisplayName("Save User LastName Is Blank Test")
    void save_User_LastName_Is_Blank_Test() {
        log.info("Executing the Test 8: save_User_LastName_Is_Blank_Test");

        testsHelper.verifyExceptionTest(
                API_URL, JsonUserHelper.UserWithLastNameBlank, HttpMethod.POST,
                "Invalid request content. In field: lastName.", HttpStatus.BAD_REQUEST, fields);
    }

    @Test
    @Order(9)
    @DisplayName("Save User LastName Is Empty Test")
    void save_User_LastName_Is_Empty_Test() {
        log.info("Executing the Test 9: save_User_LastName_Is_Empty_Test");

        testsHelper.verifyExceptionTest(
                API_URL, JsonUserHelper.UserWithLastNameEmpty, HttpMethod.POST,
                "Invalid request content. In field: lastName.", HttpStatus.BAD_REQUEST, fields);
    }

    @Test
    @Order(10)
    @DisplayName("Save User Username Is Null Test")
    void save_User_Username_Is_Null_Test() {
        log.info("Executing the Test 10: save_User_Username_Is_Null_Test");

        testsHelper.verifyExceptionTest(
                API_URL, JsonUserHelper.UserWithUsernameNull, HttpMethod.POST,
                "Invalid request content. In field: username.", HttpStatus.BAD_REQUEST, fields);
    }

    @Test
    @Order(11)
    @DisplayName("Save User Username Is Blank Test")
    void save_User_Username_Is_Blank_Test() {
        log.info("Executing the Test 11: save_User_Username_Is_Blank_Test");

        testsHelper.verifyExceptionTest(
                API_URL, JsonUserHelper.UserWithUsernameBlank, HttpMethod.POST,
                "Invalid request content. In field: username.", HttpStatus.BAD_REQUEST, fields);
    }

    @Test
    @Order(12)
    @DisplayName("Save User Username Is Empty Test")
    void save_User_Username_Is_Empty_Test() {
        log.info("Executing the Test 12: save_User_Username_Is_Empty_Test");

        testsHelper.verifyExceptionTest(
                API_URL, JsonUserHelper.UserWithUsernameEmpty, HttpMethod.POST,
                "Invalid request content. In field: username.", HttpStatus.BAD_REQUEST, fields);
    }

    @Test
    @Order(13)
    @DisplayName("Save User Username Malformed Test")
    void save_User_Username_Malformed_Test() {
        log.info("Executing the Test 13: save_User_Username_Malformed_Test");

        testsHelper.verifyExceptionTest(
                API_URL, JsonUserHelper.UserWithUsernameMalformed, HttpMethod.POST,
                "Invalid request content. In field: username.", HttpStatus.BAD_REQUEST, fields);
    }

    @Test
    @Order(14)
    @DisplayName("Save User Password Is Null Test")
    void save_User_Password_Is_Null_Test() {
        log.info("Executing the Test 14: save_User_Password_Is_Null_Test");

        testsHelper.verifyExceptionTest(
                API_URL, JsonUserHelper.UserWithPasswordNull, HttpMethod.POST,
                "Invalid request content. In field: password.", HttpStatus.BAD_REQUEST, fields);
    }

    @Test
    @Order(15)
    @DisplayName("Save User Password Is Blank Test")
    void save_User_Password_Is_Blank_Test() {
        log.info("Executing the Test 15: save_User_Password_Is_Blank_Test");

        testsHelper.verifyExceptionTest(
                API_URL, JsonUserHelper.UserWithPasswordBlank, HttpMethod.POST,
                "Invalid request content. In field: password.", HttpStatus.BAD_REQUEST, fields);
    }

    @Test
    @Order(16)
    @DisplayName("Save User Password Is Empty Test")
    void save_User_Password_Is_Empty_Test() {
        log.info("Executing the Test 16: save_User_Password_Is_Empty_Test");

        testsHelper.verifyExceptionTest(
                API_URL, JsonUserHelper.UserWithPasswordEmpty, HttpMethod.POST,
                "Invalid request content. In field: password.", HttpStatus.BAD_REQUEST, fields);
    }

    @Test
    @Order(17)
    @DisplayName("Save User Password Is Less Than 8 Characters Test")
    void save_User_Password_Is_Less_Than_8_Characters_Test() {
        log.info("Executing the Test 17: save_User_Password_Is_Less_Than_8_Characters_Test");

        testsHelper.verifyExceptionTest(
                API_URL, JsonUserHelper.UserWithPasswordLessThan, HttpMethod.POST,
                "Invalid request content. In field: password.", HttpStatus.BAD_REQUEST, fields);
    }

    @Test
    @Order(18)
    @DisplayName("Save User Invalid Phone Test")
    void save_User_Invalid_Phone_Test() {
        log.info("Executing the Test 18: save_User_Invalid_Phone_Test");

        testsHelper.verifyExceptionTest(
                API_URL, JsonUserHelper.InvalidPhone, HttpMethod.POST,
                "Invalid request content. In field: phone.", HttpStatus.BAD_REQUEST, fields);
    }

    @Test
    @Order(19)
    @DisplayName("Save User Roles Is Null Test")
    void save_User_Roles_Is_Null_Test() {
        log.info("Executing the Test 19: save_User_Roles_Is_Null_Test");

        testsHelper.verifyExceptionTest(
                API_URL, JsonUserHelper.UserWithRolesNull, HttpMethod.POST,
                "Invalid request content. In field: roles.", HttpStatus.BAD_REQUEST, fields);
    }

    @Test
    @Order(20)
    @DisplayName("Save User Roles Is Blank Test")
    void save_User_Roles_Is_Blank_Test() {
        log.info("Executing the Test 20: save_User_Roles_Is_Blank_Test");

        testsHelper.verifyExceptionTest(
                API_URL, JsonUserHelper.UserWithRolesBlank, HttpMethod.POST,
                "Invalid request content. In field: roles.", HttpStatus.BAD_REQUEST, fields);
    }

    @Test
    @Order(21)
    @DisplayName("Save User Roles Is Empty Test")
    void save_User_Roles_Is_Empty_Test() {
        log.info("Executing the Test 21: save_User_Roles_Is_Empty_Test");

        testsHelper.verifyExceptionTest(
                API_URL, JsonUserHelper.UserWithRolesEmpty, HttpMethod.POST,
                "Invalid request content. In field: roles.", HttpStatus.BAD_REQUEST, fields);
    }

    @Test
    @Order(22)
    @DisplayName("Save User Roles Not Found Test")
    void save_User_Roles_Not_Found_Test() {
        log.info("Executing the Test 22: save_User_Roles_Not_Found_Test");

        testsHelper.verifyExceptionTest(
                API_URL, JsonUserHelper.UserWithRolesNotFound, HttpMethod.POST,
                "404 NOT_FOUND \"Couldn't find " + RoleEntity.class.getSimpleName()
                        + " with name: TEST\"", HttpStatus.NOT_FOUND, fields);
    }

    @Test
    @Order(23)
    @DisplayName("Update User Username Already Exist Test")
    void update_User_Username_Already_Exist_Test() {
        log.info("Executing the Test 23: update_User_Username_Already_Exist_Test");

        testsHelper.verifyExceptionTest(
                API_URL + "/1", JsonUserHelper.UsernameExist,
                HttpMethod.PUT, "409 CONFLICT \"Busy username.\"", HttpStatus.CONFLICT, fields);
    }

    @Test
    @Order(22)
    @DisplayName("Update User Email Already Exist Test")
    void update_User_Email_Already_Exist_Test() {
        log.info("Executing the Test 24: update_User_Email_Already_Exist_Test");

        testsHelper.verifyExceptionTest(
                API_URL + "/1", JsonUserHelper.EmailExist,
                HttpMethod.PUT, "409 CONFLICT \"Busy email.\"", HttpStatus.CONFLICT, fields);
    }

}