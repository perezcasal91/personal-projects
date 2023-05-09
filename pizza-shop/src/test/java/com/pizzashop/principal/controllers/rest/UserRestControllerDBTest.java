package com.pizzashop.principal.controllers.rest;

import com.pizzashop.principal.entities.UserEntity;
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
public class UserRestControllerDBTest {

    private final Long id = 4L;
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
    @DisplayName("Save User Role Admin Success Test")
    void save_User_Role_Admin_Success_Test() {
        log.info("Executing the Test 1: save_User_Role_Admin_Success_Test");

        initEntityFields(id, "User3", "t_user3",
                "t_user3@pizzashop.com", "534-565-5555");

        testsHelper.verifySuccessTest(API_URL,
                JsonUserHelper.RoleAdmin,
                HttpMethod.POST, UserEntity.class, fields);
    }

    @Test
    @Order(2)
    @DisplayName("Save User Role Admin User Success Test")
    void save_User_Role_Admin_User_Success_Test() {
        log.info("Executing the Test 2: save_User_Role_Admin_User_Success_Test");

        initEntityFields(id + 1, "User4", "t_user4",
                "t_user4@pizzashop.com", "534-565-5555");

        testsHelper.verifySuccessTest(API_URL,
                JsonUserHelper.RoleAdminUser,
                HttpMethod.POST, UserEntity.class, fields);
    }


    @Test
    @Order(3)
    @DisplayName("Update User Role Admin Success Test")
    void update_User_Role_Admin_Success_Test() {
        log.info("Executing the Test 3: update_User_Role_Admin_Success_Test");

        initEntityFields(id, "User3", "t_user3_change",
                "t_user3@pizzashop.com", "534-565-5555");

        testsHelper.verifySuccessTest(API_URL + "/" + id,
                JsonUserHelper.RoleAdminChange,
                HttpMethod.PUT, UserEntity.class, fields);
    }

    @Test
    @Order(4)
    @DisplayName("Update User Phone Role Admin Success Test")
    void update_User_Phone_Role_Admin_Success_Test() {
        log.info("Executing the Test 4: update_User_Phone_Role_Admin_Success_Test");

        initEntityFields(id, "User3", "t_user3_change",
                "t_user30@pizzashop.com", "534-565-1234");

        testsHelper.verifySuccessTest(API_URL + "/" + id,
                JsonUserHelper.RolePhoneChange,
                HttpMethod.PUT, UserEntity.class, fields);
    }

    @Test
    @Order(5)
    @DisplayName("Update User Not Found Test")
    void update_User_Not_Found_Test() {
        log.info("Executing the Test 5: update_User_Not_Found_Test");

        testsHelper.verifyExceptionTest(
                API_URL + "/30", JsonUserHelper.RoleAdminUser, HttpMethod.PUT,
                "404 NOT_FOUND \"Couldn't find " + UserEntity.class.getSimpleName()
                        + " with id: 30\"", HttpStatus.NOT_FOUND, fields);
    }

    @Test
    @Order(6)
    @DisplayName("Delete User By Id Success Test")
    void delete_User_By_Id_Success_Test() {
        log.info("Executing the Test 7: delete_User_By_Id_Success_Test");

        initEntityFields(id, "User3", "t_user3_change",
                "t_user30@pizzashop.com", "534-565-1234");

        testsHelper.verifySuccessTest(API_URL + "/" + id, null,
                HttpMethod.DELETE, UserEntity.class, fields);
    }

    @Test
    @Order(7)
    @DisplayName("Delete User By Id Not Found Test")
    void delete_User_By_Id_Not_Found_Test() {
        log.info("Executing the Test 7: delete_User_By_Id_Not_Found_Test");

        testsHelper.verifyExceptionTest(
                API_URL + "/30", null, HttpMethod.DELETE,
                "404 NOT_FOUND \"Couldn't find " + UserEntity.class.getSimpleName()
                        + " with id: 30\"", HttpStatus.NOT_FOUND, fields);
    }

    private void initEntityFields(Long id, String firstName, String username,
                                  String email, String phone) {
        fields.put("entity", "user");
        fields.put("id", id);
        fields.put("firstName", firstName);
        fields.put("username", username);
        fields.put("email", email);
        fields.put("phone", phone);
    }

    private void initListFields(int size) {
        fields.put("size", size);
    }

    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    public class GetUserTest {

        @Test
        @Order(8)
        @DisplayName("Get All Users Success Test")
        void get_All_Users_Success_Test() {
            log.info("Executing the Test 8: get_All_Users_Success_Test");

            initListFields(4);

            testsHelper.verifySuccessTest(API_URL, null,
                    HttpMethod.GET, UserEntity[].class, fields);
        }

        @Test
        @Order(9)
        @DisplayName("Get User By Id Success Test")
        void get_User_By_Id_Success_Test() {
            log.info("Executing the Test 9: get_User_By_Id_Success_Test");

            initEntityFields(2L, "User1", "t_user1",
                    "t_user1@pizzashop.com", "534-565-5555");

            testsHelper.verifySuccessTest(API_URL + "/2", null,
                    HttpMethod.GET, UserEntity.class, fields);
        }

        @Test
        @Order(10)
        @DisplayName("Get User By Id Not Found Test")
        void get_User_By_Id_Not_Found_Test() {
            log.info("Executing the Test 10: get_User_By_Id_Not_Found_Test");

            testsHelper.verifyExceptionTest(
                    API_URL + "/30", null, HttpMethod.GET,
                    "404 NOT_FOUND \"Couldn't find " + UserEntity.class.getSimpleName()
                            + " with id: 30\"", HttpStatus.NOT_FOUND, fields);
        }

        @Test
        @Order(11)
        @DisplayName("Get User By Username Success Test")
        void get_User_By_Username_Success_Test() {
            log.info("Executing the Test 11: get_User_By_Username_Success_Test");

            initEntityFields(2L, "User1", "t_user1",
                    "t_user1@pizzashop.com", "534-565-5555");

            testsHelper.verifySuccessTest(API_URL + "/by/username/t_user1", null,
                    HttpMethod.GET, UserEntity.class, fields);
        }

        @Test
        @Order(12)
        @DisplayName("Get User By Username Not Found Test")
        void get_User_By_Username_Not_Found_Test() {
            log.info("Executing the Test 12: get_User_By_Name_Not_Found_Test");

            testsHelper.verifyExceptionTest(
                    API_URL + "/by/username/test", null, HttpMethod.GET,
                    "404 NOT_FOUND \"Couldn't find " + UserEntity.class.getSimpleName()
                            + " with username: test\"", HttpStatus.NOT_FOUND, fields);
        }

        @Test
        @Order(13)
        @DisplayName("Get User By Email Success Test")
        void get_User_By_Email_Success_Test() {
            log.info("Executing the Test 13: get_User_By_Email_Success_Test");

            initEntityFields(2L, "User1", "t_user1",
                    "t_user1@pizzashop.com", "534-565-5555");

            testsHelper.verifySuccessTest(API_URL + "/by/email/t_user1@pizzashop.com",
                    null, HttpMethod.GET, UserEntity.class, fields);
        }

        @Test
        @Order(14)
        @DisplayName("Get User By Email Not Found Test")
        void get_User_By_Email_Not_Found_Test() {
            log.info("Executing the Test 14: get_User_By_Email_Not_Found_Test");

            testsHelper.verifyExceptionTest(
                    API_URL + "/by/email/test@pizzashop.com", null, HttpMethod.GET,
                    "404 NOT_FOUND \"Couldn't find " + UserEntity.class.getSimpleName()
                            + " with email: test@pizzashop.com\"", HttpStatus.NOT_FOUND, fields);
        }
    }
}