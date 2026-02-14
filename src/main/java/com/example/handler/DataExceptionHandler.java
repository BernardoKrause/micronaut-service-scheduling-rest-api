package com.example.handler;

import io.micronaut.context.annotation.Requires;
import io.micronaut.data.exceptions.DataAccessException;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.server.exceptions.ExceptionHandler; // Use this one!
import jakarta.inject.Singleton;
import java.util.Map;

@Singleton
@Requires(classes = {DataAccessException.class, ExceptionHandler.class})
public class DataExceptionHandler implements ExceptionHandler<DataAccessException, HttpResponse<?>> {

    @Override
    public HttpResponse<?> handle(HttpRequest request, DataAccessException exception) {
        return HttpResponse.badRequest(Map.of(
                "error", "Database Integrity Violation",
                "message", "The data provided violates database constraints (e.g., null values or duplicate keys).",
                "details", exception.getMessage()
        ));
    }
}