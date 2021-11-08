package com.example.gatewaymanagment.rest.gateway;

import com.example.gatewaymanagment.app.gateway.GatewayApplicationService;
import com.example.gatewaymanagment.app.gateway.GatewayCreateDTO;
import com.example.gatewaymanagment.app.gateway.GatewayQueryDTO;
import com.example.gatewaymanagment.app.gateway.GatewayUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GatewayController {
    private final GatewayApplicationService applicationService;

    public GatewayController(GatewayApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Operation(
            summary = "Get all gateways",
            description = "API to retrieve all gateways and devices"
    )
    @GetMapping(value = "/gateways")
    @ResponseStatus(HttpStatus.OK)
    public List<GatewayQueryDTO> findAllGateways() {
        return applicationService.findAllGateways();
    }

    @Operation(
            summary = "Get a gateway",
            description = "API to retrieve details of a gateway"
    )
    @GetMapping(value = "/gateways/{serialNumber}")
    @ResponseStatus(HttpStatus.OK)
    public GatewayQueryDTO findOneGateway(@PathVariable(value = "serialNumber") @Parameter(name = "serialNumber", required = true) String serialNumber) {
        return applicationService.findOneGateway(serialNumber);
    }

    @Operation(
            summary = "Create a gateway along with devices",
            description = "Method to create gateway"
    )
    @PostMapping(value = "/gateways")
    public ResponseEntity<?> createGateway(@RequestBody GatewayCreateDTO gatewayCreateDTO) {
        applicationService.createGateway(gatewayCreateDTO);
        UriComponents uriComponents =
                UriComponentsBuilder.newInstance().path("/gateways/{serialNumber}").buildAndExpand(gatewayCreateDTO.getSerialNumber());
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponents.toUri());
        headers.set("X-ID", String.valueOf(gatewayCreateDTO.getSerialNumber()));
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update gateway information and all devices",
            description = "Method to update gateway, devices also can add or remove devices from a gateway"
    )
    @PatchMapping(value = "/gateways/{serialNumber}")
    public ResponseEntity<GatewayQueryDTO> updateGateway(@PathVariable(value = "serialNumber") @Parameter(name = "serialNumber", required = true) String serialNumber, @RequestBody GatewayUpdateDTO gatewayUpdateDTO) {
        gatewayUpdateDTO.setSerialNumber(serialNumber);
        GatewayQueryDTO response = applicationService.updateGateway(gatewayUpdateDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "Update a gateway",
            description = "API to delete a gateway"
    )
    @DeleteMapping(value = "/gateways/{serialNumber}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGateway(@PathVariable(value = "serialNumber") @Parameter(name = "serialNumber", required = true) String serialNumber) {
        applicationService.deleteGateway(serialNumber);
    }
}
