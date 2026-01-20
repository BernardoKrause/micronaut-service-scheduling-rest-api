package com.example.controller;

import com.example.entity.Service;
import com.example.repository.ServiceRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import java.util.*;

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller("/services")
public class ServiceController {

    @Inject
    private ServiceRepository serviceRepository;

    @Get
    public HttpResponse<Page<Service>> listServices(@Valid Pageable pageable) {
        Page<Service> page = serviceRepository.findAll(pageable);
        return HttpResponse.ok(page);
    }

    @Get("/{id}")
    public HttpResponse<Service> getServiceById(Long id) {
        if (serviceRepository.findById(id).isEmpty()) {
            return HttpResponse.notFound();
        }
        return HttpResponse.ok(serviceRepository.findById(id).get());
    }

    @Post
    public HttpResponse<List<Service>> addService(@Body @Valid List<Service> services) {
        List<Service> saved = serviceRepository.saveAll(services);
        return HttpResponse.created(saved);
    }

    @Patch("/{id}")
    public HttpResponse<Service> updateService(Long id, @Body @Valid Service service) {
        return serviceRepository.findById(id).map(existingService -> {
            existingService.setDescription(service.getDescription());
            existingService.setType(service.getType());
            existingService.setValue(service.getValue());
            existingService.setScheduled_for(service.getScheduled_for());
            existingService.setOpened_at(service.getOpened_at());
            serviceRepository.update(existingService);

            return HttpResponse.ok(existingService);
        }).orElse(HttpResponse.notFound());
    }

    @Delete("/{id}")
    public HttpResponse<Service> deleteService(Long id) {
        if (serviceRepository.findById(id).isEmpty()) {
            return HttpResponse.notFound();
        }
        var existingService = serviceRepository.findById(id).get();
        serviceRepository.delete(existingService);
        return HttpResponse.ok(existingService);
    }
}