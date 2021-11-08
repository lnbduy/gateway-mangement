package com.example.gatewaymanagment.domain.gateway;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GatewayCommand {
    private String serialNumber;
    private Optional<String> name;
    private Optional<String> ipv4Address;
    private List<DeviceCommand> deviceCommands = new ArrayList<>();

    DeviceCommand findDeviceCommand(Integer id) {
        return deviceCommands.stream().filter(deviceCommand -> id.equals(deviceCommand.getId())).findAny().orElse(null);
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Optional<String> getName() {
        return name;
    }

    public void setName(Optional<String> name) {
        this.name = name;
    }

    public Optional<String> getIpv4Address() {
        return ipv4Address;
    }

    public void setIpv4Address(Optional<String> ipv4Address) {
        this.ipv4Address = ipv4Address;
    }

    public List<DeviceCommand> getDeviceCommands() {
        return deviceCommands;
    }

    public void setDeviceCommands(List<DeviceCommand> deviceCommands) {
        this.deviceCommands = deviceCommands;
    }
}