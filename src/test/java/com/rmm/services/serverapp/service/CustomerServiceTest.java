package com.rmm.services.serverapp.service;

import com.rmm.services.serverapp.exception.DomainException;
import com.rmm.services.serverapp.exception.ObjectNotFoundException;
import com.rmm.services.serverapp.model.*;
import com.rmm.services.serverapp.repository.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {
    private final int defaultCustomerId = 1;
    private final Device defaultDevice = new Device("Macbook Pro", DeviceType.MAC, new Customer());
    private final Service defaultService = new Service("Audits");

    @Mock
    private CustomerRepository customerRepository;

    private CustomerService customerService;

    @Before
    public void setUp() {
        customerService = new DefaultCustomerService(customerRepository);

        Customer defaultCustomer = new Customer();
        defaultCustomer.addService(defaultService);
        defaultCustomer.addDevice(defaultDevice);

        when(customerRepository.findById(defaultCustomerId))
                .thenReturn(Optional.of(defaultCustomer));
    }

    @Test
    public void should_return_customer_by_id() {
        // Arrange

        // Act
        customerService.findById(defaultCustomerId);

        // Assert
        verify(customerRepository, times(1)).findById(defaultCustomerId);
    }

    @Test(expected = ObjectNotFoundException.class)
    public void should_return_exception_when_customer_not_found() {
        // Arrange

        // Act
        customerService.findById(2);

        // Assert
        verify(customerRepository, times(1)).findById(2);
    }

    @Test
    public void should_add_new_service() {
        // Arrange
        Service newService = new Service("Tests");

        // Act
        customerService.addService(defaultCustomerId, newService);

        // Assert
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test(expected = DomainException.class)
    public void should_return_exception_when_adding_an_existing_service() {
        // Arrange

        // Act
        customerService.addService(defaultCustomerId, defaultService);

        // Assert
        verify(customerRepository, times(0)).save(any(Customer.class));
    }

    @Test
    public void should_calculate_monthly_billing() {
        // Arrange
        defaultService.addCost(new ServiceCost(defaultService, DeviceType.MAC, new BigDecimal(7)));

        // Act
        MonthlyBilling monthlyBilling = customerService.calculateMonthlyBilling(defaultCustomerId);

        // Assert
        assertEquals(monthlyBilling.getTotal(), new BigDecimal(11));
        assertEquals(monthlyBilling.getSummary().get("Devices"), new BigDecimal(4));
        assertEquals(monthlyBilling.getSummary().get(defaultService.getName()), new BigDecimal(7));
    }
}
