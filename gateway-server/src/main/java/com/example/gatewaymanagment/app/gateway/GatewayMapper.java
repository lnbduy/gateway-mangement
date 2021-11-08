package com.example.gatewaymanagment.app.gateway;

import com.example.gatewaymanagment.domain.gateway.Device;
import com.example.gatewaymanagment.domain.gateway.DeviceCommand;
import com.example.gatewaymanagment.domain.gateway.Gateway;
import com.example.gatewaymanagment.domain.gateway.GatewayCommand;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Mapper to map between DTO object and domain object.
 */
@Component
public class GatewayMapper {
    /**
     * Map Gateway domain object to GatewayQueryDTO
     *
     * @param gateway Gateway
     * @return a GatewayQueryDTO
     */
    public GatewayQueryDTO map(Gateway gateway) {
        GatewayQueryDTO gatewayQueryDTO = new GatewayQueryDTO();
        gatewayQueryDTO.setSerialNumber(gateway.getSerialNumber());
        gatewayQueryDTO.setName(gateway.getName());
        gatewayQueryDTO.setIpv4Address(gateway.getIpv4Address());
        gatewayQueryDTO.setDevices(mapDeviceQueryDTOs(gateway.getDevices()));

        return gatewayQueryDTO;
    }

    /**
     * Map list of Gateway to list of GatewayQueryDTO
     *
     * @param gateways list of gateways
     * @return list of GatewayQueryDTO
     */
    public List<GatewayQueryDTO> mapGateways(List<Gateway> gateways) {
        if (gateways == null) {
            return null;
        }

        List<GatewayQueryDTO> queryDTOS = new ArrayList<>();
        gateways.forEach(gateway -> queryDTOS.add(map(gateway)));
        return queryDTOS;
    }

    private List<GatewayQueryDTO.DeviceQueryDTO> mapDeviceQueryDTOs(List<Device> devices) {
        if (devices == null) {
            return null;
        }
        List<GatewayQueryDTO.DeviceQueryDTO> deviceDTOs = new ArrayList<>();
        devices.forEach(device -> deviceDTOs.add(map(device)));
        return deviceDTOs;
    }

    private GatewayQueryDTO.DeviceQueryDTO map(Device device) {
        GatewayQueryDTO.DeviceQueryDTO deviceQueryDTO = new GatewayQueryDTO.DeviceQueryDTO();
        deviceQueryDTO.setId(device.getId());
        deviceQueryDTO.setVendor(device.getVendor());
        deviceQueryDTO.setCreatedDate(device.getCreatedDate());
        deviceQueryDTO.setStatus(mapStatus(device.getStatus()));
        return deviceQueryDTO;
    }

    /**
     * Map a GatewayCreateDTO to GatewayCommand
     *
     * @param dto GatewayCreateDTO
     * @return GatewayCommand
     */
    public GatewayCommand map(GatewayCreateDTO dto) {
        GatewayCommand gatewayCommand = new GatewayCommand();
        gatewayCommand.setSerialNumber((dto.getSerialNumber()));
        gatewayCommand.setName(Optional.of(dto.getName()));
        gatewayCommand.setIpv4Address(Optional.of(dto.getIpv4Address()));
        gatewayCommand.setDeviceCommands(mapDeviceCommandsFromCreateDTOs(dto.getDevices()));
        return gatewayCommand;
    }

    private List<DeviceCommand> mapDeviceCommandsFromCreateDTOs(List<GatewayCreateDTO.DeviceCreateDTO> dtos) {
        if (dtos == null) {
            return null;
        }

        List<DeviceCommand> deviceCommands = new ArrayList<>();
        dtos.forEach(dto -> deviceCommands.add(map(dto)));
        return deviceCommands;
    }

    private DeviceCommand map(GatewayCreateDTO.DeviceCreateDTO deviceCreateDTO) {
        DeviceCommand deviceCommand = new DeviceCommand();
        deviceCommand.setStatus(Optional.of(mapStatus(deviceCreateDTO.getStatus())));
        deviceCommand.setVendor(Optional.of(deviceCreateDTO.getVendor()));
        return deviceCommand;
    }

    /**
     * Map a GatewayCreateDTO to GatewayCommand
     *
     * @param dto GatewayCreateDTO
     * @return Gateway
     */
    public GatewayCommand map(GatewayUpdateDTO dto) {
        GatewayCommand gatewayCommand = new GatewayCommand();
        gatewayCommand.setSerialNumber(dto.getSerialNumber());
        gatewayCommand.setName(dto.getName());
        gatewayCommand.setIpv4Address(dto.getIpv4Address());
        gatewayCommand.setDeviceCommands(mapDeviceCommandsFromUpdateDTOs(dto.getDevices()));
        return gatewayCommand;
    }

    private DeviceCommand map(GatewayUpdateDTO.DeviceUpdateDTO deviceUpdateDTO) {
        DeviceCommand deviceCommand = new DeviceCommand();
        deviceCommand.setId(deviceUpdateDTO.getId());
        if (deviceUpdateDTO.getStatus() != null) {
            com.example.gatewaymanagment.domain.gateway.Status status = mapStatus(deviceUpdateDTO.getStatus().orElse(null));
            deviceCommand.setStatus(Optional.ofNullable(status));
        }
        deviceCommand.setVendor(deviceUpdateDTO.getVendor());
        return deviceCommand;
    }

    private List<DeviceCommand> mapDeviceCommandsFromUpdateDTOs(List<GatewayUpdateDTO.DeviceUpdateDTO> dtos) {
        if (dtos == null) {
            return null;
        }

        List<DeviceCommand> deviceCommands = new ArrayList<>();
        dtos.forEach(dto -> deviceCommands.add(map(dto)));
        return deviceCommands;
    }

    private com.example.gatewaymanagment.domain.gateway.Status mapStatus(Status status) {
        if (status == Status.ONLINE) {
            return com.example.gatewaymanagment.domain.gateway.Status.ONLINE;
        } else if (status == Status.OFFLINE) {
            return com.example.gatewaymanagment.domain.gateway.Status.OFFLINE;
        } else {
            return null;
        }
    }

    private Status mapStatus(com.example.gatewaymanagment.domain.gateway.Status status) {
        if (status == com.example.gatewaymanagment.domain.gateway.Status.ONLINE) {
            return Status.ONLINE;
        } else if (status == com.example.gatewaymanagment.domain.gateway.Status.OFFLINE) {
            return Status.OFFLINE;
        } else {
            return null;
        }
    }
}
