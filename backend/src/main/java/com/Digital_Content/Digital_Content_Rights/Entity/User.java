package com.Digital_Content.Digital_Content_Rights.Entity;

import jakarta.persistence.*;

@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
}
