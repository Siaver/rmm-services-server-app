package com.rmm.services.serverapp.service;

import com.rmm.services.serverapp.exception.ObjectNotFoundException;
import com.rmm.services.serverapp.model.Service;

/**
 * Provides an abstraction for managing service operations.
 */
public interface ServiceService {
    /**
     * Retrieves a list of available services.
     *
     * @return A list of services.
     */
    Iterable<Service> getAll();

    /**
     * Finds a service by the given id.
     *
     * @param id The service id.
     * @return A customer if it exists, otherwise and exception.
     */
    Service findById(int id) throws ObjectNotFoundException;
}
