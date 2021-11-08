package com.example.gatewaymanagment.infra.gateway;

import com.example.gatewaymanagment.domain.gateway.Gateway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("GatewayRepositoryImpl test")
class GatewayRepositoryImplTest {
    @Mock
    private GatewayJpaRepository jpaRepository;
    @InjectMocks
    private GatewayRepositoryImpl repository;

    @Test
    @DisplayName("Test find one gateway")
    void test_findOne() {
        // When
        repository.findOne("123");
        // Then
        verify(jpaRepository, times(1)).findById("123");
    }

    @Test
    @DisplayName("Test find all gateways")
    void findAll() {
        // When
        repository.findAll();
        // Then
        verify(jpaRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test save a gateway")
    void save() {
        // Given
        Gateway gateway = new Gateway();
        gateway.setSerialNumber(("1"));

        // When
        repository.save(gateway);
        // Then
        verify(jpaRepository, times(1)).save(gateway);
    }

    @Test
    @DisplayName("Test delete a gateway")
    void delete() {
        // Given
        Gateway gateway = new Gateway();
        gateway.setSerialNumber(("1"));
        // When
        repository.delete(gateway);
        // Then
        verify(jpaRepository, times(1)).delete(gateway);
    }
}