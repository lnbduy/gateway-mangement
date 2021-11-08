package com.example.gatewaymanagment.domain.gateway;

import java.util.List;
import java.util.Optional;

public interface GatewayRepository {
    Optional<Gateway> findOne(String serialNumber);
    List<Gateway> findAll();
    Gateway save(Gateway gateway);
    void delete(Gateway gateway);
}
