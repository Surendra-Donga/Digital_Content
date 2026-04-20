package com.Digital_Content.Digital_Content_Rights.Entity;

import jakarta.persistence.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.Digital_Content.Digital_Content_Rights.Enum.ContentStatus;
import com.Digital_Content.Digital_Content_Rights.Enum.ContentType;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class DigitalContent {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @Enumerated(EnumType.STRING)
    private ContentType contentType;

    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDate publishedDate;

    @Enumerated(EnumType.STRING)
    private ContentStatus contentStatus;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy; 

    @CreationTimestamp
    private LocalDateTime createdAt;
}
