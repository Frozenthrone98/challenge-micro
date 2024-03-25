package com.mamani.mscustomermanagement.service.impl;

import com.mamani.mscustomermanagement.domain.entity.Customer;
import com.mamani.mscustomermanagement.domain.repository.CustomerRepository;
import com.mamani.mscustomermanagement.dto.CustomerDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;
    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer customer;
    private CustomerDTO customerDto;
    private UUID customerId;

    @BeforeEach
    public void setUp() {

        MockitoAnnotations.openMocks(this);

        customerId = UUID.randomUUID();
        customer = Customer.builder()
                .customerId(customerId)
                .password("testPassword")
                .status(true)
                .build();

        customerDto = CustomerDTO.builder()
                .customerId(customerId)
                .password("testPassword")
                .status(true)
                .build();

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
    }

    @Test
    public void saveCustomer_Success() throws Exception {
        CustomerDTO savedCustomerDto = customerService.saveCustomer(customerDto);

        assertNotNull(savedCustomerDto);
        assertEquals(customerDto.password(), savedCustomerDto.password());
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    public void updateCustomerById_Success() throws Exception {
        CustomerDTO updatedCustomerDto = customerService.updateCustomerById(customerDto, customerId);

        assertNotNull(updatedCustomerDto);
        assertEquals(customerId, updatedCustomerDto.customerId());
        verify(customerRepository, times(1)).save(any(Customer.class));
    }
}