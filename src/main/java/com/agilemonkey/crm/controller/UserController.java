package com.agilemonkey.crm.controller;

import com.agilemonkey.crm.dto.UserDTO;
import com.agilemonkey.crm.entity.User;
import com.agilemonkey.crm.security.AppUserPrincipal;
import com.agilemonkey.crm.service.UserService;
import jakarta.websocket.server.PathParam;
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
@RequestMapping("user")
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(@Autowired UserService userService, @Autowired ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }


    @GetMapping(produces = "application/json")
    public Page<UserDTO> list(Pageable pageable) {
        Page<User> users = userService.listAll(pageable);

        return users.map(user -> modelMapper.map(user, UserDTO.class));
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<UserDTO> get(@PathVariable("id") Long id) {
        User user = userService.get(id);
        if(user == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(modelMapper.map(user, UserDTO.class));
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<UserDTO> create(UserDTO user, @AuthenticationPrincipal OAuth2User principal) {
        User created = userService.create(modelMapper.map(user, User.class), ((AppUserPrincipal) principal).getUser());
        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(created, UserDTO.class));
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<UserDTO> update(@PathVariable("id") Long id, UserDTO user, @AuthenticationPrincipal OAuth2User principal) {
        user.setId(id);
        User updated = userService.update(modelMapper.map(user, User.class), ((AppUserPrincipal) principal).getUser());
        return ResponseEntity.ok(modelMapper.map(updated, UserDTO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @PatchMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<UserDTO> changeAdmin(@PathVariable("id") Long id, @PathParam("admin") boolean admin,
                               @AuthenticationPrincipal OAuth2User principal) {
        User updated = userService.changeAdmin(id, admin, ((AppUserPrincipal) principal).getUser());
        return ResponseEntity.ok(modelMapper.map(updated, UserDTO.class));
    }

}
