package com.example.controller;

import com.example.entity.Requester;
import com.example.facade.RequesterFacade;
import com.example.repository.RequesterRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Inject;
import jakarta.validation.Valid;

import javax.management.ServiceNotFoundException;
import java.util.Collection;

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller("/requesters")
public class RequesterController {

    @Inject
    private RequesterFacade requesterFacade;

    @Get
    public HttpResponse<Object> listRequesters() {
        try {
            return HttpResponse.ok(requesterFacade.list());
        } catch (ServiceNotFoundException e) {
            return HttpResponse.notFound(e.getMessage());
        } catch (Exception e) {
            return HttpResponse.serverError(e.getMessage());
        }
    }

    @Get("/{id}")
    public HttpResponse<Object> getRequesterById(Long id) {
        try {
            return HttpResponse.ok(requesterFacade.get(id).get());
        } catch (ServiceNotFoundException e) {
            return HttpResponse.notFound(e.getMessage());
        } catch (Exception e) {
            return HttpResponse.serverError(e.getMessage());
        }
    }

    @Post
    public HttpResponse<Object> createRequester(@Body @Valid Requester requester) {
        try {
            return HttpResponse.ok(requesterFacade.create(requester));
        } catch (ServiceNotFoundException e) {
            return HttpResponse.notFound(e.getMessage());
        } catch (Exception e) {
            return HttpResponse.serverError(e.getMessage());
        }
    }

    @Patch("/{id}")
    public HttpResponse<Object> patchRequester(Long id, @Body Requester requester) {
        try {
            requesterFacade.update(id, requester);
            return HttpResponse.noContent();
        } catch (ServiceNotFoundException e) {
            return HttpResponse.notFound(e.getMessage());
        } catch (Exception e) {
            return HttpResponse.serverError(e.getMessage());
        }
    }

    @Delete("/{id}")
    public HttpResponse<Object> deleteRequester(Long id) {
        try {
            requesterFacade.delete(id);
            return HttpResponse.noContent();
        } catch (ServiceNotFoundException e) {
            return HttpResponse.notFound(e.getMessage());
        } catch (Exception e) {
            return HttpResponse.serverError(e.getMessage());
        }
    }
}
