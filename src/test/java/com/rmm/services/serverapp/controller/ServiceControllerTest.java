package com.rmm.services.serverapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rmm.services.serverapp.controller.request.AddServiceDTO;
import com.rmm.services.serverapp.model.*;
import com.rmm.services.serverapp.repository.CustomerRepository;
import com.rmm.services.serverapp.repository.ServiceRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class ServiceControllerTest {
    private final String defaultService = "Antivirus";
    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        Service service = new Service(defaultService);
        service.addCost(new ServiceCost(service, DeviceType.WINDOWS_SERVER, new BigDecimal(5)));
        service.addCost(new ServiceCost(service, DeviceType.MAC, new BigDecimal(7)));

        serviceRepository.save(service);
    }

    @Test
    @WithMockUser
    public void should_return_list_of_services() throws Exception {
        // Arrange

        // Act
        ResultActions resultActions = this.mockMvc.perform(
                get("/api/services"));

        // Assert
        resultActions.andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void should_return_customer_services() throws Exception {
        // Arrange
        Service service = getDefaultService();
        Customer customer = new Customer();
        customer.addService(service);
        Customer savedCustomer = customerRepository.save(customer);

        // Act
        ResultActions resultActions = this.mockMvc.perform(
                get("/api/customers/" + savedCustomer.getId() + "/services"));

        // Assert
        resultActions.andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void should_add_customer_service() throws Exception {
        // Arrange
        Customer customer = new Customer();
        Customer savedCustomer = customerRepository.save(customer);

        Service service = getDefaultService();

        AddServiceDTO serviceDTO = new AddServiceDTO();
        serviceDTO.setId(service.getId());

        // Act
        ResultActions resultActions = this.mockMvc
                .perform(post("/api/customers/" + savedCustomer.getId() + "/services")
                        .content(toJson(serviceDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        // Assert
        resultActions.andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    public void should_return_bad_request_when_adding_existing_customer_service() throws Exception {
        // Arrange
        Service service = getDefaultService();

        Customer customer = new Customer();
        customer.addService(service);
        Customer savedCustomer = customerRepository.save(customer);


        AddServiceDTO serviceDTO = new AddServiceDTO();
        serviceDTO.setId(service.getId());

        // Act
        ResultActions resultActions = this.mockMvc
                .perform(post("/api/customers/" + savedCustomer.getId() + "/services")
                        .content(toJson(serviceDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        // Assert
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    public void should_remove_customer_service() throws Exception {
        // Arrange
        Service service = getDefaultService();

        Customer customer = new Customer();
        customer.addService(service);
        Customer savedCustomer = customerRepository.save(customer);

        // Act
        ResultActions resultActions = this.mockMvc
                .perform(delete("/api/customers/" + savedCustomer.getId() + "/services/" + service.getId()));

        // Assert
        resultActions.andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    @Transactional
    public void should_return_monthly_billing() throws Exception {
        // Arrange
        Customer customer = new Customer();
        Service service = getDefaultService();
        Device device = new Device("Macbook Air", DeviceType.MAC, customer);

        customer.addService(service);
        customer.addDevice(device);
        Customer savedCustomer = customerRepository.save(customer);

        // Act
        ResultActions resultActions = this.mockMvc
                .perform(get("/api/customers/" + savedCustomer.getId() + "/monthly-billing"));

        // Assert
        MvcResult results = resultActions
                .andExpect(status().isOk())
                .andReturn();

        MonthlyBilling monthlyBilling = mapper.readValue(results.getResponse().getContentAsString(), MonthlyBilling.class);
        assertThat(new BigDecimal(11)).isEqualByComparingTo(monthlyBilling.getTotal());
        assertThat(new BigDecimal(4)).isEqualByComparingTo(monthlyBilling.getSummary().get("Devices"));
        assertThat(getServiceCostByDevice(service, device.getType()).getAmount()).isEqualByComparingTo(monthlyBilling.getSummary().get(service.getName()));
    }

    private Service getDefaultService() {
        Iterable<Service> services = serviceRepository.findAll();

        return StreamSupport.stream(services.spliterator(), false)
                .filter(service -> service.getName().equals(defaultService))
                .findFirst()
                .orElse(null);
    }

    private ServiceCost getServiceCostByDevice(Service service, DeviceType deviceType) {
        Iterable<ServiceCost> costs = service.getCosts();

        return StreamSupport.stream(costs.spliterator(), false)
                .filter(s -> s.getDeviceType().equals(deviceType))
                .findFirst()
                .orElse(new ServiceCost());
    }

    private byte[] toJson(Object object) throws Exception {
        return this.mapper
                .writeValueAsString(object).getBytes();
    }
}
