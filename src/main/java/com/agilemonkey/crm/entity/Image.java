package com.agilemonkey.crm.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "images")
@Getter
@Setter
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imageKey;
    private String name;
    private String fileName;
    private Date created;
    @ManyToOne
    @JoinColumn(name="created_by", nullable=false)
    private User createdBy;
}
