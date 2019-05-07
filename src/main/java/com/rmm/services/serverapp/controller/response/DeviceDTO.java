package com.rmm.services.serverapp.controller.response;

import com.rmm.services.serverapp.model.DeviceType;

public class DeviceDTO {
    private Long id;

    private String name;

    private DeviceType type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
