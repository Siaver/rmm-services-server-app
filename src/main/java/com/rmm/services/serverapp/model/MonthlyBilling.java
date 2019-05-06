package com.rmm.services.serverapp.model;

import java.util.Map;

/**
 * Represents the monthly billed amount for the customer' services.
 */
public class MonthlyBilling {
    /**
     * Represents the total bill amount.
     */
    private Double total;

    /**
     * Represents a summary description of the bill amount.
     */
    private Map<String, Double> summary;

    /**
     * Creates a new instance of {@link MonthlyBilling}.
     */
    public MonthlyBilling(Double total, Map<String, Double> summary) {
        this.total = total;
        this.summary = summary;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Map<String, Double> getSummary() {
        return summary;
    }

    public void setSummary(Map<String, Double> summary) {
        this.summary = summary;
    }
}
