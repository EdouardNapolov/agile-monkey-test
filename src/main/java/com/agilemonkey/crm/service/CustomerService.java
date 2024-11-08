package com.agilemonkey.crm.service;

import com.agilemonkey.crm.entity.Customer;
import com.agilemonkey.crm.entity.User;
import com.agilemonkey.crm.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;

    public CustomerService(@Autowired CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer create(Customer customer, User currentUser){
        customer.setCreated(new Date());
        customer.setCreatedBy(currentUser);
        return this.customerRepository.save(customer);
    }

    public Customer update(Customer customer, User currentUser){
        customer.setUpdated(new Date());
        customer.setUpdatedBy(currentUser);
        return this.customerRepository.save(customer);
    }

    public void delete(Long id){
        this.customerRepository.deleteById(id);
    }

    public Customer get(Long id) {
        return this.customerRepository.getReferenceById(id);
    }

    public Page<Customer> listAll(Pageable pageable){
        return this.customerRepository.findAll(pageable);
    }
}
