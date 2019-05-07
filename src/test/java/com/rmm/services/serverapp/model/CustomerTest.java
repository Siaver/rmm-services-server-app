package com.rmm.services.serverapp.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerTest {
    @Test
    public void should_create_customer() {
        // Arrange

        // Act
        Customer customer = new Customer();

        // Assert
        assertNotNull(customer);
    }

    @Test
    public void should_add_device() {
        // Arrange
        Customer customer = new Customer();
        Device device = new Device("MacBook", DeviceType.MAC, customer);

        // Act
        customer.addDevice(device);

        // Assert
        assertTrue(customer.getDevices().size() > 0);
    }

    @Test
    public void should_remove_device() {
        // Arrange
        Customer customer = new Customer();
        Device device = new Device("MacBook", DeviceType.MAC, customer);
        customer.addDevice(device);

        // Act
        customer.removeDevice(device);

        // Assert
        assertEquals(0, customer.getDevices().size());
    }

    @Test
    public void should_add_service() {
        // Arrange
        Customer customer = new Customer();
        Service service = new Service("Audits");

        // Act
        customer.addService(service);

        // Assert
        assertTrue(customer.getServices().size() > 0);
    }

    @Test
    public void should_remove_service() {
        // Arrange
        Customer customer = new Customer();
        Service service = new Service("Audits");
        customer.addService(service);

        // Act
        customer.removeService(service);

        // Assert
        assertEquals(0, customer.getServices().size());
    }

    @Test
    public void should_has_service() {
        // Arrange
        Customer customer = new Customer();
        Service service = new Service("Audits");
        customer.addService(service);

        // Act
        boolean result = customer.hasService(service);

        // Assert
        assertTrue(result);
    }
}
