package com.agilemonkey.crm;

import com.agilemonkey.crm.controller.CustomerController;
import com.agilemonkey.crm.dto.CustomerDTO;
import com.agilemonkey.crm.entity.Customer;
import com.agilemonkey.crm.entity.User;
import com.agilemonkey.crm.repository.CustomerRepository;
import com.agilemonkey.crm.security.AppUserPrincipal;
import com.agilemonkey.crm.service.CustomerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(BlockJUnit4ClassRunner.class)
public class CustomerControllerTest {

    @Test
    public void whenGetByIdAndCustomerFoundThenSuccess(){
        Customer customer = new Customer();
        customer.setName("TEST");
        customer.setId(1L);

        CustomerRepository customerRepository = Mockito.mock(CustomerRepository.class);
        when(customerRepository.getReferenceById(1L)).thenReturn(customer);

        CustomerController controller = new CustomerController(new CustomerService(customerRepository),
                new ModelMapper());

        ResponseEntity<CustomerDTO> response = controller.get(1L);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        CustomerDTO customerDTO = response.getBody();
        assertNotNull(customerDTO);
        assertEquals(1L, (long)customerDTO.getId());
        assertEquals("TEST", customerDTO.getName());
    }

    @Test
    public void whenGetByIdAndCustomerNotFoundThen404Response(){

        CustomerRepository customerRepository = Mockito.mock(CustomerRepository.class);

        CustomerController controller = new CustomerController(new CustomerService(customerRepository),
                new ModelMapper());

        ResponseEntity<CustomerDTO> response = controller.get(1L);
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void whenCreateCustomerAndCustomerCreatedThenSuccess() {
        CustomerRepository customerRepository = Mockito.mock(CustomerRepository.class);
        Customer entity = new Customer();
        entity.setId(1L);
        entity.setName("NAME");
        entity.setSurname("SURNAME");
        entity.setImageUrl("/image/123");

        when(customerRepository.save(any(Customer.class))).thenReturn(entity);

        CustomerController controller = new CustomerController(new CustomerService(customerRepository),
                new ModelMapper());
        CustomerDTO customer = new CustomerDTO();
        customer.setSurname("SURNAME");
        customer.setName("NAME");
        customer.setImageUrl("/image/123");

        OAuth2User principal = createPrincipal(false);
        ResponseEntity<CustomerDTO> response = controller.create(customer, principal);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        CustomerDTO responseDTO = response.getBody();
        assertNotNull(responseDTO);
        assertEquals(1L, (long)responseDTO.getId());
        assertEquals("SURNAME", responseDTO.getSurname());
        assertEquals("NAME", responseDTO.getName());
        assertEquals("/image/123", responseDTO.getImageUrl());
    }

    // TODO: add more test cases to cover all combinations

    private static OAuth2User createPrincipal(boolean admin) {
        User user = new User();
        user.setId(1L);
        user.setAdmin(admin);
        user.setName("USER");
        user.setEmail("user@user.com");

        Map<String, Object> attributes = Map.of("email", "user@user.com");

        Set<GrantedAuthority> authorities = admin ? Set.of(new SimpleGrantedAuthority("ADMIN")) : Collections.emptySet();
        return new AppUserPrincipal(authorities, user, attributes);
    }
}

