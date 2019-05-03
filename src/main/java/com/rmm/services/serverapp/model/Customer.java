package com.rmm.services.serverapp.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a customer in the system.
 */
@Entity
@Table(name = "customers")
public class Customer {
    /**
     * Represents the unique identifier for this customer.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_generator")
    @SequenceGenerator(name = "customer_generator", sequenceName = "customer_seq")
    private int id;

    /**
     * Represents a list of devices for this customer.
     */
    @OneToMany(mappedBy = "customer", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<Device> devices;

    /**
     * Represents a list of services for this customer.
     */
    @ManyToMany
    private Set<Service> services;

    /**
     * Creates a new instance of {@link Customer}.
     */
    public Customer() {
        this.devices = new HashSet<>();
        this.services = new HashSet<>();
    }

    public int getId() {
        return id;
    }

    /**
     * Returns a list of devices for this customer.
     *
     * @return A list of devices.
     */
    public Set<Device> getDevices() {
        return devices;
    }

    /**
     * Adds the given device to this customer.
     *
     * @param device The device to be added.
     */
    public void addDevice(Device device) {
        this.devices.add(device);
    }

    /**
     * Removes the given device for this customer.
     *
     * @param device The device to be removed.
     */
    public void removeDevice(Device device) {
        devices.remove(device);
    }

    /**
     * Returns a list of services for this customer.
     *
     * @return A list of services.
     */
    public Set<Service> getServices() {
        return services;
    }

    /**
     * Checks whether or not the customer has the given service.
     *
     * @param service The service.
     * @return A flag that indicates whether or not the user has the service.
     */
    public boolean hasService(Service service) {
        return services.contains(service);
    }

    /**
     * Adds the given service to this customer.
     *
     * @param service The service to be added.
     */
    public void addService(Service service) {
        this.services.add(service);
    }

    /**
     * Removes the given service for this customer.
     *
     * @param service The service to be removed.
     */
    public void removeService(Service service) {
        services.remove(service);
    }
}
