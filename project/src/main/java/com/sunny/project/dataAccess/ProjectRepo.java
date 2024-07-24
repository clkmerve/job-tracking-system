package com.sunny.project.dataAccess;

import com.sunny.project.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepo extends JpaRepository<Project,Long> {
}
