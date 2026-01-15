package com.example;

import com.example.entity.Service;
import com.example.repository.ServiceRepository;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.Sort;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;
import jakarta.validation.Valid;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Controller("/services")
public class ServiceController {

    @Inject
    private ServiceRepository serviceRepository;

    public ServiceController() {
        UUID id = UUID.randomUUID();
    }

    @Get
    public Collection<Service> listServices() {
        return serviceRepository.findAll();
    }

    @Post
    public Service addService(@Body @Valid Service service) {
        return serviceRepository.save(service);
    }
}
