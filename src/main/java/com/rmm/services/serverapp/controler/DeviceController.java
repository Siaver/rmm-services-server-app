package com.rmm.services.serverapp.controler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.rmm.services.serverapp.model.Device;
import com.rmm.services.serverapp.service.DeviceService;
import com.rmm.services.serverapp.controler.request.CreateOrUpdateDeviceDTO;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * API resources for devices.
 */
@RestController
@RequestMapping("/api/devices")
public class DeviceController {
    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    /**
     * Handler method to retrieve a device.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Device> getDevice(@PathVariable int id) {
        Device device = deviceService.findById(id);
        return ResponseEntity.ok(device);
    }

    /**
     * Handler method to create a new device.
     */
    @PostMapping
    public ResponseEntity<?> createDevice(@RequestBody @Valid CreateOrUpdateDeviceDTO input) {
        Device device = new Device(input.getName(), input.getType());
        Device newDevice = deviceService.create(device);

        URI location = linkTo(methodOn(getClass()).getDevice(newDevice.getId())).toUri();
        return ResponseEntity.created(location).build();
    }

    /**
     * Handler method to update an existing device.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateDevice(@PathVariable int id, @RequestBody @Valid CreateOrUpdateDeviceDTO input) {
        Device device = deviceService.findById(id);
        device.setName(input.getName());
        device.setType(input.getType());

        deviceService.update(device);

        return ResponseEntity.noContent().build();
    }

    /**
     * Handler method to delete a device.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Device> deleteDevice(@PathVariable int id) {
        deviceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
