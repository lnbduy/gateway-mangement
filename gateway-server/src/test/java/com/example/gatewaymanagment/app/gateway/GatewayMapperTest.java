package com.example.gatewaymanagment.app.gateway;

import com.example.gatewaymanagment.domain.gateway.*;
import com.example.gatewaymanagment.domain.gateway.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DisplayName("Gateway - mapper test")
class GatewayMapperTest {
    @InjectMocks
    private GatewayMapper mapper;

    @Test
    @DisplayName("Test map Gateway to GatewayQueryDTO")
    void test_mapGatewayToGatewayQueryDTO() {
        // Given
        Gateway gateway = getGateway();
        GatewayQueryDTO expected = getGatewayQueryDTO();

        // When
        GatewayQueryDTO actual = mapper.map(gateway);

        // Then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("Test map list of Gateway to list of GatewayQueryDTO")
    void test_mapGatewaysToGatewayQueryDTOs() {
        // Given
        Gateway gateway = getGateway();
        List<Gateway> gateways = Collections.singletonList(gateway);
        GatewayQueryDTO queryDTO = getGatewayQueryDTO();
        List<GatewayQueryDTO> expected = Collections.singletonList(queryDTO);

        // When
        List<GatewayQueryDTO> actual = mapper.mapGateways(gateways);

        // Then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("Test map GatewayCreateDTO to GatewayCommand")
    void test_mapGatewayCreateDTOToGatewayCommand() {
        // Given
        GatewayCreateDTO dto = getGatewayCreateDTO();

        GatewayCommand expected = getGatewayCommandWhenCreate();

        // When
        GatewayCommand actual = mapper.map(dto);

        // Then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("Test map GatewayUpdateDTO to GatewayCommand")
    void test_mapGatewayUpdateDTOToGatewayCommand() {
        // Given
        GatewayUpdateDTO dto = getGatewayUpdateDTO();


        GatewayCommand expected = getGatewayCommandWhenUpdate();

        // When
        GatewayCommand actual = mapper.map(dto);

        // Then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);

    }

    private GatewayQueryDTO getGatewayQueryDTO() {
        GatewayQueryDTO queryDTO = new GatewayQueryDTO();
        queryDTO.setSerialNumber("12");
        queryDTO.setName("gateway");
        queryDTO.setIpv4Address("192.168.1.1");
        GatewayQueryDTO.DeviceQueryDTO deviceDTO = new GatewayQueryDTO.DeviceQueryDTO();
        deviceDTO.setStatus(com.example.gatewaymanagment.app.gateway.Status.ONLINE);
        deviceDTO.setVendor("Vendor");
        deviceDTO.setCreatedDate(LocalDate.now());
        List<GatewayQueryDTO.DeviceQueryDTO> deviceDTOs = Collections.singletonList(deviceDTO);
        queryDTO.setDevices(deviceDTOs);
        return queryDTO;
    }

    private Gateway getGateway() {
        Gateway gateway = new Gateway();
        gateway.setSerialNumber("12");
        gateway.setName("gateway");
        gateway.setIpv4Address("192.168.1.1");

        Device device = new Device();
        device.setVendor("Vendor");
        device.setStatus(Status.ONLINE);
        device.setCreatedDate(LocalDate.now());
        List<Device> devices = Collections.singletonList(device);
        gateway.setDevices(devices);
        return gateway;
    }

    private GatewayCommand getGatewayCommandWhenCreate() {
        GatewayCommand command = new GatewayCommand();
        command.setSerialNumber("12");
        command.setName(Optional.of("Gateway"));
        command.setIpv4Address(Optional.of("192.168.1.11"));
        DeviceCommand deviceCommand = new DeviceCommand();
        deviceCommand.setStatus(Optional.of(Status.OFFLINE));
        deviceCommand.setVendor(Optional.of("Vendor"));
        command.setDeviceCommands(Collections.singletonList(deviceCommand));
        return command;
    }

    private GatewayCreateDTO getGatewayCreateDTO() {
        GatewayCreateDTO dto = new GatewayCreateDTO();
        dto.setSerialNumber("12");
        dto.setName("Gateway");
        dto.setIpv4Address("192.168.1.11");

        GatewayCreateDTO.DeviceCreateDTO deviceDTO = new GatewayCreateDTO.DeviceCreateDTO();
        deviceDTO.setStatus(com.example.gatewaymanagment.app.gateway.Status.OFFLINE);
        deviceDTO.setVendor("Vendor");

        dto.setDevices(Collections.singletonList(deviceDTO));
        return dto;
    }

    private GatewayCommand getGatewayCommandWhenUpdate() {
        GatewayCommand expected = new GatewayCommand();
        expected.setSerialNumber("12");
        expected.setName(Optional.of("Gateway"));
        expected.setIpv4Address(Optional.of("192.168.1.11"));
        DeviceCommand deviceCommand1 = new DeviceCommand();
        deviceCommand1.setId(1);
        deviceCommand1.setStatus(Optional.of(Status.OFFLINE));
        deviceCommand1.setVendor(Optional.of("Vendor1"));

        DeviceCommand deviceCommand2 = new DeviceCommand();

        deviceCommand2.setStatus(Optional.of(Status.ONLINE));
        deviceCommand2.setVendor(Optional.of("Vendor2"));
        List<DeviceCommand> devices = new ArrayList<>();
        devices.add(deviceCommand1);
        devices.add(deviceCommand2);

        expected.setDeviceCommands(devices);
        return expected;
    }

    private GatewayUpdateDTO getGatewayUpdateDTO() {
        GatewayUpdateDTO dto = new GatewayUpdateDTO();
        dto.setSerialNumber("12");
        dto.setName(Optional.of("Gateway"));
        dto.setIpv4Address(Optional.of("192.168.1.11"));

        GatewayUpdateDTO.DeviceUpdateDTO deviceDTO1 = new GatewayUpdateDTO.DeviceUpdateDTO();
        deviceDTO1.setId(1);
        deviceDTO1.setStatus(Optional.of(com.example.gatewaymanagment.app.gateway.Status.OFFLINE));
        deviceDTO1.setVendor(Optional.of("Vendor1"));

        GatewayUpdateDTO.DeviceUpdateDTO deviceDTO2 = new GatewayUpdateDTO.DeviceUpdateDTO();
        deviceDTO2.setStatus(Optional.of(com.example.gatewaymanagment.app.gateway.Status.ONLINE));
        deviceDTO2.setVendor(Optional.of("Vendor2"));
        List<GatewayUpdateDTO.DeviceUpdateDTO> deviceUpdateDTOs = new ArrayList<>();

        deviceUpdateDTOs.add(deviceDTO1);
        deviceUpdateDTOs.add(deviceDTO2);

        dto.setDevices(deviceUpdateDTOs);
        return dto;
    }
}