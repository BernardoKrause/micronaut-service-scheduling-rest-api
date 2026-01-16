package com.example;

import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
public class ServiceControllerTest {

    @Inject
    @Client("/")
    HttpClient client;

    @Test
    void testGetServicesReturnsListAndValidData() {
        Argument<List<Map<String, Object>>> listaDeServices = Argument.listOf(
                Argument.mapOf(String.class, Object.class)
        );

        HttpResponse<List<Map<String, Object>>> response = client.toBlocking().exchange(
                HttpRequest.GET("/services"), listaDeServices
        );

        assertEquals(HttpStatus.OK, response.getStatus());

        List<Map<String, Object>> body = response.body();

        assertNotNull(body, "O corpo da resposta não deve ser nulo");

        assertFalse(body.isEmpty(), "A lista retornada está vazia. Certifique-se de ter dados no banco ou use um Mock.");

        Map<String, Object> firstService = body.get(0);

        assertAll("Validação dos campos",
                () -> assertNotNull(firstService.get("id")),
                () -> assertInstanceOf(String.class, firstService.get("description")),
                () -> assertNotNull(firstService.get("type")),
                () -> assertNotNull(firstService.get("value")),
                () -> assertNotNull(firstService.get("scheduled_for")),
                () -> assertNotNull(firstService.get("opened_at"))
        );
    }
}