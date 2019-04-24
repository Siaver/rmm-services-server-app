package com.rmm.services.serverapp.service;

import com.rmm.services.serverapp.exception.ObjectNotFoundException;
import com.rmm.services.serverapp.model.Device;
import com.rmm.services.serverapp.repository.DeviceRepository;
import org.springframework.stereotype.Component;

/**
 * Default implementation of {@link DeviceService}'
 */
@Component
public class DefaultDeviceService implements DeviceService {
    private final DeviceRepository deviceRepository;

    public DefaultDeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    /**
     * Finds a device by the given id.
     *
     * @param id The device id.
     * @return The found device, otherwise throws a {@link ObjectNotFoundException}.
     */
    @Override
    public Device findById(int id) throws ObjectNotFoundException {
        return deviceRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("There is no metric with id: %s", id)));
    }

    /**
     * Creates a new device record.
     *
     * @param device The device to be created.
     * @return The created device.
     */
    @Override
    public Device create(Device device) {
        return deviceRepository.save(device);
    }

    /**
     * Updates an existing device.
     *
     * @param device The device to be updated.
     * @return The updated device.
     */
    @Override
    public Device update(Device device) {
        return deviceRepository.save(device);
    }

    /**
     * Deletes an existing device by the given id.
     *
     * @param id The device id.
     */
    @Override
    public void delete(int id) {
        deviceRepository.deleteById(id);
    }
}
