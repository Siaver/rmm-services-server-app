package com.rmm.services.serverapp.service;

import com.rmm.services.serverapp.exception.DomainException;
import com.rmm.services.serverapp.exception.ObjectNotFoundException;
import com.rmm.services.serverapp.model.Customer;
import com.rmm.services.serverapp.model.Service;
import com.rmm.services.serverapp.repository.CustomerRepository;

/**
 * Default implementation if {@link CustomerService}.
 */
@org.springframework.stereotype.Service
public class DefaultCustomerService implements CustomerService {
    private final CustomerRepository customerRepository;

    public DefaultCustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Finds a customer by the given id.
     *
     * @param id The customer id.
     * @return A customer if it exists, otherwise and exception.
     */
    @Override
    public Customer findById(int id) throws ObjectNotFoundException {
        return this.customerRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("There is no customer with id: %s", id)));

    }

    /**
     * Adds a service to the given customer id.
     *
     * @param customerId The customer id.
     * @param service    The service to be added.
     */
    @Override
    public void addService(int customerId, Service service) throws DomainException {
        Customer customer = this.findById(customerId);

        if (customer.hasService(service)) {
            throw new DomainException(String.format("Customer %s already has the service %s", customer.getId(), service.getId()));
        }

        customer.addService(service);
        this.customerRepository.save(customer);
    }

    /**
     * Removes a service from the given customer id.
     *
     * @param customerId The customer id.
     * @param service    The service to be removed.
     */
    @Override
    public void removeService(int customerId, Service service) {
        Customer customer = this.findById(customerId);

        if (customer.hasService(service)) {
            customer.removeService(service);
            this.customerRepository.save(customer);
        }
    }
}
