package com.example;

import com.example.entity.Requester;
import com.example.entity.Service;
import com.example.repository.RequesterRepository;
import com.example.repository.ServiceRepository;
import io.micronaut.context.annotation.Property;
import io.micronaut.core.type.Argument;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
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
@Property(name = "AUTH_USER", value = "test-user")
@Property(name = "AUTH_SECRET", value = "test-pass")
public class ServiceControllerTest {

    @Inject
    @Client("/")
    HttpClient client;

    @Inject
    ServiceRepository serviceRepository;

    @Inject
    RequesterRepository requesterRepository;

    private Service mockService;

    @BeforeEach
    void setup() {
        reset(serviceRepository);

        // 1. Criar e salvar um Requester real no H2 para satisfazer a FK
        Requester requester = new Requester();
        requester.setId(1L);
        requester.setFullName("Cliente Teste");
        requester.setEmail("cliente@teste.com");
        requester.setDepartment("TI");
        requester.setUserName("cliente_teste");
        requester.setPhoneNumber("123456789");
        requesterRepository.save(requester);

        mockService = new Service();
        mockService.setId(1L);
        mockService.setDescription("Limpeza");
        mockService.setType("Geral");
        mockService.setValue(150.0);
        mockService.setOpened_at(LocalDate.now());
        mockService.setScheduled_for(LocalDate.now());

        when(serviceRepository.findAll(any(Pageable.class)))
                .thenReturn(Page.of(List.of(mockService), Pageable.from(0, 10), 1L));
        when(serviceRepository.FindWithFilters(any(), any(), any(), any(), any(), any(Pageable.class)))
                .thenReturn(Page.of(List.of(mockService), Pageable.from(0, 10), 1L));

        when(serviceRepository.findById(1L)).thenReturn(Optional.of(mockService));
        when(serviceRepository.findAll()).thenReturn(List.of(mockService));
        when(serviceRepository.findById(1L)).thenReturn(Optional.of(mockService));
        when(serviceRepository.save(any(Service.class))).thenReturn(mockService);

    }

    @MockBean(ServiceRepository.class)
    ServiceRepository serviceRepository() {
        return mock(ServiceRepository.class);
    }

    @Test
    void testGetServicesReturnsListAndValidData() {
        Argument<Map<String, Object>> pageArgument = Argument.mapOf(String.class, Object.class);

        HttpResponse<Map<String, Object>> response = client.toBlocking().exchange(
                HttpRequest.GET("/services"), pageArgument
        );

        assertEquals(HttpStatus.OK, response.getStatus());

        Map<String, Object> body = response.body();
        assertNotNull(body);

        List<Map<String, Object>> content = (List<Map<String, Object>>) body.get("content");

        assertNotNull(content);
        assertFalse(content.isEmpty());

        Map<String, Object> firstService = content.get(0);
        assertEquals("Limpeza", firstService.get("description"));

        verify(serviceRepository, atLeastOnce()).FindWithFilters(any(), any(), any(), any(), any(), any(io.micronaut.data.model.Pageable.class));
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
        List<Map<String, Object>> newService = List.of(Map.of(
                "description", "Limpeza",
                "type", "Geral",
                "value", 150.0,
                "opened_at", "2026-01-20",
                "scheduled_for", "2026-01-23",
                "requester_id", 1L
        ));

        when(serviceRepository.saveAll(anyList())).thenReturn(List.of(mockService));

        HttpResponse <List<Map<String, Object>>> response = client.toBlocking().exchange(
                HttpRequest.POST("/services", newService),
                Argument.listOf(Argument.mapOf(String.class, Object.class))
        );

        assertEquals(HttpStatus.CREATED, response.getStatus());
        assertNotNull(response.body());
        assertEquals("Limpeza", response.body().get(0).get("description"));
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

        verify(serviceRepository, atLeastOnce()).delete(any(Service.class));
    }
}