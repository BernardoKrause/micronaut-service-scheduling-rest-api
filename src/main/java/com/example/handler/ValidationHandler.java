package com.example.handler;

import io.micronaut.context.annotation.Replaces;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import io.micronaut.validation.exceptions.ConstraintExceptionHandler;
import jakarta.inject.Singleton;
import jakarta.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@Singleton
@Replaces(ConstraintExceptionHandler.class)
public class ValidationHandler implements ExceptionHandler<ConstraintViolationException, HttpResponse<?>> {
    @Override
    public HttpResponse<?> handle(HttpRequest request, ConstraintViolationException exception) {
        var errors = exception.getConstraintViolations().stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .collect(Collectors.toList());
        return HttpResponse.badRequest(errors);
    }
}