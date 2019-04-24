package com.rmm.services.serverapp.repository;

import com.rmm.services.serverapp.model.Device;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Handles device operations in a persistence storage.
 */
@Repository
public interface DeviceRepository extends CrudRepository<Device, Integer> {
}
