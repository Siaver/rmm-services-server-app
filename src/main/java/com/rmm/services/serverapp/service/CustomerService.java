package com.rmm.services.serverapp.service;

import com.rmm.services.serverapp.exception.DomainException;
import com.rmm.services.serverapp.exception.ObjectNotFoundException;
import com.rmm.services.serverapp.model.Customer;
import com.rmm.services.serverapp.model.Service;

/**
 * Provides an abstraction for managing customer operations.
 */
public interface CustomerService {
    /**
     * Finds a customer by the given id.
     *
     * @param id The customer id.
     * @return A customer if it exists, otherwise and exception.
     */
    Customer findById(int id) throws ObjectNotFoundException;

    /**
     * Adds a service to the given customer id.
     *
     * @param customerId The customer id.
     * @param service    The service to be added.
     */
    void addService(int customerId, Service service) throws DomainException;

    /**
     * Removes a service from the given customer id.
     *
     * @param customerId The customer id.
     * @param service    The service to be removed.
     */
    void removeService(int customerId, Service service);
}
