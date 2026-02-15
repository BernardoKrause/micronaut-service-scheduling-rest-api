package com.example.controller;

import com.example.dto.filter.RequesterFiltersDTO;
import com.example.entity.Requester;
import com.example.entity.Service;
import com.example.facade.RequesterFacade;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Inject;
import jakarta.validation.Valid;

import java.util.List;

@Controller("/requesters")
public class RequesterController {

    @Inject
    private RequesterFacade requesterFacade;

    @Secured(SecurityRule.IS_ANONYMOUS)
    @Get
    public HttpResponse<Object> listRequesters(@Valid RequesterFiltersDTO pageable) throws Exception {
        return HttpResponse.ok(requesterFacade.list(pageable));
    }

    @Secured(SecurityRule.IS_ANONYMOUS)
    @Get("/{id}")
    public HttpResponse<Requester> getRequesterById(Long id) throws Exception {
        return requesterFacade.get(id)
                .map(HttpResponse::ok)
                .orElse(HttpResponse.notFound());
    }

    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Post
    public HttpResponse<Object> addRequester(@Body @Valid List<Requester> requesters) throws Exception {
        return HttpResponse.created(requesterFacade.create(requesters));
    }

    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Patch("/{id}")
    public HttpResponse<Requester> patchRequester(Long id, @Body Requester requester) throws Exception {
            return requesterFacade.update(id, requester)
                    .map(HttpResponse::ok)
                    .orElse(HttpResponse.notFound());
    }

    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Delete("/{id}")
    public HttpResponse<Requester> deleteRequester(Long id) throws Exception {
        return requesterFacade.delete(id)
                .map(HttpResponse::ok)
                .orElse(HttpResponse.notFound());
    }

    @Secured(SecurityRule.IS_ANONYMOUS)
    @Get("/{id}/services")
    public HttpResponse<Page<Service>> listServicesByRequester(Long id, Pageable pageable) throws Exception {
        return requesterFacade.listServicesByRequester(id, pageable)
            .map(HttpResponse::ok)
            .orElse(HttpResponse.notFound());
    }
}
