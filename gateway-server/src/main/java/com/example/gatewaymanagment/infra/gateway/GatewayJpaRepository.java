package com.example.gatewaymanagment.infra.gateway;

import com.example.gatewaymanagment.domain.gateway.Gateway;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GatewayJpaRepository extends JpaRepository<Gateway, String> {
}
