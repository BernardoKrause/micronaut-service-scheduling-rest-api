package com.example.controller;

import com.example.dto.ServiceDTO;
import com.example.entity.Service;
import com.example.facade.ServiceFacade;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import jakarta.validation.Valid;

import javax.management.ServiceNotFoundException;
import java.util.*;

@Controller("/services")
public class ServiceController {

    @Inject
    private ServiceFacade serviceFacade;

    @Get
    public HttpResponse<Object> listServices() {
        try {
            return HttpResponse.ok(serviceFacade.list());
        } catch (ServiceNotFoundException e) {
            return HttpResponse.notFound(e.getMessage());
        } catch (Exception e) {
            return HttpResponse.serverError(e.getMessage());
        }
    }

    @Get("/{id}")
    public HttpResponse<Object> getServiceById(Long id) {
        try {
            return HttpResponse.ok(serviceFacade.get(id).get());
        } catch (ServiceNotFoundException e) {
            return HttpResponse.notFound(e.getMessage());
        } catch (Exception e) {
            return HttpResponse.serverError(e.getMessage());
        }
    }

    @Post
    public HttpResponse<Object> addService(@Body @Valid ServiceDTO service) {
        try {
            return HttpResponse.ok(serviceFacade.create(service));
        } catch (ServiceNotFoundException e) {
            return HttpResponse.notFound(e.getMessage());
        } catch (Exception e) {
            return HttpResponse.serverError(e.getMessage());
        }
    }

    @Patch("/{id}")
    public HttpResponse<Object> updateService(Long id, @Body @Valid ServiceDTO service) {
        try {
            return HttpResponse.ok(serviceFacade.update(id, service).get());
        } catch (ServiceNotFoundException e) {
            return HttpResponse.notFound(e.getMessage());
        } catch (Exception e) {
            return HttpResponse.serverError(e.getMessage());
        }
    }

    @Delete("/{id}")
    public HttpResponse<Object> deleteService(Long id) {
        try {
            return HttpResponse.ok(serviceFacade.delete(id).get());
        } catch (ServiceNotFoundException e) {
            return HttpResponse.notFound(e.getMessage());
        } catch (Exception e) {
            return HttpResponse.serverError(e.getMessage());
        }
    }
}