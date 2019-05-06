package com.rmm.services.serverapp.controler.response;

import com.rmm.services.serverapp.model.ServiceCost;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ServiceDTO {
    private int id;

    private String name;

    private Map<String, BigDecimal> cost;

    public ServiceDTO(int id, String name, Iterable<ServiceCost> costValues) {
        this.id = id;
        this.name = name;
        this.cost = this.buildCost(costValues);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Map<String, BigDecimal> getCost() {
        return cost;
    }

    private Map<String, BigDecimal> buildCost(Iterable<ServiceCost> costValues) {
        Map<String, BigDecimal> costs = new HashMap<>();

        for (ServiceCost cost : costValues) {
            costs.put(cost.getDeviceType().name(), cost.getAmount());
        }

        return costs;
    }
}
