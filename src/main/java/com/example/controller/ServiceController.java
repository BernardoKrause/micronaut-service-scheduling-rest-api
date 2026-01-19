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
    public HttpResponse<Collection<Service>> listServices() {
        try {
            return HttpResponse.ok(serviceFacade.list());
        } catch (ServiceNotFoundException e) {
            return HttpResponse.notFound();
        } catch (Exception e) {
            return HttpResponse.serverError();
        }
    }

    @Get("/{id}")
    public HttpResponse<Service> getServiceById(Long id) {
        try {
            return HttpResponse.ok(serviceFacade.get(id).get());
        } catch (ServiceNotFoundException e) {
            return HttpResponse.notFound();
        } catch (Exception e) {
            return HttpResponse.serverError();
        }
    }

    @Post
    public HttpResponse<Service> addService(@Body @Valid ServiceDTO service) {
        try {
            return HttpResponse.ok(serviceFacade.create(service));
        } catch (ServiceNotFoundException e) {
            return HttpResponse.notFound();
        } catch (Exception e) {
            return HttpResponse.serverError();
        }
    }

    @Patch("/{id}")
    public HttpResponse<Service> updateService(Long id, @Body @Valid ServiceDTO service) {
        try {
            return HttpResponse.ok(serviceFacade.update(id, service).get());
        } catch (ServiceNotFoundException e) {
            return HttpResponse.notFound();
        } catch (Exception e) {
            return HttpResponse.serverError();
        }
    }

    @Delete("/{id}")
    public HttpResponse<Service> deleteService(Long id) {
        try {
            return HttpResponse.ok(serviceFacade.delete(id).get());
        } catch (ServiceNotFoundException e) {
            return HttpResponse.notFound();
        } catch (Exception e) {
            return HttpResponse.serverError();
        }
    }
}