package com.rmm.services.serverapp.service;

import com.rmm.services.serverapp.exception.DomainException;
import com.rmm.services.serverapp.exception.ObjectNotFoundException;
import com.rmm.services.serverapp.model.Customer;
import com.rmm.services.serverapp.model.Device;
import com.rmm.services.serverapp.model.MonthlyBilling;
import com.rmm.services.serverapp.model.Service;
import com.rmm.services.serverapp.repository.CustomerRepository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.StreamSupport;

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

    /**
     * Calculates the monthly billing base on the customer's services.
     *
     * @param customerId The customer id.
     * @return A monthly service cost.
     */
    @Override
    public MonthlyBilling calculateMonthlyBilling(int customerId) {
        Customer customer = this.findById(customerId);
        Iterable<Device> devices = customer.getDevices();

        Map<String, BigDecimal> summary = new HashMap<>();
        BigDecimal devicesCost = StreamSupport.stream(devices.spliterator(), false)
                .map(Device::getDeviceCost)
                .reduce(BigDecimal::add)
                .orElse(new BigDecimal(0));

        summary.put("Devices", devicesCost);

        for (Service service : customer.getServices()) {
            for (Device device : devices) {
                BigDecimal costByDevice = service.getCostByDevice(device.getType()).getAmount();
                BigDecimal totalServiceCost = summary.getOrDefault(service.getName(), new BigDecimal(0)).add(costByDevice);

                summary.put(service.getName(), totalServiceCost);
            }
        }

        BigDecimal totalCost = summary.values().stream()
                .reduce(new BigDecimal(0), BigDecimal::add);


        return new MonthlyBilling(totalCost, summary);
    }
}
