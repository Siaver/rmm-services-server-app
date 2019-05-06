package com.rmm.services.serverapp;

import com.rmm.services.serverapp.model.Customer;
import com.rmm.services.serverapp.model.DeviceType;
import com.rmm.services.serverapp.model.Service;
import com.rmm.services.serverapp.model.ServiceCost;
import com.rmm.services.serverapp.repository.CustomerRepository;
import com.rmm.services.serverapp.repository.ServiceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Database initializer that populates the database with some default data
 */
@Component
public class DBInitializer implements CommandLineRunner {
    private final CustomerRepository customerRepository;
    private final ServiceRepository serviceRepository;

    public DBInitializer(CustomerRepository customerRepository, ServiceRepository serviceRepository) {
        this.customerRepository = customerRepository;
        this.serviceRepository = serviceRepository;
    }

    @Override
    public void run(String... args) {
        if (this.customerRepository.count() == 0) {
            Customer customer = new Customer();
            this.customerRepository.save(customer);
        }

        if (this.serviceRepository.count() == 0) {
            Service service = new Service("Antivirus");
            service.addCost(new ServiceCost(service, DeviceType.WINDOWS_SERVER, new BigDecimal(5)));
            service.addCost(new ServiceCost(service, DeviceType.MAC, new BigDecimal(7)));

            this.serviceRepository.save(service);
        }
    }
}
