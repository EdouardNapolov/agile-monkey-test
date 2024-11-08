package com.agilemonkey.crm.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String email;
    private String name;
    private boolean admin;
}
