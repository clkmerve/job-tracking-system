package com.sunny.project.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;

    @Column(name = "is_admin")
    private Boolean isAdmin;

    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "status_type")
    private String statusType;

    @ManyToOne//  ilişkiyi belirtiyoruz
    @JoinColumn(name = "profession_id")
    private Profession profession; // Meslek alanı

    @Column(name = "date")
    private String date;

    @Column(name = "resignDate")
    private String resignDate;


    @ManyToMany(mappedBy = "users")
    @JsonIgnoreProperties({"users", "project", "hibernateLazyInitializer", "handler"})
    private List<TaskPackage> taskPackages;

    @JsonIgnoreProperties("user")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ProjectUser> projectUsers;

    @OneToMany(mappedBy = "user")
    private Set<MonthlyTracking> monthlyTrackings;

}
