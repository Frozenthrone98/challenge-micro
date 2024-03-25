package com.mamani.mscustomeralternative.service.impl;

import com.mamani.mscustomeralternative.domain.entity.Customer;
import com.mamani.mscustomeralternative.domain.entity.Person;
import com.mamani.mscustomeralternative.domain.repository.CustomerRepository;
import com.mamani.mscustomeralternative.dto.CustomerDTO;
import com.mamani.mscustomeralternative.exception.ModelNotFoundException;
import com.mamani.mscustomeralternative.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDto) throws Exception {

        Customer customer = this.convertToEntity(customerDto);
        customer.getPerson().setCreatedAt(LocalDateTime.now());

        customer.setStatus(true);
        customerRepository.save(customer);
        return this.convertToDto(customer);
    }

    @Override
    public CustomerDTO updateCustomerById(CustomerDTO customerDto, UUID customerId) throws Exception {

        Customer customerExist = customerRepository.findById(customerId).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND: " + customerId));

        Customer updateCustomer = this.convertToEntity(customerDto);
        updateCustomer.setCustomerId(customerExist.getCustomerId());
        updateCustomer.getPerson().setCreatedAt(customerExist.getPerson().getCreatedAt());

        return this.convertToDto(customerRepository.save(updateCustomer));
    }

    @Override
    public List<CustomerDTO> findAllCustomers() throws Exception {
        List<CustomerDTO> listCustomersDto = customerRepository.findAll().stream().map(this::convertToDto).toList();
        return listCustomersDto;
    }

    @Override
    public CustomerDTO findCustomerById(UUID customerId) throws Exception {
        Customer customerExist = customerRepository.findById(customerId).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND: " + customerId));
        return this.convertToDto(customerExist);
    }

    @Override
    public void deleteCustomerById(UUID customerId) throws Exception {
        Customer customerExist = customerRepository.findById(customerId).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND: " + customerId));
        customerRepository.deleteById(customerId);
    }

    @Override
    public void updateStatusById(UUID customerId, Boolean status) throws Exception {
        Customer customerExist = customerRepository.findById(customerId).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND: " + customerId));
        customerExist.setStatus(status);
        customerRepository.save(customerExist);
    }

    private Customer convertToEntity(CustomerDTO customerDto) {
        Person person = Person.builder()
                .fullName(customerDto.fullName())
                .gender(customerDto.gender())
                .age(customerDto.age())
                .identificationNumber(customerDto.identificationNumber())
                .address(customerDto.address())
                .phoneNumber(customerDto.phoneNumber())
                .build();

        Customer customer = Customer.builder()
                .password(customerDto.password())
                .status(customerDto.status())
                .person(person)
                .build();
        return customer;
    }

    private CustomerDTO convertToDto(Customer customer) {
        return CustomerDTO.builder()
                .customerId(customer.getCustomerId())
                .password(customer.getPassword())
                .status(customer.getStatus())
                .fullName(customer.getPerson().getFullName())
                .gender(customer.getPerson().getGender())
                .age(customer.getPerson().getAge())
                .identificationNumber(customer.getPerson().getIdentificationNumber())
                .address(customer.getPerson().getAddress())
                .phoneNumber(customer.getPerson().getPhoneNumber())
                .createdAt(customer.getPerson().getCreatedAt())
                .build();
    }

}
