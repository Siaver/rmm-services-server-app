package com.rmm.services.serverapp.service;

import com.rmm.services.serverapp.exception.ObjectNotFoundException;
import com.rmm.services.serverapp.model.Device;
import com.rmm.services.serverapp.model.DeviceType;

/**
 * Provides an abstraction for managing device operations.
 */
public interface DeviceService {
    /**
     * Finds a device by the given id.
     *
     * @param id The device id.
     * @return The found device, otherwise throws a {@link ObjectNotFoundException}.
     */
    Device findById(Long id) throws ObjectNotFoundException;

    /**
     * Creates a new device record.
     *
     * @param customerId The customer id.
     * @param name       The name of the device.
     * @param type       The type of the device.
     * @return The created device.
     */
    Device create(int customerId, String name, DeviceType type);

    /**
     * Updates an existing device.
     *
     * @param device The device to be updated.
     * @return The updated device.
     */
    Device update(Device device);

    /**
     * Deletes an existing device by the given id.
     *
     * @param id The device id.
     */
    void delete(Long id);
}
