package com.pizzashop.principal.controllers.rest;

import com.pizzashop.principal.entities.ProductEntity;
import com.pizzashop.principal.helpers.JsonProductHelper;
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
public class ProductRestControllerDBTest {
    private final Long id = 6L;
    private final String API_URL = "/api/v1/products";
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
    @DisplayName("Save Product Success Test")
    @Sql({"/product_data.sql"})
    void save_Product_Success_Test() {
        log.info("Executing the Test 1: save_Product_Success_Test");

        initEntityFields(id, "P6", "P6", "STOCK", 2L);

        testsHelper.verifySuccessTest(API_URL,
                JsonProductHelper.Product,
                HttpMethod.POST, ProductEntity.class, fields);
    }

    @Test
    @Order(2)
    @DisplayName("Update Product Success Test")
    void update_Product1_Success_Test() {
        log.info("Executing the Test 2: update_Product1_Success_Test");

        initEntityFields(id, "P7", "P7", "ORDER", 3L);

        testsHelper.verifySuccessTest(API_URL + "/" + id,
                JsonProductHelper.ProductToUpdate,
                HttpMethod.PUT, ProductEntity.class, fields);
    }

    @Test
    @Order(3)
    @DisplayName("Update Product Description Success Test")
    void update_Description_Product_Success_Test() {
        log.info("Executing the Test 3: update_Description_Product_Success_Test");

        initEntityFields(id, "P7", "P7", "ORDER", 3L);

        testsHelper.verifySuccessTest(API_URL + "/" + id,
                JsonProductHelper.ProductDescriptionChange,
                HttpMethod.PUT, ProductEntity.class, fields);
    }

    @Test
    @Order(4)
    @DisplayName("Update Product 2 Not Found Test")
    void update_Product2_Not_Found_Test() {
        log.info("Executing the Test 4: update_Product2_Not_Found_Test");

        testsHelper.verifyExceptionTest(
                API_URL + "/30", JsonProductHelper.ProductToUpdate, HttpMethod.PUT,
                "404 NOT_FOUND \"Couldn't find " + ProductEntity.class.getSimpleName()
                        + " with id: 30\"", HttpStatus.NOT_FOUND, fields);
    }

    @Test
    @Order(5)
    @DisplayName("Delete Product By Id Success Test")
    void delete_Product_By_Id_Success_Test() {
        log.info("Executing the Test 5: delete_Product_By_Id_Success_Test");

        initEntityFields(id, "P7", "P7", "ORDER", 3L);

        testsHelper.verifySuccessTest(API_URL + "/" + id, null,
                HttpMethod.DELETE, ProductEntity.class, fields);
    }

    @Test
    @Order(6)
    @DisplayName("Delete Product By Id Not Found Test")
    void delete_Product_By_Id_Not_Found_Test() {
        log.info("Executing the Test 6: delete_Product_By_Id_Not_Found_Test");

        testsHelper.verifyExceptionTest(
                API_URL + "/30", null, HttpMethod.DELETE,
                "404 NOT_FOUND \"Couldn't find " + ProductEntity.class.getSimpleName()
                        + " with id: 30\"", HttpStatus.NOT_FOUND, fields);
    }

    private void initEntityFields(Long id, String name, String description,
                                  String status, Long category) {
        fields.put("entity", "products");
        fields.put("id", id);
        fields.put("name", name);
        fields.put("description", description);
        fields.put("status", status);
        fields.put("category", category);
    }

    private void initListFields(int size) {
        fields.put("size", size);
    }

    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    public class GetProductTest {

        @Test
        @Order(7)
        @DisplayName("Get All Products Success Test")
        void get_All_Products_Success_Test() {
            log.info("Executing the Test 7: get_All_Products_Success_Test");

            initListFields(5);

            testsHelper.verifySuccessTest(API_URL, null,
                    HttpMethod.GET, ProductEntity[].class, fields);
        }

        @Test
        @Order(8)
        @DisplayName("Get Product By Id Success Test")
        void get_Product_By_Id_Success_Test() {
            log.info("Executing the Test 8: get_Product_By_Id_Success_Test");

            initEntityFields(id, "P1", "P1", "STOCK", 2L);

            testsHelper.verifySuccessTest(API_URL + "/1", null,
                    HttpMethod.GET, ProductEntity.class, fields);
        }

        @Test
        @Order(9)
        @DisplayName("Get Product By Id Not Found Test")
        void get_Product_By_Id_Not_Found_Test() {
            log.info("Executing the Test 9: get_Product_By_Id_Not_Found_Test");

            testsHelper.verifyExceptionTest(
                    API_URL + "/30", null, HttpMethod.GET,
                    "404 NOT_FOUND \"Couldn't find " + ProductEntity.class.getSimpleName()
                            + " with id: 30\"", HttpStatus.NOT_FOUND, fields);
        }

        @Test
        @Order(10)
        @DisplayName("Get Product By Name Success Test")
        void get_Product_By_Name_Success_Test() {
            log.info("Executing the Test 10: get_Product_By_Name_Success_Test");

            initEntityFields(id, "P1", "P1", "STOCK", 2L);

            testsHelper.verifySuccessTest(API_URL + "/by/name/P1", null,
                    HttpMethod.GET, ProductEntity.class, fields);
        }

        @Test
        @Order(11)
        @DisplayName("Get Product By Name Not Found Test")
        void get_Product_By_Name_Not_Found_Test() {
            log.info("Executing the Test 11: get_Product_By_Name_Not_Found_Test");

            testsHelper.verifyExceptionTest(
                    API_URL + "/by/name/test", null, HttpMethod.GET,
                    "404 NOT_FOUND \"Couldn't find " + ProductEntity.class.getSimpleName()
                            + " with name: test\"", HttpStatus.NOT_FOUND, fields);
        }

        @Test
        @Order(12)
        @DisplayName("Get Product By Status Test")
        void get_Products_By_Status_Test() {
            log.info("Executing the Test 11: get_Products_By_Status_Test");

            initListFields(3);

            testsHelper.verifySuccessTest(API_URL + "/by/status/STOCK", null,
                    HttpMethod.GET, ProductEntity[].class, fields);
        }

        @Test
        @Order(12)
        @DisplayName("Get Product By Category Id Test")
        void get_Products_By_Category_Id_Test() {
            log.info("Executing the Test 11: get_Products_By_Category_Id_Test");

            initListFields(3);

            testsHelper.verifySuccessTest(API_URL + "/by/category/2", null,
                    HttpMethod.GET, ProductEntity[].class, fields);
        }

    }
}