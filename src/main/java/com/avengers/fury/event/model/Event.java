package com.avengers.fury.event.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tbleventlog")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String type;

    private String data;

    @Column(name = "is_completed")
    private boolean isCompleted = false;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "completed_on")
    private LocalDateTime completedOn;
}
