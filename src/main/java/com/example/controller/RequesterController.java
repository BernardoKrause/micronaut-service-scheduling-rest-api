package com.example.controller;

import com.example.entity.Requester;
import com.example.repository.RequesterRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import java.util.Collection;
import java.util.List;

@Controller("/requesters")
public class RequesterController {

    @Inject
    private RequesterRepository requesterRepository;

    @Get
    public HttpResponse<Collection<Requester>> listRequesters() {
        return HttpResponse.ok(requesterRepository.findAll());
    }

    @Get("/{id}")
    public HttpResponse<Requester> getRequesterById(Long id) {
        if (requesterRepository.findById(id).isEmpty()) {
            return HttpResponse.notFound();
        }
        return HttpResponse.ok(requesterRepository.findById(id).get());
    }

    @Post
    public HttpResponse<Requester> createRequester(@Body @Valid Requester requester) {
        requesterRepository.save(requester);
        return HttpResponse.created(requester);
    }

    @Patch("/{id}")
    public HttpResponse<Requester> patchRequester(Long id, @Body Requester requester) {
        if (requesterRepository.findById(id).isEmpty()) {
            return HttpResponse.notFound();
        }

        Requester existingRequester = requesterRepository.findById(id).get();

        if (requester.getName() != null) {
            existingRequester.setName(requester.getName());
        }
        if (requester.getEmail() != null) {
            existingRequester.setEmail(requester.getEmail());
        }

        requesterRepository.update(existingRequester);
        return HttpResponse.ok(existingRequester);
    }

    @Put("/{id}")
    public HttpResponse<Requester> updateRequester(Long id, @Body @Valid Requester requester) {
        if (requesterRepository.findById(id).isEmpty()) {
            return HttpResponse.notFound();
        }
        Requester existingRequester = requesterRepository.findById(id).get();
        existingRequester.setName(requester.getName());
        existingRequester.setEmail(requester.getEmail());
        requesterRepository.update(existingRequester);
        return HttpResponse.ok();
    }

    @Delete("/{id}")
    public HttpResponse<Requester> deleteRequester(Long id) {
        if (requesterRepository.findById(id).isEmpty()) {
            return HttpResponse.notFound();
        }
        var existingRequester = requesterRepository.findById(id).get();
        requesterRepository.delete(existingRequester);
        return HttpResponse.ok(existingRequester);
    }
}
