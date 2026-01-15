package com.example;

import io.micronaut.http.annotation.*;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Controller("/requesters")
public class RequesterController {

    private final Map<UUID, Requester> requesterStore = new ConcurrentHashMap<>();

    @Get
    public Collection<Requester> listRequesters() {
        return requesterStore.values();
    }

    @Get("/{id}")
    public Requester listRequester(UUID id) {
        return requesterStore.get(id);
    }

    @Post
    public Requester createRequester(Requester requester) {
        return requester;
    }

    @Patch("/{id}")
    public Requester updateRequester(Requester requester) {
        return requester;
    }

    @Delete("/{id}")
    public Requester deleteRequester(UUID id) {
        return requesterStore.remove(id);
    }
}
