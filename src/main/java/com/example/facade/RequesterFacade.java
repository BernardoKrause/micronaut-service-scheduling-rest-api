package com.example.facade;

import com.example.entity.Requester;
import com.example.repository.RequesterRepository;
import com.example.repository.ServiceRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import javax.management.ServiceNotFoundException;
import java.util.List;
import java.util.Optional;

@Singleton
public class RequesterFacade {

	private final ServiceRepository serviceRepository;
	private final RequesterRepository requesterRepository;

	@Inject
	public RequesterFacade(ServiceRepository serviceRepository,
                           RequesterRepository requesterRepository) {
		this.serviceRepository = serviceRepository;
		this.requesterRepository = requesterRepository;
	}

	public Page<Requester> list(Pageable pageable) throws Exception{
		return requesterRepository.findAll( pageable );
	}

	public Optional<Requester> get(Long id) throws Exception {
        Optional<Requester> requester = requesterRepository.findById(id);

        if (requester.isEmpty()) {
            throw new ServiceNotFoundException("Requester not found: " + id);
        }

		return requester;
	}

	public List<Requester> create(List<Requester> requesters) throws Exception {
		return requesterRepository.saveAll(requesters);
	}

	public Optional<Requester> update(Long id, Requester modifiedRequester) throws Exception {
        Optional<Requester> existingRequester = requesterRepository.findById(id);

        if(existingRequester.isEmpty()) {
            throw new ServiceNotFoundException("Requester not found: " + id);
        }

        Requester existingService = existingRequester.get();

        if (modifiedRequester.getFullName() != null) {
            existingService.setFullName(modifiedRequester.getFullName());
        }

        if (modifiedRequester.getEmail() != null) {
            existingService.setEmail(modifiedRequester.getEmail());
        }

        if (modifiedRequester.getDepartment() != null) {
            existingService.setDepartment(modifiedRequester.getDepartment());
        }

        if( modifiedRequester.getUserName() != null) {
            existingService.setUserName(modifiedRequester.getUserName());
        }

        if (modifiedRequester.getPhoneNumber() != null) {
            existingService.setPhoneNumber(modifiedRequester.getPhoneNumber());
        }

        return Optional.of(requesterRepository.update(existingService));
	}

	public Optional<Requester> delete(Long id) throws Exception {
        Optional<Requester> requester = requesterRepository.findById(id);

        if (requester.isEmpty()) {
            throw new ServiceNotFoundException("Requester not found: " + id);
        }

        requesterRepository.delete(requester.get());

        return requester;
	}
}
