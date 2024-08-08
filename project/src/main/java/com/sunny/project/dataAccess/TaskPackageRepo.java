package com.sunny.project.dataAccess;

import com.sunny.project.entities.TaskPackage;
import com.sunny.project.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskPackageRepo extends JpaRepository<TaskPackage, Long> {
    List<TaskPackage> findByUsers_Id(Long userId);
}