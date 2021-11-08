package com.example.gatewaymanagment.domain.gateway;

import com.example.gatewaymanagment.domain.common.validation.NotFoundException;
import com.example.gatewaymanagment.domain.common.validation.ValidationError;
import com.example.gatewaymanagment.domain.common.validation.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Gateway Domain Service test")
class GatewayDomainServiceTest {
    @Mock
    private GatewayRepository repository;
    @Mock
    private GatewayValidator validator;
    @InjectMocks
    private GatewayDomainService domainService;

    @Test
    @DisplayName("Test create a gateway that serial number is the same with an existing gateway")
    void test_createGatewayThatAlreadyExist() {
        // Given
        GatewayCommand command = new GatewayCommand();
        command.setSerialNumber("12");
        when(repository.findOne("12")).thenReturn(Optional.of(new Gateway()));

        // When
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            domainService.createGateway(command);
        });

        // Then
        ValidationError error = exception.getValidationResult().getErrors().get(0);
        assertEquals("existing", error.getCode());
        assertEquals("serialNumber", error.getPointer());
        assertEquals("Gateway with serial number 12 already exists", error.getMessage());
    }

    @Test
    @DisplayName("Test create new gateway")
    void test_createNewGateway() {
        // Given
        GatewayCommand command = getGatewayCommandWhenCreate();
        command.setSerialNumber("12");
        when(repository.findOne("12")).thenReturn(Optional.empty());

        // When
        domainService.createGateway(command);

        // Then
        verify(validator, times(1)).validate(command);
        verify(repository, times(1)).save(any(Gateway.class));
    }

    @Test
    @DisplayName("Test update a gateway that does not exist")
    void updateGatewayNotExist() {
        GatewayCommand command = new GatewayCommand();
        command.setSerialNumber("12");
        when(repository.findOne("12")).thenReturn(Optional.empty());

        // When
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            domainService.updateGateway(command);
        });

        // Then
        assertEquals("not_found", exception.getCode());
        assertEquals("serialNumber", exception.getPointer());
        assertEquals("Gateway with serial number 12 not found", exception.getMessage());
    }

    @Test
    @DisplayName("Test update an existing gateway")
    void updateExistingGateway() {
        // Given
        Gateway existingGateway = getGateway();
        when(repository.findOne("12")).thenReturn(Optional.of(existingGateway));
        GatewayCommand command = getGatewayCommandWhenUpdate();

        // When
        domainService.updateGateway(command);

        // Then
        // Gateway Name updated
        assertEquals("Gateway Name updated", existingGateway.getName());
        // Ip address updated
        assertEquals("192.168.1.111", existingGateway.getIpv4Address());

        List<Device> devices = existingGateway.getDevices();
        // Device with id 2 is removed
        assertFalse(devices.stream().filter(device -> Integer.valueOf(2).equals(device.getId())).findAny().isPresent());
        // Device with id 1 is updated
        Device device1 = devices.get(0);
        assertEquals("Vendor1 updated", device1.getVendor());
        assertEquals(Status.OFFLINE, device1.getStatus());
        // New device is added
        Device device3 = devices.get(1);
        assertEquals("Vendor3", device3.getVendor());
        assertEquals(Status.ONLINE, device3.getStatus());

        verify(validator, times(1)).validate(command);
        verify(repository, times(1)).save(existingGateway);

    }

    @Test
    @DisplayName("Test find all gateways")
    void test_findAllGateways() {
        // Given
        Gateway gateway = getGateway();
        List<Gateway> expected = Collections.singletonList(gateway);
        when(repository.findAll()).thenReturn(expected);

        // When
        List<Gateway> actual = domainService.findAllGateways();

        // Then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("Test find one gateway that is existing in database")
    void test_findOneExist() {
        // Given
        Gateway expected = getGateway();

        when(repository.findOne("12")).thenReturn(Optional.of((expected)));

        // When
        Gateway actual = domainService.findOne("12");

        // Then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("Test find one gateway that does not exist in database")
    void test_findOneNotExist() {
        // Given
        when(repository.findOne("12")).thenReturn(Optional.empty());

        // When
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            domainService.findOne("12");
        });

        // Then
        assertEquals("not_found", exception.getCode());
        assertEquals("serialNumber", exception.getPointer());
        assertEquals("Gateway with serial number 12 not found", exception.getMessage());
    }

    @Test
    @DisplayName("Test delete gateway that is existing in database")
    void test_deleteGatewayExist() {
        // Given
        Gateway gateway = getGateway();

        when(repository.findOne("12")).thenReturn(Optional.of((gateway)));

        // When
        domainService.deleteGateway("12");

        // Then
        verify(repository, times(1)).delete(gateway);
    }

    @Test
    @DisplayName("Test delete gateway that does not exist in database")
    void test_deleteGatewayNotExist() {
        // Given
        when(repository.findOne("12")).thenReturn(Optional.empty());

        // When
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            domainService.findOne("12");
        });

        // Then
        assertEquals("not_found", exception.getCode());
        assertEquals("serialNumber", exception.getPointer());
        assertEquals("Gateway with serial number 12 not found", exception.getMessage());
    }

    private Gateway getGateway() {
        Gateway gateway = new Gateway();
        gateway.setSerialNumber("12");
        gateway.setName("gateway");
        gateway.setIpv4Address("192.168.1.1");

        List<Device> devices = new ArrayList<>();
        Device device1 = new Device();
        device1.setId(1);
        device1.setVendor("Vendor 1");
        device1.setStatus(Status.ONLINE);
        device1.setCreatedDate(LocalDate.now());
        devices.add(device1);

        Device device2 = new Device();
        device2.setId(2);
        device2.setVendor("Vendor 2");
        device2.setStatus(Status.OFFLINE);
        device2.setCreatedDate(LocalDate.now());
        devices.add(device2);

        gateway.setDevices(devices);

        return gateway;
    }

    private GatewayCommand getGatewayCommandWhenCreate() {
        GatewayCommand command = new GatewayCommand();
        command.setSerialNumber("12");
        command.setName(Optional.of("Gateway"));
        command.setIpv4Address(Optional.of("192.168.1.11"));

        List<DeviceCommand> deviceCommands = new ArrayList<>();
        DeviceCommand deviceCommand1 = new DeviceCommand();
        deviceCommand1.setStatus(Optional.of(Status.OFFLINE));
        deviceCommand1.setVendor(Optional.of("Vendor1"));
        deviceCommands.add(deviceCommand1);

        DeviceCommand deviceCommand2 = new DeviceCommand();
        deviceCommand1.setStatus(Optional.of(Status.ONLINE));
        deviceCommand1.setVendor(Optional.of("Vendor2"));
        deviceCommands.add(deviceCommand2);

        command.setDeviceCommands(deviceCommands);
        return command;
    }

    private GatewayCommand getGatewayCommandWhenUpdate() {
        GatewayCommand command = new GatewayCommand();
        command.setSerialNumber("12");
        command.setName(Optional.of("Gateway Name updated"));
        command.setIpv4Address(Optional.of("192.168.1.111"));

        // Update device 1, remove device 2 and add new device 3
        List<DeviceCommand> deviceCommands = new ArrayList<>();
        DeviceCommand deviceCommand1 = new DeviceCommand();
        deviceCommand1.setStatus(Optional.of(Status.OFFLINE));
        deviceCommand1.setVendor(Optional.of("Vendor1 updated"));
        deviceCommand1.setId(1);
        deviceCommands.add(deviceCommand1);

        DeviceCommand deviceCommand2 = new DeviceCommand();
        deviceCommand2.setStatus(Optional.of(Status.ONLINE));
        deviceCommand2.setVendor(Optional.of("Vendor3"));
        deviceCommands.add(deviceCommand2);

        command.setDeviceCommands(deviceCommands);
        return command;
    }
}