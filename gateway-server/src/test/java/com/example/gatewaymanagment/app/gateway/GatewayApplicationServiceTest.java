package com.example.gatewaymanagment.app.gateway;

import com.example.gatewaymanagment.domain.gateway.*;
import com.example.gatewaymanagment.domain.gateway.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Gateway application service tests")
class GatewayApplicationServiceTest {
    @Mock
    private GatewayDomainService domainService;
    @Mock
    private GatewayMapper mapper;
    @InjectMocks
    private GatewayApplicationService applicationService;

    @Test
    @DisplayName("Test find all gateways")
    void test_findAllGateways() {
        // Given
        Gateway gateway = getGateway();
        List<Gateway> gateways = Collections.singletonList(gateway);
        when(domainService.findAllGateways()).thenReturn(gateways);

        GatewayQueryDTO dto = getGatewayQueryDTO();
        List<GatewayQueryDTO> expected = Collections.singletonList(dto);
        when(mapper.mapGateways(gateways)).thenReturn(expected);

        // When
        List<GatewayQueryDTO> actual = applicationService.findAllGateways();

        // Then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test find one Gateway")
    void test_findOneGateway() {
        // Given
        Gateway gateway = getGateway();
        when(domainService.findOne("12")).thenReturn(gateway);

        GatewayQueryDTO expected = getGatewayQueryDTO();
        when(mapper.map(gateway)).thenReturn(expected);

        // When
        GatewayQueryDTO actual = applicationService.findOneGateway("12");

        // Then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test create a gateway")
    void test_createGateway() {
        // Given
        GatewayCreateDTO createDTO = new GatewayCreateDTO();
        createDTO.setSerialNumber("123");
        GatewayCommand command = new GatewayCommand();
        command.setSerialNumber("123");
        when(mapper.map(createDTO)).thenReturn(command);

        // When
        applicationService.createGateway(createDTO);

        // Then
        verify(domainService, times(1)).createGateway(command);
        verify(mapper, times(1)).map(createDTO);
    }

    @Test
    @DisplayName("Test update a gateway")
    void test_updateGateway() {
        // Given
        GatewayUpdateDTO updateDTO = new GatewayUpdateDTO();
        updateDTO.setSerialNumber("123");
        GatewayCommand command = new GatewayCommand();
        command.setSerialNumber("123");
        when(mapper.map(updateDTO)).thenReturn(command);

        // When
        applicationService.updateGateway(updateDTO);

        // Then
        verify(domainService, times(1)).updateGateway(command);
        verify(mapper, times(1)).map(updateDTO);
    }

    @Test
    @DisplayName("Test delete a gateway")
    void deleteGateway() {
        // When
        applicationService.deleteGateway("12");

        // Then
        verify(domainService, times(1)).deleteGateway("12");

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
}