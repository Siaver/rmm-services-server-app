package com.rmm.services.serverapp.controler.response;

import com.rmm.services.serverapp.model.ServiceCost;

import java.util.HashMap;
import java.util.Map;

public class ServiceDTO {
    private int id;

    private String name;

    private Map<String, Double> cost;

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

    public Map<String, Double> getCost() {
        return cost;
    }

    private Map<String, Double> buildCost(Iterable<ServiceCost> costValues) {
        Map<String, Double> costs = new HashMap<>();

        for (ServiceCost cost : costValues) {
            costs.put(cost.getDeviceType().name(), cost.getAmount());
        }

        return costs;
    }
}
