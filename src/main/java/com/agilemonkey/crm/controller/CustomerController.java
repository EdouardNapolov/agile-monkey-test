package com.agilemonkey.crm.controller;

import com.agilemonkey.crm.dto.CustomerDTO;
import com.agilemonkey.crm.entity.Customer;
import com.agilemonkey.crm.security.AppUserPrincipal;
import com.agilemonkey.crm.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("customer")
public class CustomerController {
    private final CustomerService customerService;
    private final ModelMapper modelMapper;

    public CustomerController(@Autowired CustomerService customerService,
                              @Autowired ModelMapper modelMapper) {
        this.customerService = customerService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(produces = "application/json")
    public Page<CustomerDTO> list(Pageable pageable) {
        Page<Customer> customers = customerService.listAll(pageable);

        return customers.map(customer -> modelMapper.map(customer, CustomerDTO.class));
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<CustomerDTO> get(@PathVariable("id") Long id) {
        Customer customer = customerService.get(id);
        if(customer == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(modelMapper.map(customer, CustomerDTO.class));
    }

    @PostMapping(produces = "application/json")
    public  ResponseEntity<CustomerDTO> create(CustomerDTO customer, @AuthenticationPrincipal OAuth2User principal) {
        Customer created = customerService.create(modelMapper.map(customer, Customer.class),
                ((AppUserPrincipal) principal).getUser());
        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(created, CustomerDTO.class));
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<CustomerDTO> update(@PathVariable("id") Long id, CustomerDTO customer,
                              @AuthenticationPrincipal OAuth2User principal) {
        customer.setId(id);
        Customer updated = customerService.update(modelMapper.map(customer, Customer.class),
                ((AppUserPrincipal) principal).getUser());
        return ResponseEntity.ok(modelMapper.map(updated, CustomerDTO.class));
    }

    @DeleteMapping("/{id}")
    public void delete(Long id) {
        customerService.delete(id);
    }
}
