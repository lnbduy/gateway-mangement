package com.example.gatewaymanagment.rest.gateway;

import com.example.gatewaymanagment.app.gateway.GatewayApplicationService;
import com.example.gatewaymanagment.app.gateway.GatewayCreateDTO;
import com.example.gatewaymanagment.app.gateway.GatewayQueryDTO;
import com.example.gatewaymanagment.app.gateway.GatewayUpdateDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Gateway Controller Test")
class GatewayControllerTest {
    @Mock
    private GatewayApplicationService applicationService;
    @InjectMocks
    private GatewayController controller;

    @Test
    @DisplayName("Test find all gateways")
    void test_findAllGateways() {
        // Given
        GatewayQueryDTO dto = new GatewayQueryDTO();
        dto.setSerialNumber("123");
        List<GatewayQueryDTO> expected = Collections.singletonList(dto);
        when(applicationService.findAllGateways()).thenReturn(expected);

        // When
        List<GatewayQueryDTO> response = controller.findAllGateways();
        // Then
        assertThat(response).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("Test find one gateway")
    void test_findOneGateway() {
        GatewayQueryDTO expected = new GatewayQueryDTO();
        expected.setSerialNumber("1234");
        when(applicationService.findOneGateway("1234")).thenReturn(expected);

        GatewayQueryDTO response = controller.findOneGateway("1234");
        assertThat(response).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("Test create a gateway")
    void createGateway() {
        // Given
        GatewayCreateDTO dto = new GatewayCreateDTO();
        dto.setSerialNumber("12");

        // When
        ResponseEntity<?> response = controller.createGateway(dto);

        // Then
        verify(applicationService, times(1)).createGateway(dto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().get("X-ID"));
        assertEquals("12", response.getHeaders().get("X-ID").get(0));
    }

    @Test
    @DisplayName("Test update a gateway")
    void updateGateway() {
        // Given
        GatewayUpdateDTO dto = new GatewayUpdateDTO();

        // When
        ResponseEntity<GatewayQueryDTO> response = controller.updateGateway("12", dto);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(applicationService, times(1)).updateGateway(dto);
        assertEquals("12", dto.getSerialNumber());
    }

    @Test
    @DisplayName("Test delete a gateway")
    void deleteGateway() {
        // When
        controller.deleteGateway("123");

        // Then
        verify(applicationService, times(1)).deleteGateway("123");
    }
}