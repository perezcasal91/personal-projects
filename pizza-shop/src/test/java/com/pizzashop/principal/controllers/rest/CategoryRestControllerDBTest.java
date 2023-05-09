package com.pizzashop.principal.controllers.rest;

import com.pizzashop.principal.entities.CategoryEntity;
import com.pizzashop.principal.helpers.JsonCategoryHelper;
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
public class CategoryRestControllerDBTest {

    private final Long id = 4L;
    private final String API_URL = "/api/v1/categories";
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
    @DisplayName("Save Category Success Test")
    @Sql({"/category_data.sql"})
    void save_Category_Success_Test() {
        log.info("Executing the Test 1: save_Category_Success_Test");

        initEntityFields(id, "C4", "C4");

        testsHelper.verifySuccessTest(API_URL,
                JsonCategoryHelper.Category,
                HttpMethod.POST, CategoryEntity.class, fields);
    }

    @Test
    @Order(2)
    @DisplayName("Save Category Already Exist Test")
    void save_Category_Already_Exist_Test() {
        log.info("Executing the Test 2: save_Category_Already_Exist_Test");

        testsHelper.verifyExceptionTest(
                API_URL, JsonCategoryHelper.Category, HttpMethod.POST,
                "409 CONFLICT \"Busy name.\"", HttpStatus.CONFLICT, fields);
    }

    @Test
    @Order(3)
    @DisplayName("Save Category Name Is Null Test")
    void save_Category_Name_Is_Null_Test() {
        log.info("Executing the Test 3: save_Category_Name_Is_Null_Test");

        testsHelper.verifyExceptionTest(
                API_URL, JsonCategoryHelper.CategoryWithNameNull, HttpMethod.POST,
                "Invalid request content. In field: name.", HttpStatus.BAD_REQUEST, fields);
    }

    @Test
    @Order(4)
    @DisplayName("Save Category Name Is Blank Test")
    void save_Category_Name_Is_Blank_Test() {
        log.info("Executing the Test 4: save_Category_Name_Is_Blank_Test");

        testsHelper.verifyExceptionTest(
                API_URL, JsonCategoryHelper.CategoryWithNameBlank, HttpMethod.POST,
                "Invalid request content. In field: name.", HttpStatus.BAD_REQUEST, fields);
    }

    @Test
    @Order(5)
    @DisplayName("Save Category Name Is Empty Test")
    void save_Category_Name_Is_Empty_Test() {
        log.info("Executing the Test 5: save_Category_Name_Is_Empty_Test");

        testsHelper.verifyExceptionTest(
                API_URL, JsonCategoryHelper.CategoryWithNameEmpty, HttpMethod.POST,
                "Invalid request content. In field: name.", HttpStatus.BAD_REQUEST, fields);
    }

    @Test
    @Order(6)
    @DisplayName("Update Category Success Test")
    void update_Category_Success_Test() {
        log.info("Executing the Test 6: update_Category_Success_Test");

        initEntityFields(id, "C5", "C5");

        testsHelper.verifySuccessTest(API_URL + "/" + id,
                JsonCategoryHelper.CategoryToUpdate,
                HttpMethod.PUT, CategoryEntity.class, fields);
    }

    @Test
    @Order(7)
    @DisplayName("Update Category Description Success Test")
    void update_Category_Description_Success_Test() {
        log.info("Executing the Test 7: update_Category_Description_Success_Test");

        initEntityFields(id, "C5", "C5 Change");

        testsHelper.verifySuccessTest(API_URL + "/" + id,
                JsonCategoryHelper.CategoryDescriptionChange,
                HttpMethod.PUT, CategoryEntity.class, fields);
    }

    @Test
    @Order(8)
    @DisplayName("Update Category Name Already Exist Test")
    void update_Category_Name_Already_Exist_Test() {
        log.info("Executing the Test 8: update_Category_Name_Already_Exist_Test");

        testsHelper.verifyExceptionTest(
                API_URL + "/" + id, JsonCategoryHelper.CategoryAlreadyExist,
                HttpMethod.PUT, "409 CONFLICT \"Busy name.\"", HttpStatus.CONFLICT, fields);
    }

    @Test
    @Order(9)
    @DisplayName("Update Category Not Found Test")
    void update_Category_Not_Found_Test() {
        log.info("Executing the Test 9: update_Category_Not_Found_Test");

        testsHelper.verifyExceptionTest(
                API_URL + "/30", JsonCategoryHelper.CategoryToUpdate, HttpMethod.PUT,
                "404 NOT_FOUND \"Couldn't find " + CategoryEntity.class.getSimpleName()
                        + " with id: 30\"", HttpStatus.NOT_FOUND, fields);
    }

    @Test
    @Order(10)
    @DisplayName("Delete Category By Id Success Test")
    void delete_Category_By_Id_Success_Test() {
        log.info("Executing the Test 10: delete_Category_By_Id_Success_Test");

        initEntityFields(id, "C5", "C5 Change");

        testsHelper.verifySuccessTest(API_URL + "/" + id, null,
                HttpMethod.DELETE, CategoryEntity.class, fields);
    }

    @Test
    @Order(11)
    @DisplayName("Delete Category By Id Not Found Test")
    void delete_Category_By_Id_Not_Found_Test() {
        log.info("Executing the Test 11: delete_Category_By_Id_Not_Found_Test");

        testsHelper.verifyExceptionTest(
                API_URL + "/30", null, HttpMethod.DELETE,
                "404 NOT_FOUND \"Couldn't find " + CategoryEntity.class.getSimpleName()
                        + " with id: 30\"", HttpStatus.NOT_FOUND, fields);
    }

    private void initEntityFields(Long id, String name, String description) {
        fields.put("entity", "category");
        fields.put("id", id);
        fields.put("name", name);
        fields.put("description", description);
    }

    private void initListFields(int size) {
        fields.put("size", size);
    }

    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    public class GetCategoryTest {

        @Test
        @Order(12)
        @DisplayName("Get All Categories Success Test")
        void get_All_Categories_Success_Test() {
            log.info("Executing the Test 12: get_All_Categories_Success_Test");

            initListFields(3);

            testsHelper.verifySuccessTest(API_URL, null,
                    HttpMethod.GET, CategoryEntity[].class, fields);
        }

        @Test
        @Order(13)
        @DisplayName("Get Category By Id Success Test")
        void get_Category_By_Id_Success_Test() {
            log.info("Executing the Test 13: get_Category_By_Id_Success_Test");

            initEntityFields(1L, "C", "C");

            testsHelper.verifySuccessTest(API_URL + "/1", null,
                    HttpMethod.GET, CategoryEntity.class, fields);
        }

        @Test
        @Order(14)
        @DisplayName("Get Category By Id Not Found Test")
        void get_Category_By_Id_Not_Found_Test() {
            log.info("Executing the Test 14: get_Category_By_Id_Not_Found_Test");

            testsHelper.verifyExceptionTest(
                    API_URL + "/30", null, HttpMethod.GET,
                    "404 NOT_FOUND \"Couldn't find " + CategoryEntity.class.getSimpleName()
                            + " with id: 30\"", HttpStatus.NOT_FOUND, fields);
        }

        @Test
        @Order(15)
        @DisplayName("Get Category By Name Success Test")
        void get_Category_By_Name_Success_Test() {
            log.info("Executing the Test 15: get_Category_By_Name_Success_Test");

            initEntityFields(1L, "C", "C");

            testsHelper.verifySuccessTest(API_URL + "/by/name/C", null,
                    HttpMethod.GET, CategoryEntity.class, fields);
        }

        @Test
        @Order(16)
        @DisplayName("Get Category By Name Not Found Test")
        void get_Category_By_Name_Not_Found_Test() {
            log.info("Executing the Test 16: get_Category_By_Name_Not_Found_Test");

            testsHelper.verifyExceptionTest(
                    API_URL + "/by/name/test", null, HttpMethod.GET,
                    "404 NOT_FOUND \"Couldn't find " + CategoryEntity.class.getSimpleName()
                            + " with name: test\"", HttpStatus.NOT_FOUND, fields);
        }
    }

}