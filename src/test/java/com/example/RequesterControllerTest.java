package com.example;

import com.example.entity.Requester;
import com.example.repository.RequesterRepository;
import io.micronaut.context.annotation.Replaces;
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
        mockRequester.setFullName("teste");
        mockRequester.setEmail("teste@teste.com");
        mockRequester.setDepartment("TI");
        mockRequester.setUserName("teste_user");
        mockRequester.setPhoneNumber("123456789");

        when(requesterRepository.findById(1L)).thenReturn(Optional.of(mockRequester));
        when(requesterRepository.findAll()).thenReturn(List.of(mockRequester));
        when(requesterRepository.findById(1L)).thenReturn(Optional.of(mockRequester));
        when(requesterRepository.save(any(Requester.class))).thenReturn(mockRequester);
        when(requesterRepository.findAll(any(Pageable.class)))
                .thenReturn(Page.of(List.of(mockRequester), Pageable.from(0, 10), 1L));
        when(requesterRepository.findWithFilters(any(), any(), any(), any(), any(), any(Pageable.class)))
                .thenReturn(Page.of(List.of(mockRequester), Pageable.from(0, 10), 1L));
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

        Map<String, Object> firstRequester = content.get(0);
        assertEquals("teste", firstRequester.get("fullName"));

        verify(requesterRepository, atLeastOnce()).findWithFilters(any(), any(), any(), any(), any(), any(Pageable.class));
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
        assertEquals("teste", body.get("fullName"));
        assertEquals("teste@teste.com", body.get("email"));

        verify(requesterRepository, atLeastOnce()).findById(1L);
    }

    @Test
    void testPostRequester() {
        Requester newRequester = new Requester();
        newRequester.setFullName("teste");
        newRequester.setEmail("teste@teste.com");
        newRequester.setDepartment("TI");
        newRequester.setUserName("teste_user");
        newRequester.setPhoneNumber("123456789");

        when(requesterRepository.saveAll(anyList())).thenReturn(List.of(mockRequester));

        try {
            HttpResponse<List<Requester>> response = client.toBlocking().exchange(
                    HttpRequest.POST("/requesters", List.of(newRequester))
                            .basicAuth("admin", "admin"),
                    Argument.listOf(Requester.class)
            );

            assertEquals(HttpStatus.CREATED, response.getStatus());
            assertNotNull(response.body());
            assertEquals("teste", response.body().get(0).getFullName());
        } catch (io.micronaut.http.client.exceptions.HttpClientResponseException e) {
            System.out.println("Error response: " + e.getResponse().getBody(String.class).orElse("No body"));
            throw e;
        }
    }

    @Test
    void testPatchRequester() {
        Requester updatedRequester = mockRequester;
        updatedRequester.setFullName("teste1");

        when(requesterRepository.findById(1L)).thenReturn(Optional.of(mockRequester));
        when(requesterRepository.update(any(Requester.class))).thenReturn(updatedRequester);

        Map<String, Object> updateData = Map.of("fullName", "teste1");

        HttpResponse<Map<String, Object>> response = client.toBlocking().exchange(
            HttpRequest.PATCH("/requesters/1", updateData)
                    .basicAuth("admin", "admin"),
            Argument.mapOf(String.class, Object.class)
        );

        assertEquals(HttpStatus.OK, response.getStatus());

        verify(requesterRepository, atLeastOnce()).update(any(Requester.class));
    }

    @Test
    void testDeleteService() {
        when(requesterRepository.findById(1L)).thenReturn(Optional.of(mockRequester));

        doNothing().when(requesterRepository).delete(any(Requester.class));

        HttpResponse<Map<String, Object>> response = client.toBlocking().exchange(
                HttpRequest.DELETE("/requesters/1")
                        .basicAuth("admin", "admin"),
                Argument.mapOf(String.class, Object.class)
        );

        assertEquals(HttpStatus.OK, response.getStatus());

        verify(requesterRepository, atLeastOnce()).delete(any(Requester.class));
    }
}