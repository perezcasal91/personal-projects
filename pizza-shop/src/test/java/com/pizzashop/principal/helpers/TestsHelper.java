package com.pizzashop.principal.helpers;

import com.pizzashop.principal.dtos.AuthResponseDTO;
import com.pizzashop.principal.dtos.ExceptionResponseDTO;
import com.pizzashop.principal.dtos.MessageResponseDTO;
import com.pizzashop.principal.entities.CategoryEntity;
import com.pizzashop.principal.entities.ProductEntity;
import com.pizzashop.principal.entities.RoleEntity;
import com.pizzashop.principal.entities.UserEntity;
import com.pizzashop.principal.security.TokenProvider;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class TestsHelper {
    private final int port;
    private TestRestTemplate testRestTemplate;
    private RestTemplateBuilder restTemplateBuilder;
    private HttpHeaders httpHeaders;

    private ResponseEntity<?> responseEntity;

    private HttpEntity<String> httpEntity;

    private TokenProvider tokenProvider;

    public TestsHelper(RestTemplateBuilder restTemplateBuilder, int port) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.port = port;
        initTemplate();
        initHeadersWithToken(loginAndGetToken(JsonAuthHelper.RoleAdmin.getData()));
    }

    public TestsHelper(RestTemplateBuilder restTemplateBuilder, int port,
                       TokenProvider tokenProvider) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.port = port;
        this.tokenProvider = tokenProvider;
        initTemplate();
        initHeaders();
    }

    private void initTemplate() {
        restTemplateBuilder = restTemplateBuilder.rootUri("http://localhost:" + port);
        testRestTemplate = new TestRestTemplate(restTemplateBuilder);
    }

    private void initHeaders() {
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
    }

    private void initHeadersWithToken(String token) {
        initHeaders();
        httpHeaders.setBearerAuth(token);
    }

    private String loginAndGetToken(String requestBody) {
        initHeaders();

        login(requestBody);

        return ((AuthResponseDTO) Objects.requireNonNull(
                responseEntity.getBody())).getAccessToken();
    }

    private String refreshAndGetToken(String requestBody) {
        login(requestBody);

        return ((AuthResponseDTO) Objects.requireNonNull(
                responseEntity.getBody())).getRefreshToken();
    }

    private void login(String requestBody) {
        httpEntity = new HttpEntity<>(requestBody, httpHeaders);

        responseEntity = testRestTemplate.exchange("/api/v1/auth/login",
                HttpMethod.POST, httpEntity, AuthResponseDTO.class);
    }

    public void verifySuccessTest(String path, JsonHelper requestBody,
                                  HttpMethod method, Class<?> responseType,
                                  @NonNull Map<String, Object> fields) {
        if (Objects.equals(fields.get("entity"), "auth")) {
            if (Objects.equals(fields.get("method"), "login")) {
                execute(path, requestBody, method, responseType);
                authTest(fields);
            }
            if (Objects.equals(fields.get("method"), "refresh")) {
                initHeadersWithToken(requestBody.getData());
                execute(path, requestBody, method, responseType);
                authTest(fields);
            }
            if (Objects.equals(fields.get("method"), "logout")) {
                execute(path, requestBody, method, responseType);
                logoutTest(fields);
            }
            if (Objects.equals(fields.get("method"), "expired")) {
                initHeadersWithToken(loginAndGetToken(requestBody.getData()));
                try {
                    Thread.sleep(15000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                execute(path, null, method, responseType);
                expiredTest();
            }
        } else {
            execute(path, requestBody, method, responseType);

            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

            if (Objects.isNull(fields.get("size"))) {
                if (Objects.equals(fields.get("entity"), "role")) {
                    roleTest(fields);
                }
                if (Objects.equals(fields.get("entity"), "user")) {
                    userTest(fields);
                }
                if (Objects.equals(fields.get("entity"), "category")) {
                    categoryTest(fields);
                }
                if (Objects.equals(fields.get("entity"), "product")) {
                    productTest(fields);
                }
            } else {
                List<?> entityList = List.of(Objects.requireNonNull(
                        (Object[]) responseEntity.getBody()));
                assertNotNull(entityList);
                assertEquals(fields.get("size"), entityList.size());
            }
        }
    }

    private void authTest(Map<String, Object> fields) {
        AuthResponseDTO response = (AuthResponseDTO) responseEntity.getBody();
        assertNotNull(response);
        assertTrue(response.getAccessToken().length() > 0);
        assertTrue(response.getRefreshToken().length() > 0);
        assertEquals(fields.get("user"), tokenProvider.getUsernameFromToken(response.getAccessToken()));
        assertEquals(fields.get("user"), tokenProvider.getUsernameFromToken(response.getRefreshToken()));
    }

    private void logoutTest(Map<String, Object> fields) {
        MessageResponseDTO response = (MessageResponseDTO) responseEntity.getBody();
        assertNotNull(response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals(fields.get("message"), response.message());
    }

    private void expiredTest() {
        Map<?, ?> response = (Map<?, ?>) responseEntity.getBody();
        assertNotNull(response);
        assertEquals(response.get("token"), "expired");
    }

    private void roleTest(Map<String, Object> fields) {
        RoleEntity response = (RoleEntity) responseEntity.getBody();
        assertNotNull(response);
        assertEquals((Long) fields.get("id"), response.getId());
        assertEquals(fields.get("name"), response.getName());
        assertEquals(fields.get("description"), response.getDescription());
    }

    private void userTest(Map<String, Object> fields) {
        UserEntity response = (UserEntity) responseEntity.getBody();
        assertNotNull(response);
        assertEquals((Long) fields.get("id"), response.getId());
        assertEquals(fields.get("firstName"), response.getFirstName());
        assertEquals(fields.get("username"), response.getUsername());
        assertEquals(fields.get("email"), response.getEmail());
        assertEquals(fields.get("phone"), response.getPhone());
    }

    private void categoryTest(Map<String, Object> fields) {
        CategoryEntity response = (CategoryEntity) responseEntity.getBody();
        assertNotNull(response);
        assertEquals((Long) fields.get("id"), response.getId());
        assertEquals(fields.get("name"), response.getName());
        assertEquals(fields.get("description"), response.getDescription());
    }

    private void productTest(Map<String, Object> fields) {
        ProductEntity response = (ProductEntity) responseEntity.getBody();
        assertNotNull(response);
        assertEquals((Long) fields.get("id"), response.getId());
        assertEquals(fields.get("name"), response.getName());
        assertEquals(fields.get("description"), response.getDescription());
        assertEquals(fields.get("status"), response.getStatus().name());
        assertEquals((Long) fields.get("category"), response.getCategory().getId());
    }

    public void verifyExceptionTest(String path, JsonHelper requestBody, HttpMethod method,
                                    String message, HttpStatus httpStatus, Map<String, Object> fields) {
        if (Objects.equals(fields.get("method"), "refresh")) {
            if (Objects.equals(fields.get("test"), "header_null")) {
                refreshAndGetToken(requestBody.getData());
            }
            if (Objects.equals(fields.get("test"), "bearer_null")) {
                refreshAndGetToken(requestBody.getData());
                initHeadersWithToken(null);
            }
            if (Objects.equals(fields.get("test"), "malformed_token_missing")) {
                String[] tokenParts = refreshAndGetToken(requestBody.getData()).split("\\.");

                String refreshToken = tokenParts[0] + "" + tokenParts[1] + "." + tokenParts[2];

                initHeadersWithToken(refreshToken);
            }
            if (Objects.equals(fields.get("test"), "malformed_token")) {
                String[] tokenParts = refreshAndGetToken(requestBody.getData()).split("\\.");

                String refreshToken = tokenParts[0] + "." + tokenParts[1] + "." + tokenParts[2] + "zxc";

                initHeadersWithToken(refreshToken);
            }
            execute(path, requestBody, method, ExceptionResponseDTO.class);
        } else {
            execute(path, requestBody, method, ExceptionResponseDTO.class);
        }

        ExceptionResponseDTO exceptionResponseDTO =
                (ExceptionResponseDTO) responseEntity.getBody();

        assertEquals(httpStatus, responseEntity.getStatusCode());
        assertNotNull(exceptionResponseDTO);
        assertEquals(httpStatus.value(), exceptionResponseDTO.statusValue());
        assertEquals(message, exceptionResponseDTO.message());
    }

    public void execute(String path, JsonHelper requestBody,
                        HttpMethod method, Class<?> responseType) {
        if (requestBody != null) {
            httpEntity = new HttpEntity<>(requestBody.getData(), httpHeaders);
        } else {
            httpEntity = new HttpEntity<>(null, httpHeaders);
        }

        responseEntity = testRestTemplate.exchange(path, method, httpEntity, responseType);
    }

}
