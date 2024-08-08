package com.sunny.project.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
        import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "task_packages")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TaskPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_package_id")
    private Long id;

    @Column(name = "task_package_name")
    private String taskPackageName;

    @Column(name = "start_date")
    private Timestamp startDate;

    @Column(name = "end_date")
    private Timestamp endDate;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id", nullable = false)
    @JsonIgnoreProperties({"taskPackages", "hibernateLazyInitializer", "handler"})
    private Project project;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "task_package_user",
            joinColumns = @JoinColumn(name = "task_package_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonIgnoreProperties({"taskPackages", "hibernateLazyInitializer", "handler"})
    private List<User> users;


}
