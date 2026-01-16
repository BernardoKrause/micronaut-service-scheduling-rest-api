package com.example;

import com.example.entity.Service;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;

import java.util.Collection;

@Client("/services")
public interface ServiceClient {

    @Get
    Collection<Service> listServices();
}
