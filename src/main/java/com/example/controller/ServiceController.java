package com.example.controller;

import com.example.entity.Service;
import com.example.repository.ServiceRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import java.util.*;

@Controller("/services")
public class ServiceController {

    @Inject
    private ServiceRepository serviceRepository;

    @Get
    public HttpResponse<Collection<Service>> listServices() {
        return HttpResponse.ok(serviceRepository.findAll());
    }

    @Get("/{id}")
    public HttpResponse<Service> getServiceById(Long id) {
        if (serviceRepository.findById(id).isEmpty()) {
            return HttpResponse.notFound();
        }
        return HttpResponse.ok(serviceRepository.findById(id).get());
    }

    @Post
    public HttpResponse<Service> addService(@Body @Valid Service service) {
        return HttpResponse.ok(serviceRepository.save(service));
    }

    @Put("/{id}")
    public HttpResponse<Service> updateService(Long id, @Body @Valid Service service) {
        if (serviceRepository.findById(id).isEmpty()) {
            return HttpResponse.notFound();
        }
        Service existingService = serviceRepository.findById(id).get();
        existingService.setDescription(service.getDescription());
        existingService.setType(service.getType());
        existingService.setValue(service.getValue());
        existingService.setScheduled_for(service.getScheduled_for());
        existingService.setOpened_at(service.getOpened_at());
        serviceRepository.update(existingService);
        return HttpResponse.ok();
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