package com.rmm.services.serverapp.model;

import javax.persistence.*;

/**
 * Represents a device in the system.
 */
@Entity
@Table(name = "devices")
public class Device {
    /**
     * Represents the unique identifier for this device.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    /**
     * Represents the name for this device.
     */
    private String name;

    /**
     * Represents the type of this device.
     */
    private DeviceType type;

    public Device() {
    }

    public Device(String name, DeviceType type) {
        this.name = name;
        this.type = type;
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

    public DeviceType getType() {
        return type;
    }

    public void setType(DeviceType type) {
        this.type = type;
    }
}
