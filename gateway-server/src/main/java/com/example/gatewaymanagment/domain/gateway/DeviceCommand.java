package com.example.gatewaymanagment.domain.gateway;

import java.util.Optional;

public class DeviceCommand {
    private Integer id;
    private Optional<String> vendor;
    private Optional<Status> status;

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
