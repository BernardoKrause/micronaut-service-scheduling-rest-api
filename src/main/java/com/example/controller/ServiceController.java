package com.example.controller;

import com.example.entity.Service;
import com.example.repository.ServiceRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
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

    @Post
    public HttpResponse<Service> addService(@Body @Valid Service service) {
        return HttpResponse.ok(serviceRepository.save(service));
    }
}
