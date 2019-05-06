package com.rmm.services.serverapp.controler;

import com.rmm.services.serverapp.controler.request.CreateOrUpdateDeviceDTO;
import com.rmm.services.serverapp.controler.response.DeviceDTO;
import com.rmm.services.serverapp.model.Customer;
import com.rmm.services.serverapp.model.Device;
import com.rmm.services.serverapp.service.CustomerService;
import com.rmm.services.serverapp.service.DeviceService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * API resources for devices.
 */
@RestController
@RequestMapping("/api")
public class DeviceController {
    private final DeviceService deviceService;
    private final CustomerService customerService;
    private final ModelMapper modelMapper;

    public DeviceController(DeviceService deviceService, CustomerService customerService, ModelMapper modelMapper) {
        this.deviceService = deviceService;
        this.customerService = customerService;
        this.modelMapper = modelMapper;
    }

    /**
     * Handler method to retrieve a list of customer devices.
     */
    @GetMapping("/customers/{customerId}/devices")
    public ResponseEntity<List<DeviceDTO>> getDevices(@PathVariable int customerId) {
        Customer customer = this.customerService.findById(customerId);

        List<DeviceDTO> devices = customer.getDevices().stream()
                .map(device -> modelMapper.map(device, DeviceDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(devices);
    }

    /**
     * Handler method to retrieve a device.
     */
    @GetMapping("/devices/{id}")
    public ResponseEntity<DeviceDTO> getDevice(@PathVariable Long id) {
        Device device = deviceService.findById(id);
        return ResponseEntity.ok(modelMapper.map(device, DeviceDTO.class));
    }

    /**
     * Handler method to create a new device.
     */
    @PostMapping("/customers/{customerId}/devices")
    @ApiResponses(value = {
            @ApiResponse(
                    code = 201,
                    message = "New device created",
                    responseHeaders = @ResponseHeader(name = "location", description = "The resulting URI of the newly-created service", response = String.class)
            )
    })
    public ResponseEntity<?> createDevice(@PathVariable int customerId, @RequestBody @Valid CreateOrUpdateDeviceDTO input) {
        Device newDevice = deviceService.create(customerId, input.getName(), input.getType());

        URI location = linkTo(methodOn(getClass()).getDevice(newDevice.getId())).toUri();
        return ResponseEntity.created(location).build();
    }

    /**
     * Handler method to update an existing device.
     */
    @PutMapping("/devices/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> updateDevice(@PathVariable Long id, @RequestBody @Valid CreateOrUpdateDeviceDTO input) {
        Device device = deviceService.findById(id);
        device.setName(input.getName());
        device.setType(input.getType());

        deviceService.update(device);

        return ResponseEntity.noContent().build();
    }

    /**
     * Handler method to delete a device.
     */
    @DeleteMapping("/devices/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Device> deleteDevice(@PathVariable Long id) {
        deviceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
