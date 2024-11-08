package com.agilemonkey.crm;

import com.agilemonkey.crm.controller.UserController;
import com.agilemonkey.crm.dto.UserDTO;
import com.agilemonkey.crm.entity.User;
import com.agilemonkey.crm.repository.UserRepository;
import com.agilemonkey.crm.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(BlockJUnit4ClassRunner.class)
public class UserControllerTest {

    @Test
    public void whenGetByIdAndFoundThenSuccess(){
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        User user = new User();
        user.setId(1L);
        user.setName("USER");
        user.setEmail("user@user.com");

        when(userRepository.getReferenceById(1L)).thenReturn(user);

        UserController userController = new UserController(new UserService(userRepository), new ModelMapper());
        ResponseEntity<UserDTO> response = userController.get(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        UserDTO userDTO = response.getBody();
        assertNotNull(userDTO);
        assertEquals(1L, (long)userDTO.getId());
        assertEquals("USER", userDTO.getName());
        assertEquals("user@user.com", userDTO.getEmail());
    }

    @Test
    public void whenGetByIdAndNotFoundThenNotFoundResponse(){
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        UserController userController = new UserController(new UserService(userRepository), new ModelMapper());
        ResponseEntity<UserDTO> response = userController.get(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // TODO: add more use cases to cover various combinations
}
