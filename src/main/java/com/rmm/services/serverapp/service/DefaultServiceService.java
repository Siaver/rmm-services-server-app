package com.rmm.services.serverapp.service;

import com.rmm.services.serverapp.exception.ObjectNotFoundException;
import com.rmm.services.serverapp.model.Service;
import com.rmm.services.serverapp.repository.ServiceRepository;


/**
 * Default implementation of {@link ServiceService}.
 */
@org.springframework.stereotype.Service
public class DefaultServiceService implements ServiceService {
    private final ServiceRepository serviceRepository;

    public DefaultServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    /**
     * Retrieves a list of available services.
     *
     * @return A list of services.
     */
    @Override
    public Iterable<Service> getAll() {
        return serviceRepository.findAll();
    }

    /**
     * Finds a service by the given id.
     *
     * @param id The service id.
     * @return A customer if it exists, otherwise and exception.
     */
    @Override
    public Service findById(int id) throws ObjectNotFoundException {
        return null;
    }
}
