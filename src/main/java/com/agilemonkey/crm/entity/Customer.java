package com.agilemonkey.crm.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "customers")
@Getter
@Setter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String imageUrl;
    private Date created;
    private Date updated;

    @ManyToOne
    @JoinColumn(name="created_by", nullable=false)
    private User createdBy;

    @ManyToOne
    @JoinColumn(name="updated_by")
    private User updatedBy;
}
