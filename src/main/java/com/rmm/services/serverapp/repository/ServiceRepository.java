package com.rmm.services.serverapp.repository;

import com.rmm.services.serverapp.model.Service;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Handles service operations in a persistence storage.
 */
@Repository
public interface ServiceRepository extends CrudRepository<Service, Long> {
}
