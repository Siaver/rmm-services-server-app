package com.rmm.services.serverapp.repository;

import com.rmm.services.serverapp.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Handles customer operations in a persistence storage.
 */
@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {
}
