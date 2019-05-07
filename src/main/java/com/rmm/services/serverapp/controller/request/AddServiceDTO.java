package com.rmm.services.serverapp.controller.request;

import javax.validation.constraints.NotNull;

public class AddServiceDTO {
    @NotNull(message = "The service id is required")
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
