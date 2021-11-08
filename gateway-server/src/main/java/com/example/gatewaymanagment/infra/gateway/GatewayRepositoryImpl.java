package com.example.gatewaymanagment.infra.gateway;

import com.example.gatewaymanagment.domain.gateway.Gateway;
import com.example.gatewaymanagment.domain.gateway.GatewayRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class GatewayRepositoryImpl implements GatewayRepository {
    private GatewayJpaRepository jpaRepository;

    public GatewayRepositoryImpl(GatewayJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Gateway> findOne(String serialNumber) {
        return jpaRepository.findById(serialNumber);
    }

    @Override
    public List<Gateway> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public Gateway save(Gateway gateway) {
        return jpaRepository.save(gateway);
    }

    @Override
    public void delete(Gateway gateway) {
        jpaRepository.delete(gateway);
    }
}
