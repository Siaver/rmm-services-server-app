package com.rmm.services.serverapp.service;

import com.rmm.services.serverapp.exception.ObjectNotFoundException;
import com.rmm.services.serverapp.model.Customer;
import com.rmm.services.serverapp.model.Device;
import com.rmm.services.serverapp.model.DeviceType;
import com.rmm.services.serverapp.repository.DeviceRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DeviceServiceTest {
    private final Long defaultDeviceId = 1L;
    private final String defaultDeviceName = "Macbook Pro";

    @Mock
    private DeviceRepository deviceRepository;
    @Mock
    private CustomerService customerService;

    private DeviceService deviceService;

    @Before
    public void setUp() {
        deviceService = new DefaultDeviceService(deviceRepository, customerService);

        Device defaultDevice = new Device();
        defaultDevice.setName(defaultDeviceName);

        when(deviceRepository.findById(defaultDeviceId))
                .thenReturn(Optional.of(defaultDevice));

        when(customerService.findById(any(Integer.class)))
                .thenReturn(new Customer());
    }

    @Test
    public void should_return_device_by_id() {
        // Arrange

        // Act
        Device device = deviceService.findById(defaultDeviceId);

        // Assert
        assertEquals(device.getName(), defaultDeviceName);
        verify(deviceRepository, times(1)).findById(defaultDeviceId);
    }

    @Test(expected = ObjectNotFoundException.class)
    public void should_return_exception_when_device_not_found() {
        // Arrange
        final Long deviceId = 2L;

        // Act
        deviceService.findById(deviceId);

        // Assert
        verify(deviceRepository, times(1)).findById(deviceId);
    }

    @Test
    public void should_create_device() {
        // Arrange
        Device device = new Device("PC", DeviceType.WINDOWS_SERVER, new Customer());
        when(deviceRepository.save(any(Device.class)))
                .thenReturn(device);

        // Act
        Device createdDevice = deviceService.create(1, device.getName(), device.getType());

        // Assert
        assertEquals(device.getName(), createdDevice.getName());
        verify(customerService, times(1)).findById(any(Integer.class));
        verify(deviceRepository, times(1)).save(any(Device.class));
    }

    @Test
    public void should_update_device() {
        // Arrange
        Device device = new Device("Macbook Air", DeviceType.MAC, new Customer());
        when(deviceRepository.save(any(Device.class)))
                .thenReturn(device);

        // Act
        Device updatedDevice = deviceService.update(device);

        // Assert
        assertEquals(device.getName(), updatedDevice.getName());
        verify(deviceRepository, times(1)).save(any(Device.class));
    }

    @Test
    public void should_delete_device() {
        // Arrange

        // Act
        deviceService.delete(1L);

        // Assert
        verify(deviceRepository, times(1)).deleteById(any(Long.class));
    }
}
