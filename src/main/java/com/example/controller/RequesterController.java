package com.example.controller;

import com.example.entity.Requester;
import com.example.facade.RequesterFacade;
import io.micronaut.data.model.Pageable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Inject;
import jakarta.validation.Valid;

import javax.management.ServiceNotFoundException;
import java.util.List;

@Controller("/requesters")
public class RequesterController {

    @Inject
    private RequesterFacade requesterFacade;

    @Secured(SecurityRule.IS_ANONYMOUS)
    @Get
    public HttpResponse<Object> listRequesters(@Valid Pageable pageable) {
        try {
            return HttpResponse.ok(requesterFacade.list(pageable));
        } catch (ServiceNotFoundException e) {
            return HttpResponse.notFound(e.getMessage());
        } catch (Exception e) {
            return HttpResponse.serverError(e.getMessage());
        }
    }

    @Secured(SecurityRule.IS_ANONYMOUS)
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

    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Post
    public HttpResponse<Object> addRequester(@Body @Valid List<Requester> requesters) throws Exception {
        return HttpResponse.created(requesterFacade.create(requesters));
    }

    @Secured(SecurityRule.IS_AUTHENTICATED)
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

    @Secured(SecurityRule.IS_AUTHENTICATED)
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
