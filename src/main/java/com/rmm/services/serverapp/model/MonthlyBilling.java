package com.rmm.services.serverapp.model;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Represents the monthly billed amount for the customer' services.
 */
public class MonthlyBilling {
    /**
     * Represents the total bill amount.
     */
    private BigDecimal total;

    /**
     * Represents a summary description of the bill amount.
     */
    private Map<String, BigDecimal> summary;

    /**
     * Creates a new instance of {@link MonthlyBilling}.
     */
    public MonthlyBilling(BigDecimal total, Map<String, BigDecimal> summary) {
        this.total = total;
        this.summary = summary;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Map<String, BigDecimal> getSummary() {
        return summary;
    }

    public void setSummary(Map<String, BigDecimal> summary) {
        this.summary = summary;
    }
}
