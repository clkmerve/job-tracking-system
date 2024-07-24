package com.sunny.project.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MonthlyTracking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private TaskPackage taskPackage;

    @ManyToOne
    @JoinColumn(name = "rate_id", nullable = false)
    private Rate rate;


    @Column(nullable = false)
    private String month; // String olarak tarih
}
