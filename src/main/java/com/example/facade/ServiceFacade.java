package com.example.facade;

import com.example.dto.ServiceDTO;
import com.example.entity.Requester;
import com.example.entity.Service;
import com.example.repository.RequesterRepository;
import com.example.repository.ServiceRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import javax.management.ServiceNotFoundException;
import java.util.List;
import java.util.Optional;

@Singleton
public class ServiceFacade {

	private final ServiceRepository serviceRepository;
	private final RequesterRepository requesterRepository;

	@Inject
	public ServiceFacade(ServiceRepository serviceRepository,
						 RequesterRepository requesterRepository) {
		this.serviceRepository = serviceRepository;
		this.requesterRepository = requesterRepository;
	}

	public List<Service> list() throws Exception{
		return serviceRepository.findAll();
	}

	public Optional<Service> get(Long id) throws Exception {
        Optional<Service> service = serviceRepository.findById(id);

        if (service.isEmpty()) {
            throw new ServiceNotFoundException("Service not found: " + id);
        }

		return serviceRepository.findById(id);
	}

	public Service create(ServiceDTO dto) throws Exception {
		Optional<Requester> requester = requesterRepository.findById(dto.getRequesterId());

        if(requester.isEmpty()) {
            throw new ServiceNotFoundException("Requester not found: " + dto.getRequesterId());
        }

		Service service = new Service();
		service.setDescription(dto.getDescription());
		service.setType(dto.getType());
		service.setValue(dto.getValue());
		service.setScheduled_for(dto.getScheduled_for());
		service.setOpened_at(dto.getOpened_at());
		service.setRequester(requester.get());

		return serviceRepository.save(service);
	}

	public Optional<Service> update(Long id, ServiceDTO dto) throws Exception {
        Optional<Service> service = serviceRepository.findById(id);
        Optional<Requester> requester = requesterRepository.findById(dto.getRequesterId());

        if (service.isEmpty()) {
            throw new ServiceNotFoundException("Service not found: " + id);
        }

        if(requester.isEmpty()) {
            throw new ServiceNotFoundException("Requester not found: " + dto.getRequesterId());
        }

        Service existingService = service.get();

        if(dto.getDescription() != null) {
            existingService.setDescription(dto.getDescription());
        }

        if(dto.getValue() != null) {
            existingService.setValue(dto.getValue());
        }

        if(dto.getType() != null) {
            existingService.setType(dto.getType());
        }

        if(dto.getScheduled_for() != null) {
            existingService.setScheduled_for(dto.getScheduled_for());
        }

        if(dto.getOpened_at() != null) {
            existingService.setOpened_at(dto.getOpened_at());
        }

        return Optional.of(serviceRepository.update(existingService));
	}

	public Optional<Service> delete(Long id) throws Exception {
        Optional<Service> service = serviceRepository.findById(id);

        if (service.isEmpty()) {
            throw new ServiceNotFoundException("Service not found: " + id);
        }

        serviceRepository.delete(service.get());

        return service;
	}
}
