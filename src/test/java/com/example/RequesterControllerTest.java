package com.example;

import com.example.entity.Requester;
import com.example.repository.RequesterRepository;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.core.type.Argument;
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
public class RequesterControllerTest {

    @Inject
    @Client("/")
    HttpClient client;

    @Inject
    RequesterRepository requesterRepository;

    private Requester mockRequester;

    @BeforeEach
    void setup() {
        reset(requesterRepository);

        mockRequester = new Requester();
        mockRequester.setId(1L);
        mockRequester.setName("teste");
        mockRequester.setEmail("teste@teste.com");

        when(requesterRepository.findById(1L)).thenReturn(Optional.of(mockRequester));
        when(requesterRepository.findAll()).thenReturn(List.of(mockRequester));
        when(requesterRepository.findById(1L)).thenReturn(Optional.of(mockRequester));
        when(requesterRepository.save(any(Requester.class))).thenReturn(mockRequester);
    }

    @MockBean(RequesterRepository.class)
    @Replaces(RequesterRepository.class)
    RequesterRepository requesterRepository() {
        return mock(RequesterRepository.class);
    }

    @Test
    void testGetRequesterReturnsListAndValidData() {
        Argument<Map<String, Object>> pageArgument = Argument.mapOf(String.class, Object.class);

        HttpResponse<Map<String, Object>> response = client.toBlocking().exchange(
                HttpRequest.GET("/requesters"), pageArgument
        );

        assertEquals(HttpStatus.OK, response.getStatus());

        Map<String, Object> body = response.body();
        assertNotNull(body);

        List<Map<String, Object>> content = (List<Map<String, Object>>) body.get("content");

        assertNotNull(content);
        assertFalse(content.isEmpty());

        Map<String, Object> firstService = content.get(0);
        assertEquals("Limpeza", firstService.get("description"));

        verify(requesterRepository, atLeastOnce()).findAll(any(Pageable.class));
    }

    @Test
    void testGetRequesterReturnsByIdAndValidData() {
        Argument<Map<String, Object>> requesterArgument = Argument.mapOf(String.class, Object.class);

        HttpResponse<Map<String, Object>> response = client.toBlocking().exchange(
                HttpRequest.GET("/requesters/1"), requesterArgument
        );

        assertEquals(HttpStatus.OK, response.getStatus());

        Map<String, Object> body = response.body();

        assertNotNull(body);
        assertEquals("teste", body.get("name"));
        assertEquals("teste@teste.com", body.get("email"));

        verify(requesterRepository, atLeastOnce()).findById(1L);
    }

    @Test
    void testPostRequester() {
        List<Map<String, Object>> newRequester = List.of(Map.of(
                "name", "teste",
                "email", "teste@teste.com"
        ));

        when(requesterRepository.saveAll(anyList())).thenReturn(List.of(mockRequester));

        HttpResponse <List<Map<String, Object>>> response = client.toBlocking().exchange(
                HttpRequest.POST("/requesters", newRequester),
                Argument.listOf(Argument.mapOf(String.class, Object.class))
        );

        assertEquals(HttpStatus.CREATED, response.getStatus());
        assertNotNull(response.body());
        assertEquals("teste", response.body().get(0).get("name"));
    }

    @Test
    void testPatchRequester() {
        Requester updatedRequester = mockRequester;
        updatedRequester.setName("teste1");

        when(requesterRepository.findById(1L)).thenReturn(Optional.of(mockRequester));
        when(requesterRepository.update(any(Requester.class))).thenReturn(updatedRequester);

        Map<String, Object> updateData = Map.of("name", "teste1");

        HttpResponse<Map<String, Object>> response = client.toBlocking().exchange(
            HttpRequest.PATCH("/requesters/1", updateData),
            Argument.mapOf(String.class, Object.class)
        );

        assertEquals(HttpStatus.NO_CONTENT, response.getStatus());

        verify(requesterRepository, atLeastOnce()).update(any(Requester.class));
    }

    @Test
    void testDeleteService() {
        when(requesterRepository.findById(1L)).thenReturn(Optional.of(mockRequester));

        doNothing().when(requesterRepository).delete(any(Requester.class));

        HttpResponse<Map<String, Object>> response = client.toBlocking().exchange(
                HttpRequest.DELETE("/requesters/1"),
                Argument.mapOf(String.class, Object.class)
        );

        assertEquals(HttpStatus.NO_CONTENT, response.getStatus());

        verify(requesterRepository, atLeastOnce()).delete(any(Requester.class));
    }
}