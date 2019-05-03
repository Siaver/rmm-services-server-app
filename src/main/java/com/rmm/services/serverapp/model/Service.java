package com.rmm.services.serverapp.model;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a service in the system.
 */
@Entity
@Table(name = "services")
public class Service {
    /**
     * Represents the unique identifier for this service.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "service_generator")
    @SequenceGenerator(name = "service_generator", sequenceName = "service_seq")
    private int id;

    /**
     * Represent the name for this service.
     */
    @Column(nullable = false)
    private String name;

    /**
     * Represents the cost for this service.
     */
    @OneToMany(mappedBy = "service", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @MapKey(name = "deviceType")
    private Map<DeviceType, ServiceCost> cost;

    /**
     * Creates a new instance of {@link Service}.
     */
    public Service() {
        this.cost = new HashMap<>();
    }

    /**
     * Creates a new instance of {@link Service}.
     *
     * @param name The name for the service.
     */
    public Service(String name) {
        this();
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Iterable<ServiceCost> getCosts() {
        return cost.values();
    }

    public ServiceCost getCostByDevice(DeviceType deviceType) {
        return cost.get(deviceType);
    }

    public void addCost(ServiceCost cost) {
        this.cost.put(cost.getDeviceType(), cost);
    }
}
