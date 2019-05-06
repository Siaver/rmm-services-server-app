package com.rmm.services.serverapp.model;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Represents a device in the system.
 */
@Entity
@Table(name = "devices")
public class Device {
    /**
     * Device cost in dollars.
     */
    private static final BigDecimal COST = new BigDecimal(4.0);

    /**
     * Represents the unique identifier for this device.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "device_generator")
    @SequenceGenerator(name = "device_generator", sequenceName = "device_seq")
    private Long id;

    /**
     * Represents the name for this device.
     */
    @Column(nullable = false)
    private String name;

    /**
     * Represents the type of this device.
     */
    @Column
    private DeviceType type;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Customer customer;

    public Device() {
    }

    public Device(String name, DeviceType type, Customer customer) {
        this.name = name;
        this.type = type;
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DeviceType getType() {
        return type;
    }

    public void setType(DeviceType type) {
        this.type = type;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public BigDecimal getDeviceCost() {
        return COST;
    }
}
