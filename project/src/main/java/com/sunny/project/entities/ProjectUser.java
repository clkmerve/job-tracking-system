package com.sunny.project.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "project_user")
public class ProjectUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_user_id")
    private Long id;

    @JsonIgnoreProperties("user")
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;


}
