package com.pizzashop.principal.controllers.rest;

import com.pizzashop.principal.entities.RoleEntity;
import com.pizzashop.principal.helpers.JsonRoleHelper;
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
import org.springframework.test.context.jdbc.Sql;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@Slf4j
public class RoleRestControllerDBTest {

    private final Long id = 4L;
    private final String API_URL = "/api/v1/roles";
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
    @DisplayName("Save Role Success Test")
    @Sql({"/role_data.sql"})
    void save_Role_Success_Test() {
        log.info("Executing the Test 1: save_Role_Success_Test");

        initEntityFields(id, "R1", "R1");

        testsHelper.verifySuccessTest(API_URL,
                JsonRoleHelper.Role,
                HttpMethod.POST, RoleEntity.class, fields);
    }

    @Test
    @Order(2)
    @DisplayName("Save Role Already Exist Test")
    void save_Role_Already_Exist_Test() {
        log.info("Executing the Test 2: save_Role_Already_Exist_Test");

        testsHelper.verifyExceptionTest(
                API_URL, JsonRoleHelper.Role, HttpMethod.POST,
                "409 CONFLICT \"Busy name.\"", HttpStatus.CONFLICT, fields);
    }

    @Test
    @Order(3)
    @DisplayName("Save Role Name Is Null Test")
    void save_Role_Name_Is_Null_Test() {
        log.info("Executing the Test 3: save_Role_Name_Is_Null_Test");

        testsHelper.verifyExceptionTest(
                API_URL, JsonRoleHelper.RoleWithNameNull, HttpMethod.POST,
                "Invalid request content. In field: name.", HttpStatus.BAD_REQUEST, fields);
    }

    @Test
    @Order(4)
    @DisplayName("Save Role Name Is Blank Test")
    void save_Role_Name_Is_Blank_Test() {
        log.info("Executing the Test 4: save_Role_Name_Is_Blank_Test");

        testsHelper.verifyExceptionTest(
                API_URL, JsonRoleHelper.RoleWithNameBlank, HttpMethod.POST,
                "Invalid request content. In field: name.", HttpStatus.BAD_REQUEST, fields);
    }

    @Test
    @Order(5)
    @DisplayName("Save Role Name Is Empty Test")
    void save_Role_Name_Is_Empty_Test() {
        log.info("Executing the Test 5: save_Role_Name_Is_Empty_Test");

        testsHelper.verifyExceptionTest(
                API_URL, JsonRoleHelper.RoleWithNameEmpty, HttpMethod.POST,
                "Invalid request content. In field: name.", HttpStatus.BAD_REQUEST, fields);
    }

    @Test
    @Order(6)
    @DisplayName("Update Role Success Test")
    void update_Role_Success_Test() {
        log.info("Executing the Test 6: update_Role_Success_Test");

        initEntityFields(id, "R2", "R2");

        testsHelper.verifySuccessTest(API_URL + "/" + id,
                JsonRoleHelper.RoleToUpdate,
                HttpMethod.PUT, RoleEntity.class, fields);
    }

    @Test
    @Order(7)
    @DisplayName("Update Role Description Success Test")
    void update_Role_Description_Success_Test() {
        log.info("Executing the Test 7: update_Role_Description_Success_Test");

        initEntityFields(id, "R2", "R2 Change");

        testsHelper.verifySuccessTest(API_URL + "/" + id,
                JsonRoleHelper.RoleDescriptionChange,
                HttpMethod.PUT, RoleEntity.class, fields);
    }

    @Test
    @Order(8)
    @DisplayName("Update Role Name Already Exist Test")
    void update_Role_Name_Already_Exist_Test() {
        log.info("Executing the Test 8: update_Role_Already_Exist_Test");

        testsHelper.verifyExceptionTest(
                API_URL + "/" + id, JsonRoleHelper.RoleAlreadyExist,
                HttpMethod.PUT, "409 CONFLICT \"Busy name.\"", HttpStatus.CONFLICT, fields);
    }

    @Test
    @Order(9)
    @DisplayName("Update Role Not Found Test")
    void update_Role_Not_Found_Test() {
        log.info("Executing the Test 9: update_Role_Not_Found_Test");

        testsHelper.verifyExceptionTest(
                API_URL + "/30", JsonRoleHelper.RoleToUpdate, HttpMethod.PUT,
                "404 NOT_FOUND \"Couldn't find " + RoleEntity.class.getSimpleName()
                        + " with id: 30\"", HttpStatus.NOT_FOUND, fields);
    }

    @Test
    @Order(10)
    @DisplayName("Delete Role By Id Success Test")
    void delete_Role_By_Id_Success_Test() {
        log.info("Executing the Test 10: delete_Role_By_Id_Success_Test");

        initEntityFields(id, "R2", "R2 Change");

        testsHelper.verifySuccessTest(API_URL + "/" + id, null,
                HttpMethod.DELETE, RoleEntity.class, fields);
    }

    @Test
    @Order(11)
    @DisplayName("Delete Role By Id Not Found Test")
    void delete_Role_By_Id_Not_Found_Test() {
        log.info("Executing the Test 11: delete_Role_By_Id_Not_Found_Test");

        testsHelper.verifyExceptionTest(
                API_URL + "/30", null, HttpMethod.DELETE,
                "404 NOT_FOUND \"Couldn't find " + RoleEntity.class.getSimpleName()
                        + " with id: 30\"", HttpStatus.NOT_FOUND, fields);
    }

    private void initEntityFields(Long id, String name, String description) {
        fields.put("entity", "role");
        fields.put("id", id);
        fields.put("name", name);
        fields.put("description", description);
    }

    private void initListFields(int size) {
        fields.put("size", size);
    }

    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    public class GetRoleTest {

        @Test
        @Order(12)
        @DisplayName("Get All Roles Success Test")
        void get_All_Roles_Success_Test() {
            log.info("Executing the Test 12: get_All_Roles_Success_Test");

            initListFields(3);

            testsHelper.verifySuccessTest(API_URL, null,
                    HttpMethod.GET, RoleEntity[].class, fields);
        }

        @Test
        @Order(13)
        @DisplayName("Get Role By Id Success Test")
        void get_Role_By_Id_Success_Test() {
            log.info("Executing the Test 13: get_Role_By_Id_Success_Test");

            initEntityFields(3L, "R", "R");

            testsHelper.verifySuccessTest(API_URL + "/3", null,
                    HttpMethod.GET, RoleEntity.class, fields);
        }

        @Test
        @Order(14)
        @DisplayName("Get Role By Id Not Found Test")
        void get_Role_By_Id_Not_Found_Test() {
            log.info("Executing the Test 14: get_Role_By_Id_Not_Found_Test");

            testsHelper.verifyExceptionTest(
                    API_URL + "/30", null, HttpMethod.GET,
                    "404 NOT_FOUND \"Couldn't find " + RoleEntity.class.getSimpleName()
                            + " with id: 30\"", HttpStatus.NOT_FOUND, fields);
        }

        @Test
        @Order(15)
        @DisplayName("Get Role By Name Success Test")
        void get_Role_By_Name_Success_Test() {
            log.info("Executing the Test 15: get_Role_By_Name_Success_Test");

            initEntityFields(3L, "R", "R");

            testsHelper.verifySuccessTest(API_URL + "/by/name/R", null,
                    HttpMethod.GET, RoleEntity.class, fields);
        }

        @Test
        @Order(16)
        @DisplayName("Get Role By Name Not Found Test")
        void get_Role_By_Name_Not_Found_Test() {
            log.info("Executing the Test 16: get_Role_By_Name_Not_Found_Test");

            testsHelper.verifyExceptionTest(
                    API_URL + "/by/name/test", null, HttpMethod.GET,
                    "404 NOT_FOUND \"Couldn't find " + RoleEntity.class.getSimpleName()
                            + " with name: test\"", HttpStatus.NOT_FOUND, fields);
        }
    }

}