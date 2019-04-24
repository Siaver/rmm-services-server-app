package com.rmm.services.serverapp.controler.request;

import com.rmm.services.serverapp.model.DeviceType;

import javax.validation.constraints.NotBlank;

public class CreateOrUpdateDeviceDTO {
    @NotBlank(message = "The name field is required")
    private String name;

    private DeviceType type;

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
