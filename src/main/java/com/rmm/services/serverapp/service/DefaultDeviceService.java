package com.rmm.services.serverapp.service;

import com.rmm.services.serverapp.exception.ObjectNotFoundException;
import com.rmm.services.serverapp.model.Customer;
import com.rmm.services.serverapp.model.Device;
import com.rmm.services.serverapp.model.DeviceType;
import com.rmm.services.serverapp.repository.DeviceRepository;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link DeviceService}'
 */
@Service
public class DefaultDeviceService implements DeviceService {
    private final DeviceRepository deviceRepository;
    private final CustomerService customerService;

    public DefaultDeviceService(DeviceRepository deviceRepository, CustomerService customerService) {
        this.deviceRepository = deviceRepository;
        this.customerService = customerService;
    }

    /**
     * Finds a device by the given id.
     *
     * @param id The device id.
     * @return The found device, otherwise throws a {@link ObjectNotFoundException}.
     */
    @Override
    public Device findById(Long id) throws ObjectNotFoundException {
        return deviceRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("There is no metric with id: %s", id)));
    }

    /**
     * Creates a new device record.
     *
     * @param customerId The customer id.
     * @param name       The name of the device.
     * @param type       The type of the device.
     * @return The created device.
     */
    @Override
    public Device create(int customerId, String name, DeviceType type) {
        Customer customer = this.customerService.findById(customerId);

        Device device = new Device(name, type, customer);
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
    public void delete(Long id) {
        deviceRepository.deleteById(id);
    }
}
