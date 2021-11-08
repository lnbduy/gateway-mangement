package com.example.gatewaymanagment.domain.gateway;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "gateway")
public class Gateway {
    @Id
    @Column(name = "serial_number", nullable = false)
    private String serialNumber;

    @Column(name = "name")
    private String name;

    @Column(name = "ipv4_address")
    private String ipv4Address;

    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "gateway", orphanRemoval = true)
    private List<Device> devices;

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

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    void addDevice(Device device) {
        if (devices == null) {
            devices = new ArrayList<>();
        }
        devices.add(device);
    }

    void removeDevice(Integer id) {
        devices.removeIf(device -> device.getId().equals(id));
    }

    Optional<Device> findDevice(Integer id) {
        if (devices != null) {
            return devices.stream().filter(device -> device.getId().equals(id)).findAny();
        } else {
            return Optional.empty();
        }
    }
}
