package com.example.controller;

import com.example.dto.ServiceDTO;
import com.example.dto.filter.ServiceFiltersDTO;
import com.example.entity.Service;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import com.example.facade.ServiceFacade;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Inject;
import jakarta.validation.Valid;

import javax.management.ServiceNotFoundException;
import java.util.*;

@Valid
@Controller("/services")
public class ServiceController {

    @Inject
    private ServiceFacade serviceFacade;

    @Secured(SecurityRule.IS_ANONYMOUS)
    @Get
    public HttpResponse<Object> listServices(@Valid ServiceFiltersDTO pageable) throws Exception {
        return HttpResponse.ok(serviceFacade.list(pageable));
    }

    @Secured(SecurityRule.IS_ANONYMOUS)
    @Get("/{id}")
    public HttpResponse<Service> getServiceById(Long id) throws Exception {
        return serviceFacade.get(id)
                .map(HttpResponse::ok)
                .orElse(HttpResponse.notFound());
    }

    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Post
    public HttpResponse<Object> addService(@Body List<@Valid ServiceDTO> services) throws Exception {
        return HttpResponse.created(serviceFacade.create(services));
    }

    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Patch("/{id}")
    public HttpResponse<Service> patchService(@PathVariable Long id, @Body ServiceDTO service) throws Exception {
        return serviceFacade.update(id, service)
                .map(HttpResponse::ok)
                .orElse(HttpResponse.notFound());
    }

    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Delete("/{id}")
    public HttpResponse<Service> deleteService(@PathVariable Long id) throws Exception {
        return serviceFacade.delete(id)
                .map(HttpResponse::ok)
                .orElse(HttpResponse.notFound());
    }
}