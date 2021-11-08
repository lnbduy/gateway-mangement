package com.example.gatewaymanagment.app.gateway;

import com.example.gatewaymanagment.app.gateway.GatewayCreateDTO;
import com.example.gatewaymanagment.domain.gateway.GatewayDomainService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GatewayApplicationService {
    private final GatewayDomainService domainService;
    private final GatewayMapper mapper;

    public GatewayApplicationService(GatewayDomainService domainService, GatewayMapper mapper) {
        this.domainService = domainService;
        this.mapper = mapper;
    }

    public List<GatewayQueryDTO> findAllGateways() {
        return mapper.mapGateways(domainService.findAllGateways());
    }

    public GatewayQueryDTO findOneGateway(String serialNumber) {
        return mapper.map(domainService.findOne(serialNumber));
    }

    public void createGateway(GatewayCreateDTO gatewayCreateDTO) {
        domainService.createGateway(mapper.map(gatewayCreateDTO));
    }

    public GatewayQueryDTO updateGateway(GatewayUpdateDTO gatewayUpdateDTO) {
        return mapper.map(domainService.updateGateway(mapper.map(gatewayUpdateDTO)));
    }

    public void deleteGateway(String serialNumber) {
        domainService.deleteGateway(serialNumber);
    }
}
