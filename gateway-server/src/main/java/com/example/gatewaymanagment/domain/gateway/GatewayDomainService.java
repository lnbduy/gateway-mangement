package com.example.gatewaymanagment.domain.gateway;

import com.example.gatewaymanagment.domain.common.validation.NotFoundException;
import com.example.gatewaymanagment.domain.common.validation.ValidationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class GatewayDomainService {
    private final GatewayValidator validator;
    private final GatewayRepository repository;

    public GatewayDomainService(GatewayValidator validator, GatewayRepository repository) {
        this.validator = validator;
        this.repository = repository;
    }

    public void createGateway(GatewayCommand gatewayCommand) {
        if (repository.findOne(gatewayCommand.getSerialNumber()).isPresent()) {
            throw new ValidationException("existing", "serialNumber", "Gateway with serial number " + gatewayCommand.getSerialNumber() + " already exists");
        }
        validator.validate(gatewayCommand);

        Gateway gateway = new Gateway();
        gateway.setSerialNumber(gatewayCommand.getSerialNumber());
        ifDefined(gatewayCommand.getName(), gateway::setName);
        ifDefined(gatewayCommand.getIpv4Address(), gateway::setIpv4Address);

        if (gatewayCommand.getDeviceCommands() != null) {
            gatewayCommand.getDeviceCommands().forEach(command -> gateway.addDevice(createDevice(command, gateway)));
        }
        repository.save(gateway);
    }

    public Gateway updateGateway(GatewayCommand command) {
        Gateway gateway = findOne(command.getSerialNumber());
        // Validate request
        validator.validate(command);

        ifDefined(command.getName(), gateway::setName);
        ifDefined(command.getIpv4Address(), gateway::setIpv4Address);

        if (command.getDeviceCommands() != null) {
            List<DeviceCommand> deviceCommands = command.getDeviceCommands();
            // Remove device if not it is not in the request
            List<Integer> deviceIds = gateway.getDevices().stream().map(device -> device.getId()).collect(Collectors.toList());
            deviceIds.forEach(id -> {
                DeviceCommand deviceCommand = command.findDeviceCommand(id);
                // Existing device does not in the request, meant it will be removed
                if (deviceCommand == null) {
                    gateway.removeDevice(id);
                }
            });

            // Add new or update
            deviceCommands.forEach(deviceCommand -> {
                if (deviceCommand.getId() == null) {
                    // add new device
                    gateway.addDevice(createDevice(deviceCommand, gateway));
                } else {
                    // update device
                    Device device = gateway.findDevice(deviceCommand.getId()).orElse(null);
                    if (device != null) {
                        // Device is existing, update it
                        ifDefined(deviceCommand.getVendor(), device::setVendor);
                        ifDefined(deviceCommand.getStatus(), device::setStatus);
                    }
                }
            });
        }

        // Save the updated gateway
        return repository.save(gateway);
    }

    public List<Gateway> findAllGateways() {
        return repository.findAll();
    }

    public Gateway findOne(String serialNumber) {
        return repository.findOne(serialNumber).orElseThrow(() -> new NotFoundException("not_found", "serialNumber", "Gateway with serial number " + serialNumber + " not found"));
    }

    public void deleteGateway(String serialNumber) {
        Gateway gateway = repository.findOne(serialNumber).orElseThrow(() -> new NotFoundException("not_found", "serialNumber", "Gateway with serial number " + serialNumber + " does not exist"));
        repository.delete(gateway);
    }

    private Device createDevice(DeviceCommand deviceCommand, Gateway gateway) {
        Device device = new Device();
        ifDefined(deviceCommand.getVendor(), device::setVendor);
        ifDefined(deviceCommand.getStatus(), device::setStatus);
        device.setCreatedDate(LocalDate.now());
        device.setGateway(gateway);
        return device;
    }

    private <T> void ifDefined(Optional<T> optional, Consumer<T> consumer) {
        if (optional != null) {
            consumer.accept(optional.isPresent() ? optional.get() : null);
        }
    }
}
