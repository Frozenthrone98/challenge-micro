package com.mamani.mscustomermanagement.controller;

import com.mamani.mscustomermanagement.dto.CustomerDTO;
import com.mamani.mscustomermanagement.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final ICustomerService customerService;

    @RequestMapping(value = "/healthcheck", produces = "application/json; charset=utf-8")
    public String getHealthCheck()	{
        return "{ \"todoOk\" : true }";
    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getCustomers() throws Exception {

        List<CustomerDTO> response = customerService.findAllCustomers();
        TimeUnit.MILLISECONDS.sleep(200);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> findCustomerById(@PathVariable UUID customerId) throws Exception {

        CustomerDTO response = customerService.findCustomerById(customerId);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDto) throws Exception {

        CustomerDTO response = customerService.saveCustomer(customerDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{customerId}")
                .buildAndExpand(response.customerId())
                .toUri();
        log.info("[CustomerController] - Create Customer : " + response.customerId());
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> updateCustomerById(@RequestBody CustomerDTO customerDto, @PathVariable UUID customerId) throws Exception {

        CustomerDTO response = customerService.updateCustomerById(customerDto,customerId);
        log.info("[CustomerController] - Update Customer : " + response.customerId());
        return ResponseEntity.ok(response);
    }


    @PatchMapping("/{customerId}")
    public ResponseEntity<Void> updateStatusById(@PathVariable UUID customerId, @RequestParam Boolean status) throws Exception {

        customerService.updateStatusById(customerId,status);
        log.info("[CustomerController] - Update Status Customer : " + customerId);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomerById(@PathVariable UUID customerId) throws Exception {

        customerService.deleteCustomerById(customerId);
        log.info("[CustomerController] - Delete Customer : " + customerId);
        return ResponseEntity.noContent().build();
    }

}
