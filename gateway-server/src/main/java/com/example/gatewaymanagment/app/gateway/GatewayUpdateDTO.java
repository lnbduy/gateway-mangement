package com.example.gatewaymanagment.app.gateway;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Class represent information to update a gateway, support partial update.
 */
@Schema(name = "GatewayUpdateDTO")
public class GatewayUpdateDTO {
    private String serialNumber; // required for update
    private Optional<String> name;
    private Optional<String> ipv4Address;
    private List<DeviceUpdateDTO> devices;

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

    public List<DeviceUpdateDTO> getDevices() {
        return devices;
    }

    public void setDevices(List<DeviceUpdateDTO> devices) {
        this.devices = devices;
    }

    public static class DeviceUpdateDTO {
        private Integer id; // for partial update. if id is null this device is newlt added
        private Optional<String> vendor; // if not provided then vendor is not updated
        private Optional<Status> status; // if not provided then status; is not updated

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Optional<String> getVendor() {
            return vendor;
        }

        public void setVendor(Optional<String> vendor) {
            this.vendor = vendor;
        }

        public Optional<Status> getStatus() {
            return status;
        }

        public void setStatus(Optional<Status> status) {
            this.status = status;
        }
    }
}
