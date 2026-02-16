package com.example.facade;

import com.example.dto.ServiceDTO;
import com.example.dto.filter.ServiceFiltersDTO;
import com.example.entity.Requester;
import com.example.entity.Service;
import com.example.repository.RequesterRepository;
import com.example.repository.ServiceRepository;
import io.micronaut.data.model.Page;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import javax.management.ServiceNotFoundException;
import java.util.ArrayList;
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

	public Page<Service> list(ServiceFiltersDTO pageable) throws Exception{
		return serviceRepository.FindWithFilters(
            pageable.getDescription(),
            pageable.getType(),
            pageable.getValue(),
            pageable.getScheduledFor(),
            pageable.getOpenedAt(),
            pageable
        );
	}

	public Optional<Service> get(Long id) throws Exception {
        Optional<Service> service = serviceRepository.findById(id);

        if (service.isEmpty()) {
            throw new ServiceNotFoundException("Service not found: " + id);
        }

		return serviceRepository.findById(id);
	}

	public List<Service> create(List<ServiceDTO> dtos) throws Exception {
        Optional<Requester> requester;
        Service service;
        List<Service> listServices = new ArrayList<>();

        for(ServiceDTO dto : dtos){
            requester = requesterRepository.findById(dto.getRequesterId());

            if(requester.isEmpty()) {
                throw new ServiceNotFoundException("Requester not found: " + dto.getRequesterId());
            }

            service = new Service();
            service.setDescription(dto.getDescription());
            service.setType(dto.getType());
            service.setValue(dto.getValue());
            service.setScheduledFor(dto.getScheduled_for());
            service.setOpenedAt(dto.getOpenedAt());
            service.setRequester(requester.get());
            listServices.add(service);
        }

		return serviceRepository.saveAll(listServices);
	}

	public Optional<Service> update(Long id, ServiceDTO dto) throws Exception {
        Optional<Service> service = serviceRepository.findById(id);

        if (service.isEmpty()) {
            throw new ServiceNotFoundException("Service not found: " + id);
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
            existingService.setScheduledFor(dto.getScheduled_for());
        }

        if(dto.getOpenedAt() != null) {
            existingService.setOpenedAt(dto.getOpenedAt());
        }

        if(dto.getRequesterId() != null) {
            Optional<Requester> requester = requesterRepository.findById(dto.getRequesterId());

            if(requester.isEmpty()) {
                throw new ServiceNotFoundException("Requester not found: " + dto.getRequesterId());
            }

            existingService.setRequester(requester.get());
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
