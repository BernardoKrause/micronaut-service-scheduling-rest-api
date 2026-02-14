package com.example.controller;

import com.example.dto.ServiceDTO;
import io.micronaut.data.model.Pageable;
import com.example.facade.ServiceFacade;
import io.micronaut.http.HttpResponse;
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
    public HttpResponse<Object> listServices(@Valid Pageable pageable) {
        try {
            return HttpResponse.ok(serviceFacade.list(pageable));
        } catch (ServiceNotFoundException e) {
            return HttpResponse.notFound(e.getMessage());
        } catch (Exception e) {
            return HttpResponse.serverError(e.getMessage());
        }
    }

    @Secured(SecurityRule.IS_ANONYMOUS)
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

    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Post
    public HttpResponse<Object> addService(@Body List<@Valid ServiceDTO> services) throws Exception {
        return HttpResponse.created(serviceFacade.create(services));
    }

    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Patch("/{id}")
    public HttpResponse<Object> updateService(Long id, @Body @Valid ServiceDTO service) {
        try {
            serviceFacade.update(id, service);
            return HttpResponse.noContent();
        } catch (ServiceNotFoundException e) {
            return HttpResponse.notFound(e.getMessage());
        } catch (Exception e) {
            return HttpResponse.serverError(e.getMessage());
        }
    }

    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Delete("/{id}")
    public HttpResponse<Object> deleteService(Long id) {
        try {
            serviceFacade.delete(id);
            return HttpResponse.noContent();
        } catch (ServiceNotFoundException e) {
            return HttpResponse.notFound(e.getMessage());
        } catch (Exception e) {
            return HttpResponse.serverError(e.getMessage());
        }
    }
}