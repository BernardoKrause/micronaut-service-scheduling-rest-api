package com.example.controller;

import com.example.entity.Requester;
import com.example.repository.RequesterRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import java.util.Collection;
import java.util.List;

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller("/requesters")
public class RequesterController {

    @Inject
    private RequesterRepository requesterRepository;

    @Get
    public HttpResponse<Page<Requester>> listRequesters(@Valid Pageable pageable) {
        Page<Requester> page = requesterRepository.findAll(pageable);
        return HttpResponse.ok(page);
    }

    @Get("/{id}")
    public HttpResponse<Requester> getRequesterById(Long id) {
        if (requesterRepository.findById(id).isEmpty()) {
            return HttpResponse.notFound();
        }
        return HttpResponse.ok(requesterRepository.findById(id).get());
    }

    @Post
    public HttpResponse<List<Requester>> createRequester(@Body @Valid List<Requester> requesters) {
        List<Requester> saved = requesterRepository.saveAll(requesters);

        return HttpResponse.created(saved);
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
