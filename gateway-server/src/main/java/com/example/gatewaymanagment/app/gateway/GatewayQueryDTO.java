package com.example.gatewaymanagment.app.gateway;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;


/**
 * Class represent all information of a gateway when querying.
 */
@Schema(name = "GatewayQueryDTO")
public class GatewayQueryDTO {
    @Schema(description = "Serial number of gateway", example = "ABC123")
    private String serialNumber;

    @Schema(description = "Name of gateway", example = "Gateway1")
    private String name;

    @Schema(description = "Id address of gateway", example = "192.168.1.3")
    private String ipv4Address;

    @Schema(description = "List of devices connected to gateway")
    private List<DeviceQueryDTO> devices;

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

    public List<DeviceQueryDTO> getDevices() {
        return Collections.unmodifiableList(devices);
    }

    public void setDevices(List<DeviceQueryDTO> devices) {
        this.devices = devices;
    }

    @Schema(name = "DeviceQueryDTO")
    public static class DeviceQueryDTO {
        @Schema(name = "id of device")
        private Integer id;

        @Schema(description = "Vendor of device", example = "Vendor 1")
        private String vendor;

        @Schema(description = "Date device is created", example = "25/01/2021")
        private LocalDate createdDate;

        @Schema(description = "Status of device", example = "ONLINE")
        private Status status;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getVendor() {
            return vendor;
        }

        public void setVendor(String vendor) {
            this.vendor = vendor;
        }

        public LocalDate getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(LocalDate createdDate) {
            this.createdDate = createdDate;
        }

        public Status getStatus() {
            return status;
        }

        public void setStatus(Status status) {
            this.status = status;
        }
    }
}
