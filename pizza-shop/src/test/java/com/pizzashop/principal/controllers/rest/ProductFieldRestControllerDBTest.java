package com.pizzashop.principal.controllers.rest;

import com.pizzashop.principal.entities.CategoryEntity;
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

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@Slf4j
public class ProductFieldRestControllerDBTest {

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
    @DisplayName("Save Product Already Exist Test")
    void save_Product_Already_Exist_Test() {
        log.info("Executing the Test 1: save_Product_Already_Exist_Test");

        testsHelper.verifyExceptionTest(
                API_URL, JsonProductHelper.ProductAlreadyExist, HttpMethod.POST,
                "409 CONFLICT \"Busy name.\"", HttpStatus.CONFLICT, fields);
    }

    @Test
    @Order(2)
    @DisplayName("Save Product Name Is Null Test")
    void save_Product_Name_Is_Null_Test() {
        log.info("Executing the Test 2: save_Product_Name_Is_Null_Test");

        testsHelper.verifyExceptionTest(
                API_URL, JsonProductHelper.ProductWithNameNull, HttpMethod.POST,
                "Invalid request content. In field: name.", HttpStatus.BAD_REQUEST, fields);
    }

    @Test
    @Order(3)
    @DisplayName("Save Product Name Is Blank Test")
    void save_Product_Name_Is_Blank_Test() {
        log.info("Executing the Test 3: save_Product_Name_Is_Blank_Test");

        testsHelper.verifyExceptionTest(
                API_URL, JsonProductHelper.ProductWithNameBlank, HttpMethod.POST,
                "Invalid request content. In field: name.", HttpStatus.BAD_REQUEST, fields);
    }

    @Test
    @Order(4)
    @DisplayName("Save Product Name Is Empty Test")
    void save_Product_Name_Is_Empty_Test() {
        log.info("Executing the Test 4: save_Product_Name_Is_Empty_Test");

        testsHelper.verifyExceptionTest(
                API_URL, JsonProductHelper.ProductWithNameEmpty, HttpMethod.POST,
                "Invalid request content. In field: name.", HttpStatus.BAD_REQUEST, fields);
    }

    @Test
    @Order(5)
    @DisplayName("Save Product Price Is Null Test")
    void save_Product_Price_Is_Null_Test() {
        log.info("Executing the Test 5: save_Product_LastName_Is_Null_Test");

        testsHelper.verifyExceptionTest(
                API_URL, JsonProductHelper.ProductWithPriceNull, HttpMethod.POST,
                "Invalid request content. In field: price.", HttpStatus.BAD_REQUEST, fields);
    }

    @Test
    @Order(6)
    @DisplayName("Save Product Image Is Null Test")
    void save_Product_Image_Is_Null_Test() {
        log.info("Executing the Test 6: save_Product_Image_Is_Null_Test");

        testsHelper.verifyExceptionTest(
                API_URL, JsonProductHelper.ProductWithImageNull, HttpMethod.POST,
                "Invalid request content. In field: imageUrl.", HttpStatus.BAD_REQUEST, fields);
    }

    @Test
    @Order(7)
    @DisplayName("Save Product Image Is Blank Test")
    void save_Product_Image_Is_Blank_Test() {
        log.info("Executing the Test 7: save_Product_Image_Is_Blank_Test");

        testsHelper.verifyExceptionTest(
                API_URL, JsonProductHelper.ProductWithImageBlank, HttpMethod.POST,
                "Invalid request content. In field: imageUrl.", HttpStatus.BAD_REQUEST, fields);
    }

    @Test
    @Order(8)
    @DisplayName("Save Product Image Is Empty Test")
    void save_Product_Image_Is_Empty_Test() {
        log.info("Executing the Test 8: save_Product_Image_Is_Empty_Test");

        testsHelper.verifyExceptionTest(
                API_URL, JsonProductHelper.ProductWithImageEmpty, HttpMethod.POST,
                "Invalid request content. In field: imageUrl.", HttpStatus.BAD_REQUEST, fields);
    }

    @Test
    @Order(9)
    @DisplayName("Save Product Category Is Null Test")
    void save_Product_Category_Is_Null_Test() {
        log.info("Executing the Test 9: save_Product_Category_Is_Null_Test");

        testsHelper.verifyExceptionTest(
                API_URL, JsonProductHelper.ProductWithCategoryNull, HttpMethod.POST,
                "Invalid request content. In field: categoryId.", HttpStatus.BAD_REQUEST, fields);
    }

    @Test
    @Order(10)
    @DisplayName("Save Product Category Not Found Test")
    void save_Product_Category_Not_Found_Test() {
        log.info("Executing the Test 10: save_Product_Category_Not_Found_Test");

        testsHelper.verifyExceptionTest(
                API_URL, JsonProductHelper.ProductWithCategoryNotFound, HttpMethod.POST,
                "404 NOT_FOUND \"Couldn't find " + CategoryEntity.class.getSimpleName()
                        + " with id: 30\"", HttpStatus.NOT_FOUND, fields);
    }

    @Test
    @Order(11)
    @DisplayName("Update Product Already Exist Test")
    void update_Product_Name_Already_Exist_Test() {
        log.info("Executing the Test 11: update_Product_Already_Exist_Test");

        testsHelper.verifyExceptionTest(
                API_URL + "/2", JsonProductHelper.ProductAlreadyExist,
                HttpMethod.PUT, "409 CONFLICT \"Busy name.\"", HttpStatus.CONFLICT, fields);
    }

    @Test
    @Order(12)
    @DisplayName("Update Product Category Not Found Test")
    void update_Product_Category_Not_Found_Test() {
        log.info("Executing the Test 11: update_Product_Category_Not_Found_Test");

        testsHelper.verifyExceptionTest(
                API_URL + "/2", JsonProductHelper.ProductWithCategoryNotFound, HttpMethod.PUT,
                "404 NOT_FOUND \"Couldn't find " + CategoryEntity.class.getSimpleName()
                        + " with id: 30\"", HttpStatus.NOT_FOUND, fields);
    }

}