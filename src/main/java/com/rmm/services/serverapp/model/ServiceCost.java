package com.rmm.services.serverapp.model;

import javax.persistence.*;

/**
 * Represents a service cost in the system.
 */
@Entity
@Table(name = "service_costs")
public class ServiceCost {
    /**
     * Represents the unique identifier for this amount.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "service_cost_generator")
    @SequenceGenerator(name = "service_cost_generator", sequenceName = "service_cost_seq")
    private int id;

    /**
     * Represents the type of the device.
     */
    @Column(name = "device_type")
    private DeviceType deviceType;

    /**
     * Represents the amount value in dollars.
     */
    @Column
    private Double amount;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;

    /**
     * Creates a new instance of {@link ServiceCost}.
     */
    public ServiceCost() {
    }

    /**
     * Creates a new instance of {@link ServiceCost}.
     */
    public ServiceCost(Service service, DeviceType deviceType, Double amount) {
        this.service = service;
        this.deviceType = deviceType;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public Double getAmount() {
        return amount;
    }

    public Service getService() {
        return service;
    }
}
