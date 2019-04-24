package com.rmm.services.serverapp.service;

import com.rmm.services.serverapp.model.Device;
import com.rmm.services.serverapp.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Provides an abstraction for managing device operations.
 */
@Service
public interface DeviceService {
    /**
     * Finds a device by the given id.
     *
     * @param id The device id.
     * @return The found device, otherwise throws a {@link ObjectNotFoundException}.
     */
    Device findById(int id) throws ObjectNotFoundException;

    /**
     * Creates a new device record.
     *
     * @param device The device to be created.
     * @return The created device.
     */
    Device create(Device device);

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
    void delete(int id);
}
