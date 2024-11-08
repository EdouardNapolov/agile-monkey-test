package com.agilemonkey.crm.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDTO {

    private Long id;
    private String name;
    private String surname;
    private String imageUrl;
}
