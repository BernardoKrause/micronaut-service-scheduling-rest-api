package com.example;

import io.micronaut.http.annotation.*;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Controller("/services")
public class ServiceController {

    private final Map<UUID, Service> serviceStore = new ConcurrentHashMap<>();

    public ServiceController() {
        UUID id = UUID.randomUUID();
        serviceStore.put(id, new Service(id, "agendamento", "agendamento de algum serviço", 3499.99, new Date()));
    }

    @Get
    public Collection<Service> listServices() {
        return serviceStore.values();
    }

    @Get("/{id}")
    public Service listService(UUID id) {
        return serviceStore.get(id);
    }

    @Post
    public Service createService(Service service) {
//        if (service.getId() == null) {
//            service.setId(UUID.randomUUID());
//        }
//
//        serviceStore.put(service.getId(), service);
        return service;
    }

    @Patch("/{id}")
    public Service updateService(Service service) {
        return service;
    }

    @Delete("/{id}")
    public Service deleteService(UUID id) {
        return serviceStore.remove(id);
    }
}
