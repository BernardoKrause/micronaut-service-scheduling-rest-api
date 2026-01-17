package com.example;

import com.example.entity.Service;
import com.example.repository.ServiceRepository;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@MicronautTest
public class ServiceControllerTest {

    @Inject
    @Client("/")
    HttpClient client;

    @Inject
    ServiceRepository serviceRepository;

    private Service mockService;

    @BeforeEach
    void setup() {
        reset(serviceRepository);

        mockService = new Service();
        mockService.setId(1L);
        mockService.setDescription("Limpeza");
        mockService.setType("Geral");
        mockService.setValue(150.0);
        mockService.setOpened_at(LocalDate.now());
        mockService.setScheduled_for(LocalDate.now());

        when(serviceRepository.findById(1L)).thenReturn(Optional.of(mockService));
        when(serviceRepository.findAll()).thenReturn(List.of(mockService));
        when(serviceRepository.findById(1L)).thenReturn(Optional.of(mockService));
        when(serviceRepository.save(any(Service.class))).thenReturn(mockService);
    }

    @MockBean(ServiceRepository.class)
    @Replaces(ServiceRepository.class)
    ServiceRepository serviceRepository() {
        return mock(ServiceRepository.class);
    }

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

        assertNotNull(body);
        assertFalse(body.isEmpty());

        Map<String, Object> firstService = body.get(0);
        assertEquals("Limpeza", firstService.get("description"));

        verify(serviceRepository, atLeastOnce()).findAll();
    }

    @Test
    void testGetServiceReturnsByIdAndValidData() {
        Argument<Map<String, Object>> serviceArgument = Argument.mapOf(String.class, Object.class);

        HttpResponse<Map<String, Object>> response = client.toBlocking().exchange(
                HttpRequest.GET("/services/1"), serviceArgument
        );

        assertEquals(HttpStatus.OK, response.getStatus());

        Map<String, Object> body = response.body();

        assertNotNull(body);
        assertEquals("Limpeza", body.get("description"));
        assertEquals(150.0, body.get("value"));

        verify(serviceRepository, atLeastOnce()).findById(1L);
    }

    @Test
    void testPostService() {
        HttpResponse<Map<String, Object>> response = client.toBlocking().exchange(
                HttpRequest.POST("/services/", mockService),
                Argument.mapOf(String.class, Object.class)
        );

        assertEquals(HttpStatus.OK, response.getStatus());

        Map<String, Object> body = response.body();

        assertNotNull(body);
        assertEquals("Limpeza", body.get("description"));
        assertEquals(150.0, body.get("value"));

        verify(serviceRepository, atLeastOnce()).save(any(Service.class));
    }

    @Test
    void testPatchService() {
        Service updatedService = mockService;
        updatedService.setValue(200.00);

        when(serviceRepository.findById(1L)).thenReturn(Optional.of(mockService));
        when(serviceRepository.update(any(Service.class))).thenReturn(updatedService);

        Map<String, Object> updateData = Map.of("value", 200.0);

        HttpResponse<Map<String, Object>> response = client.toBlocking().exchange(
                HttpRequest.PATCH("/services/1", updateData),
                Argument.mapOf(String.class, Object.class)
        );

        assertEquals(HttpStatus.OK, response.getStatus());

        Map<String, Object> body = response.body();

        assertNotNull(body);
        assertEquals(200.0, body.get("value"));

        verify(serviceRepository, atLeastOnce()).update(any(Service.class));
    }

    @Test
    void testDeleteService() {
        when(serviceRepository.findById(1L)).thenReturn(Optional.of(mockService));

        doNothing().when(serviceRepository).delete(any(Service.class));

        HttpResponse<Map<String, Object>> response = client.toBlocking().exchange(
                HttpRequest.DELETE("/services/1"),
                Argument.mapOf(String.class, Object.class)
        );

        assertEquals(HttpStatus.OK, response.getStatus());

        Map<String, Object> body = response.body();
        assertNotNull(body);
        assertEquals("Limpeza", body.get("description"));

        verify(serviceRepository, atLeastOnce()).delete(any(Service.class));
    }
}