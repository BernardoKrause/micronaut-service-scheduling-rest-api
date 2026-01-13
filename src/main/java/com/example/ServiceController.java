package com.example;

import com.example.repository.ServiceRepository;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;
import com.example.entity.Service;
import jakarta.validation.Valid;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Controller("/services")
public class ServiceController {

    @Inject
    private ServiceRepository serviceRepository;

    @Get
    public List<Service> listServices() {
        return serviceRepository.findAll();
    }

    @Post
    public Service addService(@Body @Valid Service service) {
        return serviceRepository.save(service);
    }
}
