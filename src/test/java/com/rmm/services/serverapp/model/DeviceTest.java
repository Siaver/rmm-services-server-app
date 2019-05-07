package com.rmm.services.serverapp.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DeviceTest {
    @Test
    public void should_create_a_device() {
        // Arrange
        final String name = "Macbook Pro";

        // Act
        Device device = new Device(name, DeviceType.MAC, new Customer());

        // Assert
        assertEquals(name, device.getName());
        assertEquals(DeviceType.MAC, device.getType());
    }
}
