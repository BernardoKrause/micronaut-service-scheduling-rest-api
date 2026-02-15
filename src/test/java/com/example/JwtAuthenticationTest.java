package com.example;

import io.micronaut.context.annotation.Property;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.token.render.BearerAccessRefreshToken;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@MicronautTest
@Property(name = "micronaut.security.enabled", value = "true")
public class JwtAuthenticationTest {

    @Inject
    @Client("/")
    HttpClient client;

    @Test
    void testLoginAndAccessSecuredEndpoint() {
        UsernamePasswordCredentials creds = new UsernamePasswordCredentials("admin", "admin");

        HttpRequest<?> request = HttpRequest.POST("/login", creds);

        HttpResponse<BearerAccessRefreshToken> response = client.toBlocking().exchange(request, BearerAccessRefreshToken.class);

        assertEquals(HttpStatus.OK, response.getStatus());
        BearerAccessRefreshToken token = response.body();
        assertNotNull(token.getAccessToken());
        String accessToken = token.getAccessToken();

        HttpRequest<?> protectedRequest = HttpRequest.GET("/api/status")
                .bearerAuth(accessToken);

        HttpResponse<String> protectedResponse = client.toBlocking().exchange(protectedRequest, String.class);

        assertEquals(HttpStatus.OK, protectedResponse.getStatus());
    }

    @Test
    void testAccessDeniedWithoutToken() {
        HttpClientResponseException thrown = Assertions.assertThrows(HttpClientResponseException.class, () -> {
            client.toBlocking().exchange(HttpRequest.GET("/api/status"));
        });

        assertEquals(HttpStatus.UNAUTHORIZED, thrown.getStatus());
    }
}