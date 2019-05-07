package com.rmm.services.serverapp.controller;

import com.rmm.services.serverapp.controller.request.AddServiceDTO;
import com.rmm.services.serverapp.controller.response.ServiceDTO;
import com.rmm.services.serverapp.model.MonthlyBilling;
import com.rmm.services.serverapp.model.Service;
import com.rmm.services.serverapp.service.CustomerService;
import com.rmm.services.serverapp.service.ServiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * API resources for services.
 */
@RestController
@RequestMapping("/api")
public class ServiceController {
    private final ServiceService serviceService;
    private final CustomerService customerService;

    public ServiceController(ServiceService serviceService, CustomerService customerService) {
        this.serviceService = serviceService;
        this.customerService = customerService;
    }

    /**
     * Handler method to retrieve a list of services.
     */
    @GetMapping("/services")
    public ResponseEntity<List<ServiceDTO>> getServices() {
        Iterable<Service> services = this.serviceService.getAll();
        return ResponseEntity.ok(createServicesResponse(services));
    }

    /**
     * Handler method to retrieve a list of customer services.
     */
    @GetMapping("/customers/{customerId}/services")
    public ResponseEntity<List<ServiceDTO>> getCustomerServices(@PathVariable int customerId) {
        Iterable<Service> services = this.customerService.findById(customerId).getServices();
        return ResponseEntity.ok(createServicesResponse(services));
    }

    /**
     * Handler method to add an existing service to a customer.
     */
    @PostMapping("/customers/{customerId}/services")
    public ResponseEntity<Void> addCustomerService(@PathVariable int customerId, @RequestBody @Valid AddServiceDTO input) {
        Service service = this.serviceService.findById(input.getId());
        this.customerService.addService(customerId, service);

        return ResponseEntity.noContent().build();
    }

    /**
     * Handler method to remove a customer service.
     */
    @DeleteMapping("/customers/{customerId}/services/{serviceId}")
    public ResponseEntity<Void> removeCustomerService(@PathVariable int customerId, @PathVariable int serviceId) {
        Service service = this.serviceService.findById(serviceId);
        this.customerService.removeService(customerId, service);

        return ResponseEntity.noContent().build();
    }

    /**
     * Handler method to retrieve the monthly billing amount for the customer's services.
     */
    @GetMapping("/customers/{customerId}/monthly-billing")
    public ResponseEntity<MonthlyBilling> getMonthlyBilling(@PathVariable int customerId) {
        MonthlyBilling billing = this.customerService.calculateMonthlyBilling(customerId);
        return ResponseEntity.ok(billing);
    }

    private List<ServiceDTO> createServicesResponse(Iterable<Service> services) {
        return StreamSupport.stream(services.spliterator(), false)
                .map(service -> new ServiceDTO(service.getId(), service.getName(), service.getCosts()))
                .collect(Collectors.toList());
    }
}
