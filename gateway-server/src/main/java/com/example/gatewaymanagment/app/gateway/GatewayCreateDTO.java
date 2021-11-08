package com.example.gatewaymanagment.app.gateway;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * Class represent information to create a gateway
 */
@Schema(name = "GatewayCreateDTO")
public class GatewayCreateDTO {
    @Schema(description = "Serial number of gateway", example = "ABC123")
    private String serialNumber;

    @Schema(description = "Name of gateway", example = "Gateway1")
    private String name;

    @Schema(description = "Id address of gateway", example = "192.168.1.3")
    private String ipv4Address;

    @Schema(description = "List of devices connected to gateway")
    private List<DeviceCreateDTO> devices;

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIpv4Address() {
        return ipv4Address;
    }

    public void setIpv4Address(String ipv4Address) {
        this.ipv4Address = ipv4Address;
    }

    public List<DeviceCreateDTO> getDevices() {
        return devices;
    }

    public void setDevices(List<DeviceCreateDTO> devices) {
        this.devices = devices;
    }

    @Schema(name = "DeviceCreateDTO")
    public static class DeviceCreateDTO {
        @Schema(description = "Vendor of device", example = "Vendor 1")
        private String vendor;

        @Schema(description = "Status of device", example = "ONLINE")
        private Status status;

        public String getVendor() {
            return vendor;
        }

        public void setVendor(String vendor) {
            this.vendor = vendor;
        }

        public Status getStatus() {
            return status;
        }

        public void setStatus(Status status) {
            this.status = status;
        }
    }
}
