package com.mamani.mscustomermanagement.service;

import com.mamani.mscustomermanagement.dto.CustomerDTO;

import java.util.List;
import java.util.UUID;

public interface ICustomerService {
    CustomerDTO saveCustomer(CustomerDTO customerDto) throws Exception;
    CustomerDTO updateCustomerById(CustomerDTO customerDto, UUID customerId) throws Exception;
    List<CustomerDTO> findAllCustomers() throws Exception;
    CustomerDTO findCustomerById(UUID customerId) throws Exception;
    void deleteCustomerById(UUID customerId) throws Exception;
    void updateStatusById(UUID customerId, Boolean status) throws Exception;
}
